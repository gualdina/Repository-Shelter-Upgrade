package com.example.backendshelter.controller;

import com.example.backendshelter.model.Pet;
import com.example.backendshelter.model.Shelter;
import com.example.backendshelter.request.CreatePetRQ;
import com.example.backendshelter.request.PetReturnResponse;
import com.example.backendshelter.request.ShelterCreateRequest;
import com.example.backendshelter.request.ShelterReturnResponse;
import com.example.backendshelter.service.ShelterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Validated
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    //Add shelters
    @PostMapping(value = "/shelters")
    public List<ShelterReturnResponse> addShelters(@RequestBody @Valid List<ShelterCreateRequest> sheltersReqs) {
        List<Shelter> shelters = new ArrayList<>();
        List<ShelterReturnResponse> sheltersResponse = new ArrayList<>();
        for (ShelterCreateRequest shelterReq : sheltersReqs) {
            shelters.add(Shelter
                    .builder()
                    .name(shelterReq.getName())
                    .maxPopulation(shelterReq.getMaxPopulation())
                    .build());
        }
        shelterService.saveShelters(shelters);
        for (Shelter shelter : shelters) {
            sheltersResponse.add(new ShelterReturnResponse(
                    shelter.getId(),
                    shelter.getName(),
                    shelter.getMaxPopulation(),
                    Collections.emptyList()
            ));
        }

        return sheltersResponse;
    }

    //Add pets to Shelter
    @PostMapping(value = "/shelters/{id}/pet")
    public List<PetReturnResponse> addPetsToShelter(@PathVariable(value = "id") Long id, @RequestBody @Valid List<CreatePetRQ> petsReq) {
        List<Pet> pets = new ArrayList<>();
        for (CreatePetRQ petReq : petsReq) {
            pets.add(Pet
                    .builder()
                    .petType(petReq.getPetType())
                    .name(petReq.getName())
                    .build());
        }
        pets = shelterService.addPetsToShelter(id, pets);
        List<PetReturnResponse> petsResp = new ArrayList<>();
        for (Pet pet : pets) {
            petsResp.add(new PetReturnResponse(
                    pet.getId(),
                    pet.getPetType()
            ));
        }
        return petsResp;
    }

    //Get shelter by name
    @GetMapping(value = "/shelters/{name}")
    public ShelterReturnResponse getShelterByName(@PathVariable(name = "name")String name){
        Shelter shelter = shelterService.findByName(name);
        ShelterReturnResponse shelterResponse = (new ShelterReturnResponse(
                shelter.getId(),
                shelter.getName(),
                shelter.getMaxPopulation(),
                new ArrayList<>()
        ));
        if(!shelter.getPets().isEmpty() && shelter.getPets() != null){
            for(Pet pet : shelter.getPets()){
                shelterResponse.getPetsReturnResponse().add(new PetReturnResponse(
                        pet.getId(),
                        pet.getPetType()
                ));
            }
        }
        return shelterResponse;
    }

    @GetMapping(value = "/shelters-exists/{id}")
    public ShelterReturnResponse existsById(@PathVariable(name = "id")Long id){
        Shelter shelter = shelterService.existsById(id);
        ShelterReturnResponse shelterResponse = (new ShelterReturnResponse(
                shelter.getId(),
                shelter.getName(),
                shelter.getMaxPopulation(),
                new ArrayList<>()
        ));
        if(!shelter.getPets().isEmpty() && shelter.getPets() != null){
            for(Pet pet : shelter.getPets()){
                shelterResponse.getPetsReturnResponse().add(new PetReturnResponse(
                        pet.getId(),
                        pet.getPetType()
                ));
            }
        }

        return shelterResponse;
    }
    @DeleteMapping(value = "/shelter/{id}")
    public void deleteShelterById(@PathVariable(name = "id") Long id){
        shelterService.deleteById(id);
    }
}
