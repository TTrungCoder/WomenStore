package com.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private Date createDate;
    private Boolean available;
    private Long categoryId;

}
