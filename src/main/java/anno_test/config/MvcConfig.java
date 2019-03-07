package anno_test.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.yourbatis.Dbs;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // 설정파일
@EnableWebMvc // 스프링 MVC 설정
@ComponentScan(basePackages = { "anno_test.controller" }) // 컨트롤러 패키지 스캐닝
@MapperScan(basePackages = { "anno_test.maps" }) // 맵퍼스캐닝
public class MvcConfig {

	/* DB 커넥션 정보 설정 (HikariCP 설정) */
	static HikariConfig config = new HikariConfig();
	static HikariDataSource ds;
	static SqlSessionFactoryBean sqlSessionFactory;

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

	// Mybatis 관련 빈 설정

	@Bean(name = "sqlsession")
	public SqlSessionFactory sqlSessionFactory() throws Exception {

		sqlSessionFactory = new org.mybatis.spring.SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(ds);
		sqlSessionFactory.setTypeAliasesPackage("anno_test.maps");
		return (SqlSessionFactory) sqlSessionFactory.getObject();

	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(ds);
	}

	
	@Bean(name = "dbs")
	public Dbs getDbs() {
		try {
			return new Dbs();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
			return null;
		}

	}

	@Bean(name = "con")
	public Connection getConnetion() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
			return null;
		}

	}

	@Bean(name = "hello_anno")
	public ViewResolver viewResolver() {
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setViewClass(JstlView.class);
		irvr.setPrefix("/WEB-INF/view/");
		irvr.setSuffix(".jsp");

		return irvr;
	}
}
