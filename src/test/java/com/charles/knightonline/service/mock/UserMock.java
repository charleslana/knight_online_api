package com.charles.knightonline.service.mock;

import com.charles.knightonline.model.dto.UserDTO;
import com.charles.knightonline.model.entity.UserEntity;
import com.charles.knightonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMock {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    public void createUser(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
    }

    public UserDTO getUserMock() {
        UserDTO dto = new UserDTO();
        dto.setEmail("user@user.com");
        dto.setPassword("123456");
        return dto;
    }

    public UserDTO getUserMock(String email, String password) {
        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    public void deleteAllUser() {
        repository.deleteAll();
    }

    public Long getUser() {
        Optional<UserEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
