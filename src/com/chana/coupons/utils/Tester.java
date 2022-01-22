package com.chana.coupons.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.dao.CompanyDao;
import com.chana.coupons.dao.CompanyDbDao;
import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CouponsDbDao;
import com.chana.coupons.dao.CustomerDbDao;
import com.chana.coupons.enums.ClientType;
import com.chana.coupons.facade.AdminFacade;
import com.chana.coupons.facade.CompanyFacade;
import com.chana.coupons.facade.CustomerFacade;

import exception.DaoExeption;
import exception.InvalidOparationExeption;

public class Tester {

	public static void main(String[] args) throws DaoExeption, InvalidOparationExeption, ParseException {
		
		try {
//			CompanyDbDao comDbDao = new CompanyDbDao();
//			Company company = new Company(1, "shalar", "fdsjkfjds", "123456", null);
//			comDbDao.addCompany(company);
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
//			Company company1 = new Company("kalkal", "zom@zom.com.il", "123456");
			
//			CompanyDbDao companyDao = new CompanyDbDao();
			Company company = new Company(8,"shall", "shal@shalshalim", "123456");
			CouponsDbDao coupDbDao = new CouponsDbDao();
			AdminFacade admin9 = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Admin);
			CustomerFacade customer9 = (CustomerFacade) LoginManager.getInstance().login("tahel@gmail.com", "123456", ClientType.Customer);
			CompanyFacade company9 = (CompanyFacade) LoginManager.getInstance().login("lala@la.com", "123", ClientType.Company);
//			CompanyFacade company9= (CompanyFacade)LoginManager.getInstance().login(null, null, null);
			Coupon copon = new Coupon(3, Category.VACATION, "trips ", "trip to hazbany",
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-17").getTime()),
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-20").getTime()), 
					15, 3500, "gfdgfdgjghgdfgsfdfdgfd");
//			company9.addCoupon(copon);
			Coupon coupon9 = coupDbDao.getOneCoupon(15);
			company9.updateCoupon(copon);
			admin9.updateCmpany(company);
//			Customer customer = new Customer("gil", "nave", "giln@gmail.com", null);
//			customDao.addCustomer(customer);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
	}
		
	}


