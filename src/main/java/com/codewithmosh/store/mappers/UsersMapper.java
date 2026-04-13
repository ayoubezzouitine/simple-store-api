package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.AddressDTO;
import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UsersMapper {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        if (user.getAddresses() != null) {
            List<AddressDTO> addressDTOS = user.getAddresses()
                    .stream()
                    .map(addressMapper::toDTO)
                    .collect(Collectors.toList());
            userDTO.setAddressDTOS(addressDTOS);

        }
        return userDTO;
    }


    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setId(user.getId());
        user.setName(user.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getAddressDTOS() != null) {
            List<Address> listAddresses = userDTO.getAddressDTOS()
                    .stream()
                    .map(addressMapper::toEntity)
                    .collect(Collectors.toList());

            user.setAddresses(listAddresses);
        }

        return user;
    }

}
