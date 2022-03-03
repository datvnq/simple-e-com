package com.example.simpleecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDto {
    private int id;
    private String name;
    private int countryId;
    private String countryCode;
    private String countryName;
}
