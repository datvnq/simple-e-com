package com.example.simpleecom.dto;

import com.example.simpleecom.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private int id;
    private String code;
    private String name;
}
