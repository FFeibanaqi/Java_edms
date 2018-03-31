package com.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Manage entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "manage", catalog = "edms")
public class Manage implements java.io.Serializable {

	// Fields

	private Integer mno;
	private Timestamp mdate;
	private Integer mtype;
	private Integer id;
	private Integer aid;
	private Integer mstate;

	// Constructors

	/** default constructor */
	public Manage() {
	}

	/** minimal constructor */
	public Manage(Timestamp mdate, Integer mtype, Integer id, Integer aid) {
		this.mdate = mdate;
		this.mtype = mtype;
		this.id = id;
		this.aid = aid;
	}

	/** full constructor */
	public Manage(Timestamp mdate, Integer mtype, Integer id, Integer aid,
			Integer mstate) {
		this.mdate = mdate;
		this.mtype = mtype;
		this.id = id;
		this.aid = aid;
		this.mstate = mstate;
	}

	// Property accessors
	@Id
	@GeneratedValue 
	@Column(name = "mno", unique = true, nullable = false)
	public Integer getMno() {
		return this.mno;
	}

	public void setMno(Integer mno) {
		this.mno = mno;
	}

	@Column(name = "mdate", nullable = false, length = 19)
	public Timestamp getMdate() {
		return this.mdate;
	}

	public void setMdate(Timestamp mdate) {
		this.mdate = mdate;
	}

	@Column(name = "mtype", nullable = false)
	public Integer getMtype() {
		return this.mtype;
	}

	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}

	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "aid", nullable = false)
	public Integer getAid() {
		return this.aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	@Column(name = "mstate")
	public Integer getMstate() {
		return this.mstate;
	}

	public void setMstate(Integer mstate) {
		this.mstate = mstate;
	}

}