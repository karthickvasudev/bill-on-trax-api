package com.billontrax.common.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class Timestamped {
	private Date createdTime;
	private Date updatedTime;

	@PrePersist
	public void beforeCreate(){
		this.createdTime = new Date();
	}

	@PreUpdate
	public void beforeUpdate(){
		this.updatedTime = new Date();
	}
}
