package com.sushma.olxadvertise.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdvertiseDTO {

	private Long id;
	private String title;
	private BigDecimal price;
	private String category;
	private String description;

	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	private String status;

	public AdvertiseDTO(Long id, String title, BigDecimal price, String category, String description,
			LocalDateTime createdDate, LocalDateTime modifiedDate, String status) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
		this.description = description;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
