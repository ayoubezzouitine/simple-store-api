package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.User;

public interface UserService {
    public User createUser(UserDTO userDTO);

    public User updateUser(Long id,UserDTO userDTO);
}
