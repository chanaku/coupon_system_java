package com.chana.coupons.utils;

import com.chana.coupons.enums.ClientType;
import com.chana.coupons.facade.AdminFacade;
import com.chana.coupons.facade.ClientFacade;
import com.chana.coupons.facade.CompanyFacade;
import com.chana.coupons.facade.CustomerFacade;

import exception.DaoExeption;
import exception.InvalidOparationExeption;

public class LoginManager {
	private static LoginManager instance=null;
	
	public ClientFacade login(String email, String password, ClientType clientType)throws DaoExeption, InvalidOparationExeption {
		ClientFacade facade = null;
		if(clientType == ClientType.Admin) {

			facade = new AdminFacade();
		}else if(clientType == ClientType.Company) {

			facade = new CompanyFacade();
		}else if(clientType == ClientType.Customer) {

			facade = new CustomerFacade();
		}
		if(facade!=null&&facade.login(email, password)) {
			return facade;
		}
		throw new InvalidOparationExeption("cant do login");
		
		
	}
	public synchronized static LoginManager getInstance() {
		if(instance==null) {
			instance=new LoginManager();
		}
		return instance;
	}
}
