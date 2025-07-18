package com.search.pharmacy.service;

import static com.search.pharmacy.utils.Utils.convertToRoleDTOs;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.config.JwtService;
import com.search.pharmacy.domain.model.Roles;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.repository.RoleRepository;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.mapper.PartialUserMapper;
import com.search.pharmacy.ws.mapper.UserMapper;
import com.search.pharmacy.ws.model.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final RoleRepository roleRepository;
  private final PartialUserMapper partialUserMapper;
  private final MinioService minioService;
  private final ProfileValidationService profileValidationService;

  @Transactional
  public UserDTO partialUserCreation(PartialUserDTO partialUserDTO) {

    log.info("[UserService] Creating Partial User with Email : {}", partialUserDTO.getEmail());

    if (userRepository.findByEmail(partialUserDTO.getEmail()).isPresent()) {
      throw new InvalidParamException("Email already taken: " + partialUserDTO.getEmail() + " !");
    }
    partialUserDTO.setPassword(passwordEncoder.encode(partialUserDTO.getPassword()));
    partialUserDTO.setValid(false);

    User user = partialUserMapper.toEntity(partialUserDTO);
    Roles defaultRole =
        (Roles)
            roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Le rôle ROLE_USER n'existe pas"));

    if (user.getRoles() == null) {
      user.setRoles(new HashSet<>());
    }

    user.getRoles().add(defaultRole);

    return Optional.of(user)
        .map(userRepository::save)
        .map(userMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Impossible de créer un nouvel utilisateur"));
  }

  @Transactional
  public UserDTO completeUserCreation(
      Long userId, CompleteUserDTO completeUserDTO, List<MultipartFile> files) {

    if (files == null || files.isEmpty()) {
      throw new InvalidParamException("User must have at least one file associated with him/her");
    }

    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalUser.isEmpty()) {
      throw new InvalidParamException("User with id " + userId + " not found");
    }

    List<String> fileUrls = new ArrayList<>();
    if (files != null) {
      files.forEach(
          file -> {
            try {
              fileUrls.add(minioService.uploadFiles(file));
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });
    }

    completeUserDTO.setUrls(fileUrls);
    optionalUser.get().setFirstName(completeUserDTO.getFirstName());
    optionalUser.get().setLastName(completeUserDTO.getLastName());
    optionalUser.get().setBirthDate(completeUserDTO.getBirthDate());
    optionalUser.get().setUrls(completeUserDTO.getUrls());

    return userMapper.toDTO(userRepository.save(optionalUser.get()));
  }

  @Transactional
  public String validateUserProfile(Long userId) throws MessagingException {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new InvalidParamException("User with id " + userId + " not found");
    }
    optionalUser.get().setValid(true);
    userRepository.save(optionalUser.get());
    profileValidationService.sendValidationProfileEmail(optionalUser.get());
    return "User with id " + userId + " validated successfully";
  }

  public AuthenticatedUserDTO login(LoginUserDTO loginUserDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDTO.getEmail(), loginUserDTO.getPassword()));
    Optional<User> user = userRepository.findByEmail(loginUserDTO.getEmail());
    String jwtToken = jwtService.generateToken(user.get());
    return AuthenticatedUserDTO.builder()
        .userId(user.get().getId())
        .email(user.get().getEmail())
        .firstName(user.get().getFirstName())
        .lastName(user.get().getLastName())
        .token(jwtToken)
        .roles(convertToRoleDTOs(user.get().getRoles()))
        .build();
  }

  public List<UserDTO> getUsers() {
    return userRepository.findAll().stream().map(userMapper::toDTO).toList();
  }

  public UserDTO getUser(Long id) {
    return userRepository.findById(id).map(userMapper::toDTO).orElseThrow();
  }

  @Transactional
  public UserDTO update(Long id, Map<String, Object> fields) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      fields.forEach(
          (key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, optionalUser.get(), value);
          });
      return userMapper.toDTO(userRepository.save(optionalUser.get()));
    }
    return null;
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
