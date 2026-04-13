package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.AddressDTO;
import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    AddressRepository addressRepository;
    UserRepository userRepository;

    @Autowired
    public AddressMapper(AddressRepository addressRepository, UserRepository userRepository){
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }
    public AddressDTO toDTO(Address address) {
        if (address == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZip(address.getZip());
        if(address.getUser()!=null){
            dto.setUserId(address.getUser().getId());
        }
        return dto;
    }

    public Address toEntity(AddressDTO dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZip(dto.getZip());
        if(dto.getUserId()!=null){
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            address.setUser(user);
        }

        return address;
    }

}
