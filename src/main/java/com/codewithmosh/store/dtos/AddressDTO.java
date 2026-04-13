package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Long userId;
}
