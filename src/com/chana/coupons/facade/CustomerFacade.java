package com.chana.coupons.facade;

import java.util.ArrayList;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.dao.CompanyDao;
import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CustomerDao;

import exception.DaoExeption;
import exception.InvalidOparationExeption;

public class CustomerFacade extends ClientFacade{

	public CustomerFacade() {
		super();
	}
	private int customerId;
	@Override
	public boolean login(String email, String password) throws InvalidOparationExeption {
		try {
		if(customerDao.isCustomerExist(email, password)) {
			customerId = customerDao.getCustomerIdByEmailAndPassword(email, password);
			return true;
		}
		}catch(Exception e) {
			throw new InvalidOparationExeption("error while trying to run login() of customer facade"
					+ " with email: "+email+" and password: "+password);
		}
		return false; 
	}
	
	public void purchaseCoupon(Coupon coupon) throws InvalidOparationExeption, DaoExeption {
		if(couponsDao.isCustomeralreadyPurchasedThisCoupon(customerId, coupon.getId())) {
			
			throw new InvalidOparationExeption("you already have this coupon- "+coupon.getTitle()
					+ " Each coupon can be purchased only once ");
			
		}if(couponsDao.isCouponQuantityIsZero(customerId)) {
			
			throw new InvalidOparationExeption("coupon "+coupon.getTitle()+" out of stock");
			
		}if(couponsDao.isCouponIsExpired(customerId)) {
			
			throw new InvalidOparationExeption("coupon "+coupon.getTitle()+" has been expired");
		}
		else {
			couponsDao.purchasedCoupon(customerId, coupon.getId());
		}
	}
	public ArrayList<Coupon> getCustomerCoupons() throws DaoExeption{
		ArrayList<Coupon> coupons = couponsDao.getAllCouponsByCustomerId(customerId);
		return coupons;
		
	}
	public ArrayList<Coupon> getCustomerCoupons(Category category) throws DaoExeption{
		ArrayList<Coupon> coupons = couponsDao.getAllCouponsByCustomerIdAndCategoryId(customerId, category);
		return coupons;
		
	}
	
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws DaoExeption{
		ArrayList<Coupon> coupons = customerDao.getAllCouponOfCustomerByMaxPrice(maxPrice, customerId);
		return coupons;
		
	}
	
	public Customer getCustomerDetails() throws DaoExeption {
		Customer customer = customerDao.getOneCustomer(customerId);
		return customer;
		
		
	}
	
}