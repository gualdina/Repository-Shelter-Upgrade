package com.example.backendshelter.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterReturnResponse {
    private Long id;
    private String name;
    private int maxPopulation;
    private List<PetReturnResponse> petsReturnResponse;
}