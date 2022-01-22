package com.chana.coupons.utils;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import java.text.SimpleDateFormat;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.enums.ClientType;
import com.chana.coupons.facade.AdminFacade;
import com.chana.coupons.facade.CompanyFacade;
import com.chana.coupons.facade.CustomerFacade;
import com.chana.coupons.job.CouponExpirationDailyJob;

import exception.DaoExeption;
import exception.InvalidOparationExeption;

public class Test {
	public static void testAll() throws ParseException, InvalidOparationExeption {
		try {
			
			//part a
//			//start the daily job to remove once each day the expired coupons.
//			CouponExpirationDailyJob job = new CouponExpirationDailyJob();
//			Thread daily = new Thread(job);
//			daily.start();
//			
//			//part b
//			//login with Admin user:
			AdminFacade admin =(AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Admin);
//			admin.addCustomer(null);
//			Company company3 = new Company("gil hachzakot", "gil@gil.com", "123456");
//			admin.addCompany(company3);
//			Company company1 = new Company(4, "gil hachzakot", "gil@gil.com", "123456");
//			admin.updateCmpany(company1);
//			admin.deleteCompany(5);
//			admin.getAllCompaneis();
//			admin.getOneCompany(2);
//			Customer customer3 = new Customer("lea", "silon", "lsil@gmail.com", "123456");
//			Customer customer2 = new Customer("dvora", "mali", "dvom@gmail.com", "123456");
//			admin.addCustomer(customer3);
//			admin.addCustomer(customer2);
			Customer customer1 = new Customer("tamir", "anan", "tam@tam", "123456");
			admin.addCustomer(customer1);
//			admin.deleteCustomer(1);
//			admin.getAllCustomers();
//			admin.getOneCustomer(2);
//			//login with company user:
//			CompanyFacade company =(CompanyFacade) LoginManager.getInstance().login("lala@la.com", "123", ClientType.Company);
//			Coupon coupon1 = new Coupon(3, Category.FOOD, "coupon l'shana tova", "buy water get koka kola",
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-17").getTime()),
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2027-08-17").getTime()), 
//					15, 85, "gfdgfdgfdgfd");
//			Coupon coupon2 = new Coupon(3, Category.VACATION, "eshet tours", "trip to barzelona 25% discount",
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-17").getTime()),
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-20").getTime()), 
//					15, 3500, "gfdgfdgjghgdfgsfdfdgfd");
//			Coupon coupon3 = new Coupon(5,4, Category.RESTAURANT, "your trip", "barzelona 25% discount!",
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-17").getTime()),
//					new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-20").getTime()), 
//					15, 3499, "gfdgfdgjghgdfgsfdfdgfd");
//			company.addCoupon(coupon1);
//			company.addCoupon(coupon2);
//			company.updateCoupon(coupon3);
//			company.deleteCoupon(1);
//			company.getCompanyCoupons();
//			company.getCompanyCoupons(Category.VACATION);
//			company.getCompanyCoupons(100.00);
//			company.getCompanyeDtails();
//			
//			//change the login details
//			
//			CustomerFacade customer =(CustomerFacade) LoginManager.getInstance().login("shir@gmail.com", "123456", ClientType.Customer);
//			
//			customer.purchaseCoupon(coupon1);
//			customer.getCustomerCoupons();
//			customer.getCustomerCoupons(Category.FOOD);
//			customer.getCustomerCoupons(3499);
//			customer.getCustomerDetails();
//			job.stop();
//			Thread.interrupted();
		}catch(DaoExeption e){
			e.printStackTrace();
		}
	}
	
}
