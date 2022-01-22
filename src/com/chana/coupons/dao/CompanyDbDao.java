package com.chana.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chana.coupons.beans.Category;
import com.chana.coupons.beans.Company;
import com.chana.coupons.beans.Coupon;
import com.chana.coupons.utils.ConnectionPool;
import com.chana.coupons.utils.JdbcUtils;

import exception.DaoExeption;
// each method doing what her name means.
public class CompanyDbDao implements CompanyDao {

	public void addCompany(Company company) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			 connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into companies (NAME,EMAIL,PASSWORD) values(?,?,?)";
			 statement = connection.prepareStatement(sqlStatement);

			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run addCompany() in companyDao with" + "company email: "
					+ company.getEmail());
		}if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public boolean isCompanyExist(String email, String password) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where email =? and password = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCompanyExist() in companyDao with" + "email: " + email
					+ ",password: " + password);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
	}

	public int getCompanyIdByEmailAndPassword(String email, String password) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select id from companies where email =? and password = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return result.getInt("id");
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getCompanyIdByEmailAndPassword() in companyDao with"
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

	public void updateCompany(Company company) throws DaoExeption {
		Connection connection = null;
		PreparedStatement statement = null;
//		System.out.println(company.getId());
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update companies set NAME=?,EMAIL=?,PASSWORD=? where id=?";
			 statement = connection.prepareStatement(sqlStatement);

			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, company.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run updateCompany() in companyDao with" + "email: "
					+ company.getEmail() + ",password: " + company.getPassword());
		}
			if (connection != null) {
				// closing the resources
				ConnectionPool.closeResources(statement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	

	

	public void deleteCompany(int companyId) throws DaoExeption {
			Connection connection = null;
			PreparedStatement statement = null;
		try {
			 connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "delete companies, coupons from companies left join coupons on companies.id=coupons.company_id where companies.id=?";
			 statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyId);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run deleteCompany() in companyDao with" + "company_id: " + companyId);
		}if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(statement);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public ArrayList<Company> getAllCompanies() throws DaoExeption {
		Connection connection = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		PreparedStatement statement = null;
		ResultSet result = null;
		Company company = new Company();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies";
			statement = connection.prepareStatement(sqlStatement);
			result = statement.executeQuery();
			while (result.next()) {
				company = extractCompanyFromResult(result);
				companies.add(company);
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCompanies() in companyDao");
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(statement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		
		return companies;

	}

	public Company getOneCompany(int companyId) throws DaoExeption {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Company company = new Company();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, companyId);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				company= extractCompanyFromResult(result);
			}
		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run getOneCompany() in companyDao with" + "company_id: " + companyId);
		} finally {
			if (connection != null) {
				// closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return company;
	}
	

	public Company extractCompanyFromResult(ResultSet result) throws DaoExeption {
		Company company = new Company();
		try {
			company.setId(result.getInt("id"));
			company.setName(result.getString("name"));
			company.setEmail(result.getString("email"));
			company.setPassword(result.getString("password"));
			company.setCoupons(getAllCouponByCompanyID(result.getInt("id")));

		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run extractCompanyFromResult() in companyDao with" + "result: " + result);
		}
		return company;
	}

	public ArrayList<Coupon> getAllCouponByCompanyID(int companyId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponList = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select coupons.*, categories.name"
					+ " from coupons left join categories on coupons.CATEGORY_ID = categories.id where coupons.company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, companyId);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				couponList.add(extractCouponFromResult(result));

			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run getAllCouponByCompanyId() in companyDao with"
					+ "company_id: " + companyId);
		} finally {
			if (connection != null) {
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}

		}
		return couponList;
	}

	public boolean isCouponTitleExistByCompanyId(String title, int companyId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where title =? and company_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, title);
			preparedStatement.setInt(2, companyId);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
		throw new DaoExeption("error while trying to run isCouponTitleExistByCompanyId() in companyDao with"
					+ "string title: " + title + ", company_id: " + companyId);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
	}

	public boolean isCompanyNameExist(String name) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where name =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, name);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run isCompanyNameExist() in companyDao with" + "name: " + name);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
	}
	public boolean isCompanyNameExistOnOtherCompany(String name, int companyId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where name =? and not id =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, companyId);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption(
					"error while trying to run isCompanyNameExist() in companyDao with" + "name: " + name);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
	}
	public boolean isCompanyEmailExistByCompanyId(String email) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where email =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, email);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCompanyEmailExistByCompanyId() in companyDao with"
					+ "email: " + email);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;
	}

	public boolean isCompanyNameExistByCompanyId(String name, int companyId) throws DaoExeption {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select name from companies where id =? ";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setInt(1, companyId);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				String name2 = result.getString("name");
				if (name.equals(name2)) {
					return true;
				}
			}
		} catch (Exception e) {
			throw new DaoExeption("error while trying to run isCompanyNameExistByCompanyId() in companyDao with"
					+ "name: " + name + ", company_id:" + companyId);
		} finally {
		}
		if (connection != null) {
			// closing the resources
			ConnectionPool.closeResources(preparedStatement, result);
			ConnectionPool.getInstance().restoreConnection(connection);
		}
		return false;

	}
	public Coupon extractCouponFromResult(ResultSet result) throws DaoExeption {
		Coupon coupon = null;
		try {
			// extract the data from db to coupon object, with left join statement to table "categories", 
			//to extract the name of category
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
					+ "in companyDao with result: "+result);
		}
		return coupon;

	}
}
