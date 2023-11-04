package com.project.angleace.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String type;
    private String pathImage;
    private Integer amount;
    private String detail;
    private String size;
    private double sellPrice;
    private double cost;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    private boolean saleDate; // Declare the saleDate variable

// Other code in your service class

    public Instant getSaleDate() {
        if (saleDate) {
            // Provide a valid Instant object or return null if saleDate is true
            // Example: Return the current timestamp as an Instant
            return Instant.now();
        }
        return null; // Return null if saleDate is false
    }
}
