package com.billontrax.common.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.Date;


@Data
@MappedSuperclass
public class CreatedTimestamp {
	private Date createdTime;

	@PrePersist
	public void beforeCreate() {
		this.createdTime = new Date();
	}
}
