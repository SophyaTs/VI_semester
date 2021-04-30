package com.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Containing implements Serializable{
	private long orderid;
	private long productid;
	private long quantity;
}
