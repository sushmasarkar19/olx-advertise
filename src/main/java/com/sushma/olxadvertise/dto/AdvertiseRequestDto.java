package com.sushma.olxadvertise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseRequestDto {
    private String title;
    private double price;
    private int categoryId;
    private Integer statusId;   // optional on POST, required on PUT
    private String description;
}
