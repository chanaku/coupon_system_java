package com.chana.coupons.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Coupon;

import exception.DaoExeption;

public interface CouponsDao {
	// each method do the means of her name.
	void addCoupon(Coupon coupon) throws DaoExeption;
	void updateCoupon(Coupon coupon)throws DaoExeption;
	void deleteCoupon(int couponId)throws DaoExeption;
	ArrayList<Coupon> getAllCoupons()throws DaoExeption;
	Coupon extractCouponFromResult(ResultSet result) throws DaoExeption;
	ArrayList<Coupon> getAllCouponsByCategory(Category category)throws DaoExeption;
	ArrayList<Coupon> getAllCouponsByCustomerId(int customerId)throws DaoExeption;
	ArrayList<Coupon> getAllCouponsByCustomerIdAndCategoryId(int customerId,Category category)throws DaoExeption;
	boolean isCouponQuantityIsZero(int couponId)throws DaoExeption;
	boolean isCouponIsExpired(int couponId)throws DaoExeption;
	Coupon getOneCoupon(int couponId) throws DaoExeption;
	void purchasedCoupon(int customerId,int couponId)throws DaoExeption; 
	boolean isCustomeralreadyPurchasedThisCoupon(int customerId,int couponId)throws DaoExeption;
	ArrayList<Coupon> getAllCompanyCouponsByMaxPrice(double maxPrice, int companyid) throws DaoExeption; //unchecked
}
