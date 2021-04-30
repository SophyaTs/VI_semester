package com.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable{
	private long id;
	private String name;
	private String description;
	private long price;
}
