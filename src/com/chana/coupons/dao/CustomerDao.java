package com.chana.coupons.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;

import exception.DaoExeption;

public interface CustomerDao {
	// each method do the means of her name.
	boolean isCustomerExist(String email,String password) throws DaoExeption;
	void addCustomer(Customer customer)throws DaoExeption;
	void updateCustomer(Customer customer)throws DaoExeption;
	void deleteCustomer(int customerId)throws DaoExeption;
	ArrayList<Customer> getAllCustomers()throws DaoExeption;
	Customer getOneCustomer(int customerId) throws DaoExeption;
	ArrayList<Coupon> getAllCouponByCustomerID(int customerId)throws DaoExeption;
	Customer extractCustomerFromResult(ResultSet result)throws DaoExeption;
	ArrayList<Coupon> getAllCouponOfCustomerByMaxPrice(double maxPrice, int customerId)throws DaoExeption;
	int getCustomerIdByEmailAndPassword(String email, String password) throws DaoExeption;
	
}
