package com.pawcare.web.repository;

import com.pawcare.web.model.Pet;
import com.pawcare.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerOrderByNameAsc(User owner);
}
