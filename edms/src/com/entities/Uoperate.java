package com.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Uoperate entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "uoperate", catalog = "edms")
public class Uoperate implements java.io.Serializable {

	// Fields

	private Integer uno;
	private Timestamp udate;
	private Integer utype;
	private Integer id;
	private Integer no;
	private Integer ustate;

	// Constructors

	/** default constructor */
	public Uoperate() {
	}

	/** minimal constructor */
	public Uoperate(Timestamp udate, Integer utype, Integer id, Integer no) {
		this.udate = udate;
		this.utype = utype;
		this.id = id;
		this.no = no;
	}

	/** full constructor */
	public Uoperate(Timestamp udate, Integer utype, Integer id, Integer no,
			Integer ustate) {
		this.udate = udate;
		this.utype = utype;
		this.id = id;
		this.no = no;
		this.ustate = ustate;
	}

	// Property accessors
	@Id
	@GeneratedValue 
	@Column(name = "uno", unique = true, nullable = false)
	public Integer getUno() {
		return this.uno;
	}

	public void setUno(Integer uno) {
		this.uno = uno;
	}

	@Column(name = "udate", length = 19,columnDefinition="datetime default CURRENT_TIMESTAMP")
	public Timestamp getUdate() {
		return this.udate;
	}

	public void setUdate(Timestamp udate) {
		this.udate = udate;
	}

	@Column(name = "utype", nullable = false)
	public Integer getUtype() {
		return this.utype;
	}

	public void setUtype(Integer utype) {
		this.utype = utype;
	}

	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "no", nullable = false)
	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "ustate")
	public Integer getUstate() {
		return this.ustate;
	}

	public void setUstate(Integer ustate) {
		this.ustate = ustate;
	}

}