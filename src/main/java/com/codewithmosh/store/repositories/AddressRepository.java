package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {
   @Query("SELECT ad FROM Address ad where ad.user.id =:userId")
   List<Address> getListAdresseByUserId(Long userId);
}