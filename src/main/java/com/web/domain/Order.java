package com.web.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Orders")
public class Order  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phone;

	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = dateFormat.format(orderDate);
		return "Order{" +
				"id=" + id +
				", orderDate=" + formattedDate +
				", amount=" +
				", status=" +
				", address=" +
				", phone=" +
				'}';
	}
}