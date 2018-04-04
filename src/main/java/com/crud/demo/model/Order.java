package com.crud.demo.model;

import java.time.LocalDateTime;

public class Order {
	
	private Integer orderId;
	private Float amount;
	private Float fee;
	private String orderType;
	private LocalDateTime dateCreated;
	private String status;
	private Integer userId;
	private Integer currencyId;

}
