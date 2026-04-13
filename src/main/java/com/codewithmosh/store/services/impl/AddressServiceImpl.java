package com.codewithmosh.store.services.impl;

import com.codewithmosh.store.dtos.AddressDTO;
import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.AddressMapper;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private static final Logger loggerService = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    /**
     * @return
     */
    @Override
    public List<AddressDTO> getAllAddresses() {
        List<AddressDTO> listAddressDTO = StreamSupport
                .stream(addressRepository.findAll().spliterator(), false)
                .map(addressMapper::toDTO).toList();

        loggerService.info("Fetching data from database: " + listAddressDTO.stream().count() + " records");

        if (listAddressDTO.isEmpty()) {
            throw new RuntimeException("No addresses found");
        }
        return listAddressDTO;
    }

    @Override
    public Optional<AddressDTO> getAddresseById(Long id) throws RuntimeException {
        loggerService.info("Fetching data from id {}", id);
        Address address = getAddressOrThrow(id);
        return Optional.of(addressMapper.toDTO(address));
    }

    @Override
    public AddressDTO createAdresse(AddressDTO addressDTO) {
        addressDTO.setId(null);
        Address address = addressMapper.toEntity(addressDTO);
        Address createdAdresse = addressRepository.save(address);
        return addressMapper.toDTO(createdAdresse);
    }

    @Override
    public AddressDTO updateAdresse(Long id, AddressDTO addressDTO) {
        Address address = getAddressOrThrow(id);
        address.setState(addressDTO.getState());
        address.setZip(address.getZip());
        address.setCity(addressDTO.getCity());
        if (userRepository.existsById(addressDTO.getId())) {
            User user = userRepository
                    .findById(addressDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("user with id " + addressDTO.getUserId() + " is not found"));
            address.setUser(user);
        }
        Address savedAdresse = addressRepository.save(address);
        return addressMapper.toDTO(savedAdresse);
    }

    @Override
    public void deleteAddresse(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            loggerService.info("Deleting of the Adresse with id " + id + " ...");
        } else {
            loggerService.error("Delete action Failed");
            loggerService.info("No Adresse with id " + id + " is found for the delete");
        }
    }

    /**
     * @param userId
     * @return AddressDTO
     */
    @Override
    public List<AddressDTO> getListAdresseByUserId(Long userId) {
        List<Address> listAdresseByUserId = addressRepository.getListAdresseByUserId(userId);
        List<AddressDTO> listAdresseDtoByUserId = listAdresseByUserId
                .stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
         return listAdresseDtoByUserId;
    }

    private Address getAddressOrThrow(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> {
            RuntimeException ex = new RuntimeException("Adresse not found with id " + id);
            loggerService.error("Adress not found with the id {} ", id, ex);
            return ex;
        });
    }
}
