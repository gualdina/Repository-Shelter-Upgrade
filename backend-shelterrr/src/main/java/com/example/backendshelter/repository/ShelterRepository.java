package com.example.backendshelter.repository;

import com.example.backendshelter.model.Shelter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Primary
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findByName(String name);
}
