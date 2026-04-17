package com.sushma.olxadvertise.dto;

public class AdvertiseRequestDto {

	private String title;
	private Double price;
	private String category;
	private String description;

	public AdvertiseRequestDto(String title, Double price, String category, String description) {
		super();
		this.title = title;
		this.price = price;
		this.category = category;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
