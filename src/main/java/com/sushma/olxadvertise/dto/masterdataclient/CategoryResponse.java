package com.sushma.olxadvertise.dto.masterdataclient;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
	
	private List<CategoryDto> categories;

}
