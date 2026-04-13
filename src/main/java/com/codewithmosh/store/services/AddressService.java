package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressDTO> getAllAddresses();

    Optional<AddressDTO> getAddresseById(Long id);

    AddressDTO createAdresse(AddressDTO addressDTO);

    AddressDTO updateAdresse(Long id, AddressDTO addressDTO);

    void deleteAddresse(Long id);

    List<AddressDTO> getListAdresseByUserId(Long userId);
}
