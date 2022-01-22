package com.chana.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.beans.Customer;
import com.chana.coupons.utils.ConnectionPool;
import com.chana.coupons.utils.JdbcUtils;
import exception.DaoExeption;
//each method doing what her name means.
public class CustomerDbDao implements CustomerDao {
	public boolean isCustomerExist(String email,String password) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers where email =? and password = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCustomerExist() "
					+ "in couponDao with email: " + email+" and password: "+password);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
		
	}
	public void addCustomer(Customer customer) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			 connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into customers (FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) values(?,?,?,?)";
			 statement = connection.prepareStatement(sqlStatement);

				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getPassword());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new DaoExeption("error while trying to run addCustomer() "
						+ "in couponDao with customer id: " + customer.getId());
			}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	public void updateCustomer(Customer customer) throws DaoExeption {
		Connection connection =null;
		PreparedStatement statement = null;
		try {
			 connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update customers set FIRST_NAME=?,LAST_NAME=?,EMAIL=?,PASSWORD=? where id=?";
			 statement = connection.prepareStatement(sqlStatement);

				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getPassword());
				statement.setInt(5, customer.getId());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new DaoExeption("error while trying to run updateCustomer() "
						+ "in couponDao with customer id: " + customer.getId());
			}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
    public void deleteCustomer(int customerId) throws DaoExeption {
    	Connection connection = null;
    	PreparedStatement statement = null;
    	try {
			 connection = JdbcUtils.getConnection();
		String sqlStatement = "delete from customers where id=?";
			 statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerId);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run deleteCustomer() "
					+ "in couponDao with customer id: " + customerId);
			
		}if (connection != null) {
			// closing all resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
    public ArrayList<Customer> getAllCustomers() throws DaoExeption {
    	Connection connection=null;
		ArrayList<Customer> customers = new ArrayList<Customer>() ;
		PreparedStatement statement = null;
		ResultSet result = null;
		Customer customer = null;
    	try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers";
			statement = connection.prepareStatement(sqlStatement);
			result =statement.executeQuery();
			while(result.next()){
				customer = extractCustomerFromResult(result);
				customers.add(customer);
			}
    	} catch (Exception e) {
    		throw new DaoExeption("error while trying to run getAllCustomers() "
					+ "in couponDao");
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement,result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return customers;
   	}
    public Customer getOneCustomer(int customerId) throws DaoExeption {
    	Connection connection=null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Customer customer = null;
    	try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "select * from customers where id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1,customerId);
			result =statement.executeQuery();
			while(result.next()){
				customer = extractCustomerFromResult(result);
			}
			
    	} catch (Exception e) {
    		throw new DaoExeption("error while trying to run getOneCustomers() "
					+ "in couponDao with customer id: "+customerId );
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement,result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return customer;
    }
    public Customer extractCustomerFromResult(ResultSet result) throws DaoExeption {
		Customer customer = new Customer();
		try {
			customer = new Customer();
			customer.setId(result.getInt("id"));
			customer.setFirstName(result.getString("first_name"));
			customer.setLastName(result.getString("last_name"));
			customer.setEmail(result.getString("email"));
			customer.setPassword(result.getString("password"));
			customer.setCoupons(getAllCouponByCustomerID(result.getInt("id")));

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run extractCustomerFromResult() "
					+ "in couponDao with result: "+result);
		}
		return customer;
	}
    //this query is to join the coupons table, with category table and customer table. 
    //category table is for the param on extractFromResult method. 
    public ArrayList<Coupon> getAllCouponByCustomerID(int customerId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponList = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select cp.*, ct.name from coupons cp, categories ct,"
					+" customers_vs_coupons vs, customers cs, companies cmp"
					+" where ct.id=cp.CATEGORY_ID and cp.COMPANY_ID =cmp.id"
					+" and cp.id=vs.COUPON_ID and vs.CUSTOMER_ID=cs.id and cs.id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, customerId);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				couponList.add(extractCouponFromResult(result));
				
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponByCustomerID() "
					+ "in couponDao with customer id: "+customerId);
		} finally {
			if (connection != null) {
				ConnectionPool.closeResources(preparedStatement,result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		return couponList;
	}
    public ArrayList<Coupon> getAllCouponOfCustomerByMaxPrice(double maxPrice, int customerId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponList = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select cp.*, ct.name from coupons cp, categories ct,"
					+" customers_vs_coupons vs, customers cs, companies cmp"
					+" where ct.id=cp.CATEGORY_ID and cp.COMPANY_ID =cmp.id"
					+" and cp.id=vs.COUPON_ID and vs.CUSTOMER_ID=cs.id and cs.id=? and cp.price<=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setDouble(2, maxPrice);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				couponList.add(extractCouponFromResult(result));
			
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponByMaxPrice() "
					+ "in couponDao with customer id: "+customerId);
		} finally {
			if (connection != null) {
				ConnectionPool.closeResources(preparedStatement,result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		return couponList;
	}
    public Coupon extractCouponFromResult(ResultSet result) throws DaoExeption {
		Coupon coupon = null;
		try {
			// extract the data from db to coupon object
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
					+ "in customer Dao with result: "+result);
		}
		return coupon;

	}
    public int getCustomerIdByEmailAndPassword(String email, String password) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select id from customers where email =? and password = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return result.getInt("id");
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getCustomerIdByEmailAndPassword() in companyDao with"
					+ "email: " + email + ",password: " + password);
		} finally {
			if (connection != null) {
				// closing the resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return 0;
	}
}
