package com.chana.coupons.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;

import exception.DaoExeption;

public interface CompanyDao {
	// each method do the means of her name.
	void addCompany(Company company) throws DaoExeption;
	boolean isCompanyExist(String email, String password) throws DaoExeption;
	void updateCompany(Company company)throws DaoExeption;
	 void deleteCompany(int companyId)throws DaoExeption;
	 ArrayList<Company> getAllCompanies()throws DaoExeption;
	 Company getOneCompany(int companyId)throws DaoExeption;
	 Company extractCompanyFromResult(ResultSet result)throws DaoExeption;
	 ArrayList<Coupon> getAllCouponByCompanyID(int companyId)throws DaoExeption;
	int getCompanyIdByEmailAndPassword(String email, String password)throws DaoExeption;
	boolean isCouponTitleExistByCompanyId(String title, int companyId)throws DaoExeption;
	boolean isCompanyNameExist(String name)throws DaoExeption;
	boolean isCompanyEmailExistByCompanyId(String email)throws DaoExeption;
	boolean isCompanyNameExistByCompanyId(String name, int companyId)throws DaoExeption; 
	boolean isCompanyNameExistOnOtherCompany(String name, int companyId) throws DaoExeption;
}
