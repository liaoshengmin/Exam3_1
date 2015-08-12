package com.hand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Exam3_1 {

	public static void main(String[] args) {
		System.out.print("请输入CountryID：");
		Scanner scaner = new Scanner(System.in);
		String countryId = scaner.next();
		
		Connection conn = new ConnectionMySql().makeConnectionMySql();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sql1="select country from country where country_id =?";
		String sql2 = "select city_id,city from city,country where country.country_id=? and city.country_id=country.country_id";
		
		try {
			PreparedStatement ps1 = conn.prepareCall(sql1);
			ps1.setString(1, countryId);
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				System.out.println("Country "+rs1.getString("country")+"所属的城市：");
			}
			
			
			PreparedStatement ps2 = conn.prepareCall(sql2);
			ps2.setString(1, countryId);
			rs2 = ps2.executeQuery();
			
			System.out.println("城市ID|城市名称");
			while(rs2.next()){
				System.out.println(rs2.getInt("city_id")+"|"+rs2.getString("city"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}


class ConnectionMySql {

	public Connection makeConnectionMySql(){

		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","");
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
}
