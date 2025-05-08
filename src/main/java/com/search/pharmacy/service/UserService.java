package com.search.pharmacy.service;

import com.search.pharmacy.config.JwtService;
import com.search.pharmacy.domain.model.Roles;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.mapper.RoleMapper;
import com.search.pharmacy.ws.mapper.UserMapper;
import com.search.pharmacy.ws.model.AuthenticatedUserDTO;
import com.search.pharmacy.ws.model.LoginUserDTO;
import com.search.pharmacy.ws.model.UserDTO;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final RoleMapper roleMapper;

  @Transactional
  public UserDTO create(UserDTO userDTO) {
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    return Optional.of(userDTO)
        .map(userMapper::toEntity)
        .map(userRepository::save)
        .map(userMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new user"));
  }

  public AuthenticatedUserDTO login(LoginUserDTO loginUserDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDTO.getEmail(), loginUserDTO.getPassword()));
    Optional<User> user = userRepository.findByEmail(loginUserDTO.getEmail());
    String jwtToken = jwtService.generateToken(user.get());
    return AuthenticatedUserDTO.builder()
        .email(user.get().getEmail())
        .firstName(user.get().getFirstName())
        .lastName(user.get().getLastName())
        .token(jwtToken)
        .roles(Collections.singletonList(roleMapper.toListDTO((Roles) user.get().getRoles().stream().toList())))
        .build();
  }

  public List<UserDTO> getUsers() {
    return userRepository.findAll().stream().map(userMapper::toDTO).toList();
  }

  public UserDTO getUser(Long id) {
    return userRepository.findById(id).map(userMapper::toDTO).orElseThrow();
  }

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
