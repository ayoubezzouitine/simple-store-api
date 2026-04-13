package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddressDTO;
import com.codewithmosh.store.services.AddressService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
    private AddressService addresseService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAdresse() {
        List<AddressDTO> allAddresses = addresseService.getAllAddresses();
        if (allAddresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allAddresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAdresseById(@PathVariable Long id) {
        Optional<AddressDTO> addresseById = addresseService.getAddresseById(id);
        if (addresseById.isPresent()) {
            return ResponseEntity.ok(addresseById.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddresse(@RequestBody AddressDTO addressDTO) {
        AddressDTO adresseCreated = addresseService.createAdresse(addressDTO);
        return ResponseEntity.ok(adresseCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        AddressDTO addressDTOUpdated = addresseService.updateAdresse(id, addressDTO);
        return ResponseEntity.ok(addressDTOUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addresseService.deleteAddresse(id);
        return ResponseEntity.noContent().build();
    }

    //GET /addresses?userId=5

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAlladdressesOfUserById(@RequestParam("userId") Long userId) {
        List<AddressDTO> listAdresseByUserId = addresseService.getListAdresseByUserId(userId);
        if(listAdresseByUserId.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listAdresseByUserId);
    }
}
