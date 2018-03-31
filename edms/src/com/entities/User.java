package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "edms")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String password;
	private String name;
	private Integer rank;
	private Integer did;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String password, String name, Integer rank) {
		this.password = password;
		this.name = name;
		this.rank = rank;
	}

	/** full constructor */
	public User(Integer id,String password, String name, Integer rank, Integer did) {
		this.id=id;
		this.password = password;
		this.name = name;
		this.rank = rank;
		this.did = did;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "Generator")    
	@GenericGenerator(name = "Generator", strategy = "assigned") //assigned:primary key is produced by java web project
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "password", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "rank", nullable = false)
	public Integer getRank() {
		return this.rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "did")
	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

}