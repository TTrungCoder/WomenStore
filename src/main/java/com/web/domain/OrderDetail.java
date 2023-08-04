package com.web.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Orderdetails")
public class OrderDetail  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Double unitPrice;

	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;

	@ManyToOne
	@JoinColumn(name="orderId")
	private Order order;
}
