package com.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Aoperate entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "aoperate", catalog = "edms")
public class Aoperate implements java.io.Serializable {

	// Fields

	private Integer ano;
	private Timestamp adate;
	private Integer atype;
	private Integer aid;
	private Integer no;
	private Integer astate;

	// Constructors

	/** default constructor */
	public Aoperate() {
	}

	/** minimal constructor */
	public Aoperate(Timestamp adate, Integer atype, Integer aid, Integer no) {
		this.adate = adate;
		this.atype = atype;
		this.aid = aid;
		this.no = no;
	}

	/** full constructor */
	public Aoperate(Timestamp adate, Integer atype, Integer aid, Integer no,
			Integer astate) {
		this.adate = adate;
		this.atype = atype;
		this.aid = aid;
		this.no = no;
		this.astate = astate;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "Generator")    
	@GenericGenerator(name = "Generator", strategy = "native") 
	@Column(name = "ano", unique = true, nullable = false)
	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Column(name = "adate", nullable = false, length = 19,columnDefinition="datetime default CURRENT_TIMESTAMP")
	public Timestamp getAdate() {
		return this.adate;
	}

	public void setAdate(Timestamp adate) {
		this.adate = adate;
	}

	@Column(name = "atype", nullable = false)
	public Integer getAtype() {
		return this.atype;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}

	@Column(name = "aid", nullable = false)
	public Integer getAid() {
		return this.aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	@Column(name = "no", nullable = false)
	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "astate")
	public Integer getAstate() {
		return this.astate;
	}

	public void setAstate(Integer astate) {
		this.astate = astate;
	}

}