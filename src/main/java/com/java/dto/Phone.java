package com.java.dto;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="student")
@EqualsAndHashCode
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@DynamicUpdate
public class Phone {
	@Id
	private long number;
	@Enumerated(EnumType.STRING)
	private Type type;
	@ManyToOne
	private Student student;
}
