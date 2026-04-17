package com.sushma.olxadvertise.dto;

import java.util.List;

public class AdvertiseSearchResponseDTO {

	private List<AdvertiseDTO> advertises;

	public AdvertiseSearchResponseDTO(List<AdvertiseDTO> advertises) {
		super();
		this.advertises = advertises;
	}

	public List<AdvertiseDTO> getAdvertises() {
		return advertises;
	}

	public void setAdvertises(List<AdvertiseDTO> advertises) {
		this.advertises = advertises;
	}

}
