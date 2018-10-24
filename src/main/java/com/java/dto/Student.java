package com.java.dto;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@ToString
@EqualsAndHashCode
@Entity(name="st")
@Table(name="student_details")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
@Immutable
@SQLDelete(sql="update student_details set status = 2 where id = ? and version = ?")//Soft delete
//@Where(clause="status <> 2")
@NamedQuery(name="joinFetch", query="select s from st s join fetch s.phones where s.id= :id")
public class Student {
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id;
	@Column(name="stName",unique=true)
	private String name;
	private float marks;
	public Student(String name) {
		this.name= name;
	}
	@Version
	private long version;
	private Status status=Status.ACTIVE;
	@OneToMany( cascade=CascadeType.PERSIST,  mappedBy="student")
	@BatchSize(size=3)
	private List<Phone> phones;
}
//5 students : 3 queries
/*student_phone*/