package com.sushma.olxadvertise.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sushma.olxadvertise.dto.AdvertiseDTO;
import com.sushma.olxadvertise.dto.AdvertiseRequestDto;
import com.sushma.olxadvertise.dto.AdvertiseResponseDto;
import com.sushma.olxadvertise.dto.AdvertiseSearchRequestDTO;
import com.sushma.olxadvertise.dto.AdvertiseSearchResponseDTO;

@RestController
public class AdvertisementController {

	@PostMapping("/advertise")
	public ResponseEntity<AdvertiseResponseDto> createAdvertisement(
			@RequestBody AdvertiseRequestDto advertiseRequestDto) {

		AdvertiseResponseDto responseDto = new AdvertiseResponseDto(1, "Sofa available for sale", 3000.0, "Furniture",
				"Sofa 5 years old available for Sale in Pune", "sushg", "13:20 14/04/2026", "13:20 14/04/2026", "OPEN");
		return new ResponseEntity<AdvertiseResponseDto>(responseDto, HttpStatus.CREATED);
	}

	@PutMapping("/advertise/{id}")
	public ResponseEntity<AdvertiseResponseDto> updateAdvertisement(@RequestBody AdvertiseRequestDto requestDto,
			@PathVariable("id") int advertiseId) {
		AdvertiseResponseDto responseDto = new AdvertiseResponseDto(1, "Sofa available for sale", 2500.0, "Furniture",
				"Sofa 2 years old available for Sale in Kolkata", "sushg", "13:20 14/04/2026", "13:20 14/04/2026",
				"OPEN");
		return new ResponseEntity<AdvertiseResponseDto>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/user/advertise")
	public ResponseEntity<List<AdvertiseResponseDto>> fetchAdvertisementOfLoggedUser() {
		List<AdvertiseResponseDto> responseDtosList = List.of(
				new AdvertiseResponseDto(1, "Sofa available for sale", 2500.0, "Furniture",
						"Sofa 2 years old available for Sale in Kolkata", "sushg", "13:20 14/04/2026",
						"13:20 14/04/2026", "OPEN"),
				new AdvertiseResponseDto(2, "Sofa available for sale", 2500.0, "Furniture",
						"Sofa 2 years old available for Sale in Kolkata", "sushg", "13:20 14/04/2026",
						"13:20 14/04/2026", "OPEN"),
				new AdvertiseResponseDto(2, "Sofa available for sale", 2500.0, "Furniture",
						"Sofa 2 years old available for Sale in Kolkata", "sushg", "13:20 14/04/2026",
						"13:20 14/04/2026", "OPEN"));

		return new ResponseEntity<List<AdvertiseResponseDto>>(responseDtosList, HttpStatus.OK);
	}

	@GetMapping("/user/advertise/{advertiseId}")
	public ResponseEntity<AdvertiseResponseDto> fetchAdvertisementById(@PathVariable("advertiseId") int advertiseId) {

		AdvertiseResponseDto responseDto = new AdvertiseResponseDto(1, "Sofa available for sale", 2500.0, "Furniture",
				"Sofa 2 years old available for Sale in Kolkata", "sushg", "13:20 14/04/2026", "13:20 14/04/2026",
				"OPEN");
		return new ResponseEntity<AdvertiseResponseDto>(responseDto, HttpStatus.OK);

	}

	@DeleteMapping("/user/advertise/{advertiseId}")
	public ResponseEntity<Boolean> removeAdvertisementById(@PathVariable("advertiseId") int advertiseId) {
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/advertise/search/filtercriteria")
	public void searchAdvertises(@ModelAttribute AdvertiseSearchRequestDTO request) {

	}

	@GetMapping("/advertise/{advertiseId}")
	public void searchAdvertiseById(@PathVariable("advertiseId") int advertiseId) {

	}

}
