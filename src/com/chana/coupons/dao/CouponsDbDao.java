package com.chana.coupons.dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.utils.ConnectionPool;
import com.chana.coupons.utils.JdbcUtils;

import exception.DaoExeption;
//each method doing what her name means.
public class CouponsDbDao implements CouponsDao {
	public void addCoupon(Coupon coupon) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into coupons (COMPANY_ID,CATEGORY_ID,TITLE,DESCRIPTION,START_DATE,END_DATE,AMOUNT,PRICE,IMAGE) values(?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sqlStatement);

			statement.setInt(1, coupon.getCompanyId());
			statement.setInt(2, coupon.getCategory().getId());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStartDate());
			statement.setDate(6, coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run addCoupon() in couponDao with coupon id: " + coupon.getId());
		}
		if (connection != null) {
			// closing all resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public void updateCoupon(Coupon coupon) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update coupons set COMPANY_ID =?,CATEGORY_ID=?,TITLE=?,DESCRIPTION=?,START_DATE=?,END_DATE=?,AMOUNT=?,PRICE=?,IMAGE=? where id=?";
			statement = connection.prepareStatement(sqlStatement);

			statement.setInt(1, coupon.getCompanyId());
			statement.setObject(2, coupon.getCategory());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStartDate());
			statement.setDate(6, coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.setInt(10, coupon.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run updateCoupon() in couponDao with coupon id: " + coupon.getId());
		}
		if (connection != null) {
			// closing all resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public void deleteCoupon(int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			 connection = JdbcUtils.getConnection();
			String sqlStatement = "delete from coupons where id=?";
			 statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponId);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run deleteCoupon() in couponDao with coupon id: "
					+couponId);
		}if (connection != null) {
			// closing all resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public ArrayList<Coupon> getAllCoupons() throws DaoExeption {
		Connection connection = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = new Coupon();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name, categories.name from coupons"
					+ " left join categories on coupons.category_id = categories.id";
			statement = connection.prepareStatement(sqlStatement);
			result = statement.executeQuery();
			while (result.next()) {
				coupon = extractCouponFromResult(result);
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCoupons() in couponDao with coupon id");
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupons;

	}

	public ArrayList<Coupon> getAllCouponsByCategory(Category category) throws DaoExeption {
		Connection connection = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = null;
		System.out.println(category);
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where category_id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, category.getId());
			result = statement.executeQuery();
			while (result.next()) {
				coupon = extractCouponFromResult(result);
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponsByCategory() in couponDao with category id: "+category.getId());
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}

		return coupons;

	}

	public Coupon extractCouponFromResult(ResultSet result) throws DaoExeption {
		Coupon coupon = null;
		try {
			// extract the data from db to coupon object, from table coupons and categories.
			coupon = new Coupon();
			coupon.setId(result.getInt("id"));
			coupon.setCompanyId(result.getInt("company_id"));
			coupon.setCategory(Category.valueOf(result.getString( "name").toUpperCase()));
			coupon.setTitle(result.getString("title"));
			coupon.setDescription(result.getString("description"));
			coupon.setStartDate(result.getDate("start_date"));
			coupon.setEndDate(result.getDate("end_date"));
			coupon.setAmount(result.getInt("amount"));
			coupon.setPrice(result.getDouble("price"));
			coupon.setImage(result.getString("image"));

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run extractCouponFromResult() "
					+ "in couponDao with result: "+result);
		}
		return coupon;

	}
	public Coupon extractCouponOnlyFromResult(ResultSet result) throws DaoExeption {
		Coupon coupon = null;
		try {
			// extract the data from db to coupon object, only from coupon table.
			coupon = new Coupon();
			coupon.setId(result.getInt("id"));
			coupon.setCompanyId(result.getInt("company_id"));
			coupon.setTitle(result.getString("title"));
			coupon.setDescription(result.getString("description"));
			coupon.setStartDate(result.getDate("start_date"));
			coupon.setEndDate(result.getDate("end_date"));
			coupon.setAmount(result.getInt("amount"));
			coupon.setPrice(result.getDouble("price"));
			coupon.setImage(result.getString("image"));

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run extractCouponFromResult() "
					+ "in couponDao with result: "+result);
		}
		return coupon;

	}

	public ArrayList<Coupon> getAllCouponsByCustomerId(int customerId) throws DaoExeption {
		Connection connection = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons left join customers_vs_coupons on coupons.id= customers_vs_coupons.coupon_id where"
					+ " customers_vs_coupons.customer_id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerId);
			result = statement.executeQuery();
			while (result.next()) {
				coupon = extractCouponOnlyFromResult(result);
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponsByCustomerId() "
					+ "in couponDao with customer id: "+customerId);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupons;
	}
	public ArrayList<Coupon> getAllCompanyCouponsByMaxPrice(double maxPrice, int companyid) throws DaoExeption {
		Connection connection = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where amount<=? and company_id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setDouble(1, maxPrice);
			statement.setDouble(2, companyid);
			result = statement.executeQuery();
			while (result.next()) {
				coupon = extractCouponFromResult(result);
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCompanyCouponsByMaxPrice() "
					+ "in couponDao with company id: "+companyid +" and max price= "+maxPrice);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupons;
	}

	public ArrayList<Coupon> getAllCouponsByCustomerIdAndCategoryId(int customerId, Category category) throws DaoExeption {
		Connection connection = null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name from coupons"
					+ " left join categories on coupons.category_id = categories.id"
					+ " left join customers_vs_coupons"
					+ " on coupons.id=customers_vs_coupons.coupon_id where customers_vs_coupons.customer_id=? and coupons.category_id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerId);
			statement.setInt(2, category.getId());
			result = statement.executeQuery();
			while (result.next()) {
				coupon = extractCouponFromResult(result);
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponsByCustomerIdAndCategoryId() "
					+ "in couponDao with customer id: "+customerId);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupons;
	}

	public boolean isCouponQuantityIsZero(int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name from coupons"
			+ " left join categories on coupons.category_id = categories.id where coupons.id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, couponId);

			result = preparedStatement.executeQuery();
			while(result.next()) {
			Coupon coupon = extractCouponFromResult(result);
			if (coupon.getAmount() == 0) {
				return true;
			}
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCouponQuantityIsZero() "
					+ "in couponDao with cuopon id: "+couponId);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;

	}

	public boolean isCouponIsExpired(int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name from coupons"
					+ " left join categories on coupons.category_id = categories.id where coupons.id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, couponId);

			result = preparedStatement.executeQuery();
			while(result.next()) {
			Coupon coupon = extractCouponFromResult(result);
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			
			if (coupon.getEndDate().before(date)) {
				return true;
			}
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCouponIsExpired() "
					+ "in couponDao with cuupon id: "+couponId);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;

	}

	public void purchasedCoupon(int customerId, int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			System.out.println("customer id: "+customerId +" coupon id: "+couponId);
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, couponId);
			result = preparedStatement.executeQuery();
			System.out.println("select statement done"+result);
			while(result.next()) {
			Coupon coupon = extractCouponOnlyFromResult(result);
			System.out.println(coupon);
			sqlStatement = "update coupons set amount= amount-1 where id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, couponId);
			preparedStatement.executeUpdate();
			sqlStatement = "insert into customers_vs_coupons(CUSTOMER_ID,COUPON_ID)values(?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setInt(2, couponId);
			preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run purchasedCoupon() "
					+ "in couponDao with customer id: "+customerId+" and coupon id: "+couponId);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	public Coupon getOneCoupon(int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Coupon coupon = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name from coupons"
					+ " left join categories on coupons.category_id = categories.id where coupons.id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponId);
			result = statement.executeQuery();
			while(result.next()) {
			coupon = extractCouponFromResult(result);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getOneCoupon() "
					+ "in couponDao with  coupon id: "+couponId);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupon;
	}

	public boolean isCustomeralreadyPurchasedThisCoupon(int customerId, int couponId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers_vs_coupons where customer_id =? and coupon_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setInt(2, couponId);

			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCustomeralreadyPurchasedThisCoupon() "
					+ "in couponDao with customer id: "+customerId+" and coupon id: "+couponId);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;

	}
}
