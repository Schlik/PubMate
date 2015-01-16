package com.schlik.pubmate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PubModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String pub_name;

	public PubModel( String name ) {
		this.pub_name = name;
	}

	public String getName() {
		return pub_name;
	}
	
	public Long getId()	{
		return id;
	}
} 


