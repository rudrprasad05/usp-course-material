package data_access_layer;

import java.sql.*;
import business_layer.*;

public class DAcustomer {
	private Access_JDBC db;
	public DAcustomer(){
		db = new Access_JDBC();
	}

	public void add(BLcustomer cus) throws Exception{
		String sql = "";
		try{
			db.connect();
			Statement s = db.getConnect().createStatement();
			sql = sql + "INSERT INTO customer(id, lname, fname) ";
			sql = sql + "VALUES (" + cus.getCusId()+",'"+cus.getLName() + "','" + cus.getFName()+ "')";
			s.execute(sql);
			db.disconnect();
		}
		catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}

	public void delete(BLcustomer cus){
		String sql = "";
		try{
			db.connect();
			Statement s = db.getConnect().createStatement();
			sql = sql + "DELETE FROM customer ";
			sql = sql + "WHERE id = " + cus.getCusId()+"";
			s.executeUpdate(sql);
			db.disconnect();
		}
		catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}

	public void update(BLcustomer cus){
		String sql;
		try{
			db.connect();
			Statement s = db.getConnect().createStatement();

			sql = String.format(
				"UPDATE customer " +
				"SET lname = '%s', fname = '%s' " +
				"WHERE id = %s",
				cus.getLName(), cus.getFName(), cus.getCusId());
		
			s.executeUpdate(sql);
			db.disconnect();
		}
		catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}
}