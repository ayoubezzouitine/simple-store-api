package com.codewithmosh.store.services.impl;

import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.exceptions.UserNotFoundException;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User userToCreate = new User();
        userToCreate.setName(userDTO.getName());
        userToCreate.setEmail(userDTO.getEmail());
        userToCreate.setPassword(userDTO.getPassword());
        return userRepository.save(userToCreate);
    }

    @Override
    public User updateUser(Long id,UserDTO userDTO) {
        User userToUpdate = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with the id "+ id + " is not found"));
        userToUpdate.setName(userDTO.getName());
        userToUpdate.setPassword(userDTO.getPassword());
        userToUpdate.setEmail(userToUpdate.getEmail());

        return userRepository.save(userToUpdate);
    }
}
