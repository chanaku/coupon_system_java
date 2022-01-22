package com.chana.coupons.facade;

import java.util.ArrayList;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.dao.CompanyDao;
import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CustomerDao;

import exception.DaoExeption;

import exception.InvalidOparationExeption;

public class CompanyFacade extends ClientFacade{

	public CompanyFacade() {
		super();
	
	}

	private int companyId;
	@Override
	public boolean login(String email, String password) throws InvalidOparationExeption {
		try {
		if(companyDao.isCompanyExist(email, password)) {
			companyId=companyDao.getCompanyIdByEmailAndPassword(email, password);
			return true;
		}
		}catch(Exception e) {
			throw new InvalidOparationExeption("error while trying to run login() of company facade"
					+ " with email: "+email+" and password: "+password);
		}
		return false;
	}
	//checking if the coupon title is not exist for the exist company
	public void addCoupon(Coupon coupon) throws InvalidOparationExeption, DaoExeption {
		
		if(companyDao.isCouponTitleExistByCompanyId(coupon.getTitle(), companyId)) {
		throw new InvalidOparationExeption(String.format("company %d has tried to add coupon with existing title",companyId));
		}else {
			couponsDao.addCoupon(coupon);
		}
	}
	//is impossible to check update of company code and coupon code
	public void updateCoupon(Coupon coupon) throws DaoExeption {
		couponsDao.updateCoupon(coupon);
	}
	//purchases history remove also because the cascade
	public void deleteCoupon(int couponId) throws DaoExeption {
		couponsDao.deleteCoupon(couponId);
	}
	public ArrayList<Coupon> getCompanyCoupons() throws DaoExeption{
		ArrayList<Coupon> coupons = couponsDao.getAllCoupons();
		return coupons;
		
		
	}
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws DaoExeption{
		ArrayList<Coupon> coupons = couponsDao.getAllCouponsByCategory(category);
		return coupons;
		
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws DaoExeption{
		ArrayList<Coupon> coupons = couponsDao.getAllCompanyCouponsByMaxPrice(maxPrice, companyId);
		return coupons;
		
	}
	public Company getCompanyeDtails() throws DaoExeption {
		Company company = companyDao.getOneCompany(companyId);
		return company;
		
	}
}
