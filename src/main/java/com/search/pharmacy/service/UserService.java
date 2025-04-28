package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.mapper.UserMapper;
import com.search.pharmacy.ws.model.UserDTO;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDTO create(UserDTO userDTO) {
    return Optional.of(userDTO)
        .map(userMapper::toEntity)
        .map(userRepository::save)
        .map(userMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new user"));
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
