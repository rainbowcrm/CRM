package com.rainbow.crm.address.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;

public class Address  extends CRMBusinessModelObject{
	
	Division division;
	Customer customer;
	FiniteValue addressType;
	String recipient ;
	String address1;
	String address2;
	String zipcode;
	String city;
	String state;
	String country;
	String phone;
	String email;
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public FiniteValue getAddressType() {
		return addressType;
	}
	public void setAddressType(FiniteValue addressType) {
		this.addressType = addressType;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
