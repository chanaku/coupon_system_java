package com.chana.coupons.facade;

import java.util.ArrayList;

import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.dao.CompanyDao;
import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CustomerDao;

import exception.DaoExeption;
import exception.InvalidOparationExeption;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		super();
	}
	
	@Override
	public boolean login(String email, String password) throws InvalidOparationExeption {
		try {
		if (email == "admin@admin.com" && password == "admin") {
			return true;
		}
		}catch(Exception e) {
			throw new InvalidOparationExeption("error while trying to run login() of admin facade"
					+ " with email: "+email+" and password: "+password);
		}
		return false;
	}
	//checking if email and/or name already exist.
	public void addCompany(Company company) throws InvalidOparationExeption, DaoExeption {
		if(companyDao.isCompanyNameExist(company.getName())){
			throw new InvalidOparationExeption("can't add company "+company.getName()+
					" company with the same name already exist.");
		}
	 if(companyDao.isCompanyEmailExistByCompanyId(company.getEmail())) {
			throw new InvalidOparationExeption("can't add company "+company.getEmail()+" company with"
					+ " the same email address already exist.");
		} else {
		 companyDao.addCompany(company);
		}
		}
	
	//checking if not change the company
	public void updateCmpany(Company company) throws InvalidOparationExeption, DaoExeption {
		if(companyDao.isCompanyNameExistByCompanyId(company.getName(), company.getId())) {
			companyDao.updateCompany(company);
		}
		else {
			throw new InvalidOparationExeption("can't update company "+company.getName()+
					", update the company's name is invalid.");
		}
	}

	public void deleteCompany(int companyId) throws DaoExeption {
		companyDao.deleteCompany(companyId);
	}

	public ArrayList<Company> getAllCompaneis() throws DaoExeption {
		ArrayList<Company> companies = companyDao.getAllCompanies();
		return companies;
		

	}

	public Company getOneCompany(int companyId) throws DaoExeption {
		return companyDao.getOneCompany(companyId);
		
	}
	//checking if the customer is not exist before adding.
	public void addCustomer(Customer customer) throws InvalidOparationExeption, DaoExeption {
		if (customerDao.isCustomerExist(customer.getEmail(), customer.getPassword())) {
			throw new InvalidOparationExeption("customer "+customer.getFirstName()+" "+customer.getLastName()
					+" already exist.");
		}else {
			customerDao.addCustomer(customer);
		}
	}
//can't check if updated the customer's code
	public void updateCustomer(Customer customer) throws DaoExeption {
		customerDao.updateCustomer(customer);
	}
	//the purchases list already removed because the cascade.
	public void deleteCustomer(int customerId) throws DaoExeption {
		customerDao.deleteCustomer(customerId);
	}

	public ArrayList<Customer> getAllCustomers() throws DaoExeption {
		ArrayList<Customer> customers = customerDao.getAllCustomers();
		return customers;

	}

	public Customer getOneCustomer(int customerId) throws DaoExeption {
		Customer customer = customerDao.getOneCustomer(customerId);
		return customer;
		
	}
}
