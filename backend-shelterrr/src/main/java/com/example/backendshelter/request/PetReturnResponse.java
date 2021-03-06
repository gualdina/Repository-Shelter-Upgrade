package com.example.backendshelter.request;

import com.example.backendshelter.model.PetType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetReturnResponse {
    private Long id;
    private PetType petType;
}