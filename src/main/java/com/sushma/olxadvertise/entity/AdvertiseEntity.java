package com.sushma.olxadvertise.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADVERTISES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "category", nullable = false)
    private int categoryId;

    @Column(name = "status", nullable = false)
    private int statusId;

    @Column(name = "price")
    private double price;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "active")
    private String active;

    @Column(name = "posted_by")
    private String postedBy;

    @Column(name = "username")
    private String username;
}
