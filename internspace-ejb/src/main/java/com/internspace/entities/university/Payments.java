package com.internspace.entities.university;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Payment")
public class Payments {
	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Payment_id")
	long id;

	@Column(name = "Payment_date")
	LocalDate PaymentDate;
	

	@ManyToOne
	@JoinColumn(name = "uni_id")
	University university;
	public Payments() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}public LocalDate getPaymentDate() {
		return PaymentDate;
	}
	public University getUniversity() {
		return university;
	}
	public void setId(long id) {
		this.id = id;
	} 
	public void setPaymentDate(LocalDate paymentDate) {
		PaymentDate = paymentDate;
	}
	 public void setUniversity(University university) {
		this.university = university;
	}
	

}
