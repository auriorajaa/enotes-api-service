package com.example.enotes.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	@CreatedDate
	private Date createdOn;

	@LastModifiedBy
	@Column(insertable = false)
	private Integer updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_on", insertable = false)
	@LastModifiedDate
	private Date updatedOn;
}
