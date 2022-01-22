package com.chana.coupons.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.chana.coupons.dao.CouponsDao;
import com.chana.coupons.dao.CouponsDbDao;
import com.chana.coupons.utils.ConnectionPool;

public class CouponExpirationDailyJob implements Runnable{
	private CouponsDbDao couponsDao;
	private  boolean quit;
	
	public CouponExpirationDailyJob() {
		this.couponsDao = new CouponsDbDao();
		quit=false;
		
	}


	

	@Override
	public void run() {
		while(!quit) {
			deleteAllExpiredCoupons();
			Thread tread = new Thread();
			try {
				Thread.sleep(1000*86400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void deleteAllExpiredCoupons() {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String sqlStatement ="DELETE FROM COUPONS WHERE END_DATE < CURRENT_DATE";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//changed the method name to not run over stop() method of thread 
	public void stop() {
		quit = true;
	}
}
