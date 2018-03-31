package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Department entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "department", catalog = "edms")
public class Department implements java.io.Serializable {

	// Fields

	private Integer did;
	private String dname;
	private Integer managerid;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** minimal constructor */
	public Department(String dname) {
		this.dname = dname;
	}

	/** full constructor */
	public Department(String dname, Integer managerid) {
		this.dname = dname;
		this.managerid = managerid;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "Generator")    
	@GenericGenerator(name = "Generator", strategy = "assigned") 
	@Column(name = "did", unique = true, nullable = false)
	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	@Column(name = "dname", nullable = false, length = 20)
	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Column(name = "managerid")
	public Integer getManagerid() {
		return this.managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}

}