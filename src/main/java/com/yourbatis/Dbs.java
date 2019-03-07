package com.yourbatis;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Dbs {

	/* DB 커넥션 정보 설정 (HikariCP 설정) */
	static HikariConfig config = new HikariConfig();
	static HikariDataSource ds;
	Connection c = null;
	Statement s = null;

	static {
		config.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setUsername("scott");
		config.setPassword("Tiger07#");
		// SQL 캐쉬 사용 여부
		config.addDataSourceProperty("cachePrepStmts", "true");
		// SQL 캐쉬 사이즈
		config.addDataSourceProperty("preStmtCacheSize", "250");
		// SQL 캐쉬 제한
		config.addDataSourceProperty("preStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}

	public void getConnection() {
		try {
			c = ds.getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	// DML을 전달받아 실행시키는 메서드
	public int executeUpdateSp(String pname) {
		// db연결
		getConnection();
		// sql 실행
		CallableStatement s = null;
		try {
			s = c.prepareCall(String.format("{CALL %s}", pname));
			// 프로시져실행
			return s.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return 0;

	}

	// DML을 전달받아 실행시키는 메서드
	public int executeUpdate(String sql) {
		// db연결
		getConnection();
		// sql 실행
		try {
			s = c.createStatement();
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return 0;

	}

	// 쿼리를 전달받아 결과 listMap으로 리턴
	public ArrayList<HashMap<String, String>> selectListMap(String sql) {
		// db연결
		getConnection();

		/* 3.2. SELECT SQL실행 */
		ResultSet rs = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("3. SQL실행 예외 : " + e.getMessage());
		}
		// ResultSet을 ArrayList화
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = null;
		try {
			while (rs.next()) {
				map = new HashMap<String, String>();
				// 열의 개수 가져오기
				ResultSetMetaData rm = rs.getMetaData();
				int c_count = rm.getColumnCount();
				for (int i = 1; i <= c_count; i++) {
					map.put(rm.getColumnName(i), rs.getString(i));
				}
				//
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("ResultSet을 ArrayList화 예외 : " + e.getMessage());
		}
		// close
		// return
		return list;
	}

}
