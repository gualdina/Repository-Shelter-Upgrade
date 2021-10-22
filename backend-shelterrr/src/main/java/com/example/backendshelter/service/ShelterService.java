package com.example.backendshelter.service;

import com.example.backendshelter.model.Pet;
import com.example.backendshelter.model.Shelter;
import com.example.backendshelter.repository.PetRepository;
import com.example.backendshelter.repository.ShelterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    public ShelterService(ShelterRepository shelterRepo, PetRepository petRepo) {
        this.shelterRepository = shelterRepo;
        this.petRepository = petRepo;
    }

    public List<Shelter> saveShelters(List<Shelter> shelters) {
        for (Shelter shelter : shelters) {
            shelterRepository.save(shelter);
        }
        return shelters;
    }
    //Add pets to shelter by adding shelter id in each pet
    public List<Pet> addPetsToShelter(Long id, List<Pet> pets) {
        Optional<Shelter> shelter = shelterRepository.findById(id);
        List<Pet> returnPets = new ArrayList<>();
        if(shelter.isPresent()){

            for(Pet pet : pets){
                pet.setShelter(shelter.get());
                returnPets.add(petRepository.save(pet));
            }
            return returnPets;
        }
        //Exception
        return null;
    }

    public Shelter findByName(String name) {
        return shelterRepository.findByName(name);
    }

    public Shelter existsById(Long id) {
        if(shelterRepository.existsById(id)){
            return shelterRepository.findById(id).get();
        }
        else return null;
    }

    public void deleteById(Long id) {
        Optional<Shelter> shelter = shelterRepository.findById(id);
        //Check if shelter exists
        if(shelter.isPresent()){
            //Check if there are any pets in the shelter
            if(!shelter.get().getPets().isEmpty()){
                //Delete all pets in the shelter
                for(Pet pet : shelter.get().getPets()){
                    petRepository.deleteById(pet.getId());
                }
            }
            //Delete shelter
            shelterRepository.deleteById(id);
        }
    }
}
