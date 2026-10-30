package com.Jay.ECom_Project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import tools.jackson.databind.node.StringNode;

import java.beans.BeanDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    private Date releaseDate;

    @JsonProperty("productAvailable")
    private boolean available;

    @JsonProperty("stockQuantity")
    private Integer quantity;

    private String ImageName;
    private String ImageType;

    @Lob
    private byte[] ImageData;

}
