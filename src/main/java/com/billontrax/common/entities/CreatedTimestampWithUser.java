package com.billontrax.common.entities;

import com.billontrax.common.config.CurrentUserHolder;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class CreatedTimestampWithUser {
	private Date createdTime;
	private Long createdBy;

	@PrePersist
	public void beforeCreate() {
		this.createdTime = new Date();
		if (CurrentUserHolder.getUserId() != null) {
			this.createdBy = CurrentUserHolder.getUserId();
		}
	}
}
