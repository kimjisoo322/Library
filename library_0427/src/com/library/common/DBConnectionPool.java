package com.library.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionPool {

	public DBConnectionPool() {
		// TODO Auto-generated constructor stub
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		
		// 커넥션 풀 생성
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			conn = ds.getConnection();
			
		} catch (NamingException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return conn;
	}
	
		/*
		 * 	자원 반납 (Connection, Statement, ResultSet
		 * 
		 * */
	public static void close(Connection conn) {
			try {
				if(conn != null && !conn.isClosed())conn.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
	}
	
	public static void close(Connection conn, Statement stsmt) {
		try {
			if(stsmt != null && !stsmt.isClosed())stsmt.close();
			if(conn != null && !conn.isClosed())conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	public static void close(Connection conn, Statement stsmt, ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed())rs.close();
			if(stsmt != null && !stsmt.isClosed())stsmt.close();
			if(conn != null && !conn.isClosed())conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
