package com.chana.coupons.facade;

import com.chana.coupons.dao.CompanyDao;
import com.chana.coupons.dao.CompanyDbDao;
import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CouponsDbDao;
import com.chana.coupons.dao.CustomerDao;
import com.chana.coupons.dao.CustomerDbDao;

import exception.InvalidOparationExeption;

public abstract class ClientFacade {
	// protected - to allow permission to all the package and the heirs
	protected CustomerDao customerDao;
	protected CouponsDao couponsDao;
	protected CompanyDao companyDao;
	
	public ClientFacade() {
		this.customerDao =  new CustomerDbDao();
		this.couponsDao =  new CouponsDbDao();
		this.companyDao = new CompanyDbDao();
	}
	
	public abstract boolean login(String email, String password) throws InvalidOparationExeption;
	
}
