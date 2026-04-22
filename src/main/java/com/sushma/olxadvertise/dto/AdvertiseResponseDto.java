package com.sushma.olxadvertise.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseResponseDto {
    private int id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String username;
    private String postedBy;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private String status;
}
