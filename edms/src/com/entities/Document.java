package com.entities;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Document entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true) 
@Table(name = "document", catalog = "edms")
public class Document implements java.io.Serializable {

	// Fields

	private Integer no;
	private String name;
	private Double size;
	private String type;
	private Integer frequency;
	private Timestamp sdate;
	private Timestamp edate;
	private Integer dedit;
	private Integer dprint;
	private Integer dread;
	private Integer ddownload;
	private Integer dstate;

	// Constructors

	/** default constructor */
	public Document() {
	}

	/** minimal constructor */
	public Document(String name, Double size, String type, Integer frequency,
			Timestamp sdate) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.frequency = frequency;
		this.sdate = sdate;
	}

	/** full constructor */
	public Document(String name, Double size, String type, Integer frequency,
			Timestamp sdate, Timestamp edate, Integer dedit, Integer dprint,
			Integer dread, Integer ddownload, Integer dstate) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.frequency = frequency;
		this.sdate = sdate;
		this.edate = edate;
		this.dedit = dedit;
		this.dprint = dprint;
		this.dread = dread;
		this.ddownload = ddownload;
		this.dstate = dstate;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "Generator")    
	@GenericGenerator(name = "Generator", strategy = "native")//native：primary key is produced by database
	@Column(name = "no", unique = true, nullable = false)
	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "name", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "size", nullable = false, length = 20)
	public Double getSize() {
		return this.size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "frequency",columnDefinition="int default 10")
	public Integer getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Column(name = "sdate", length = 19,columnDefinition="datetime default CURRENT_TIMESTAMP")
	public Timestamp getSdate() {
		return this.sdate;
	}

	public void setSdate(Timestamp sdate) {
		this.sdate = sdate;
	}

	@Column(name = "edate", length = 19)
	public Timestamp getEdate() {
		return this.edate;
	}

	public void setEdate(Timestamp edate) {
		this.edate = edate;
	}

	@Column(name = "dedit",columnDefinition="int(1) default 1")
	public Integer getDedit() {
		return this.dedit;
	}

	public void setDedit(Integer dedit) {
		this.dedit = dedit;
	}

	@Column(name = "dprint",columnDefinition="int(1) default 1")
	public Integer getDprint() {
		return this.dprint;
	}

	public void setDprint(Integer dprint) {
		this.dprint = dprint;
	}

	@Column(name = "dread",columnDefinition="int(1) default 1")
	public Integer getDread() {
		return this.dread;
	}

	public void setDread(Integer dread) {
		this.dread = dread;
	}

	@Column(name = "ddownload",columnDefinition="int(1) default 1")
	public Integer getDdownload() {
		return this.ddownload;
	}

	public void setDdownload(Integer ddownload) {
		this.ddownload = ddownload;
	}

	@Column(name = "dstate",columnDefinition="int(1) default 0")
	public Integer getDstate() {
		return this.dstate;
	}

	public void setDstate(Integer dstate) {
		this.dstate = dstate;
	}

}