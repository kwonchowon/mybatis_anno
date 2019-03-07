package anno_test.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yourbatis.Dbs;

import anno_test.maps.AnnoMap;

@org.springframework.stereotype.Controller
public class AnnoController {
	@Autowired
	Connection con;
	@Autowired
	Dbs dbs;
	@Autowired
	AnnoMap amap;

	// 마이바티스 테스트

	// 3. 등록테스트

//	@RequestMapping(value = { "/get_listt" }, method = RequestMethod.GET)
//	public String getListWhere(Model m) {
//		m.addAttribute("message", amap.getListWhere(45));
//		return "get_listt";
//	}
//	
//	//4. 삭제테스트
//	
//	
//	@RequestMapping(value = { "/get_listttttt" }, method = RequestMethod.GET)
//	public String getListWhere(Model m) {
//		m.addAttribute("message", amap.getListWhere(45));
//		return "get_listtttt";
//	}

	// 3. 수정테스트

	@RequestMapping(value = { "/set_update" }, method = RequestMethod.GET)
	public String setUpdate(Model m) {
		HashMap<String, Object> mm = new HashMap<String, Object>() {
				{
					put("s_name","여진구");
					put("s_num", 11 );
				}
		};
		amap.setUpdate(mm);
		m.addAttribute("message", amap.getList());
		return "get_list";
	}

	// 2. 매개변수 있는 SELECT
	@RequestMapping(value = { "/get_listt" }, method = RequestMethod.GET)
	public String getListWhere(Model m) {
		m.addAttribute("message", amap.getListWhere(45));
		return "get_listt";
	}

	// 1. SELECT
	@RequestMapping(value = { "/get_list" }, method = RequestMethod.GET)
	public String getList(Model m) {
		m.addAttribute("message", amap.getList());
		return "get_list";
	}

	// 히카리씨피 테스트
	@RequestMapping(value = { "/test_yourbatis" }, method = RequestMethod.GET)
	public String testYourbatis(Model m) {
		// ResultSet을 ArrayList화
		ArrayList<HashMap<String, String>> list = dbs.selectListMap("SELECT * FROM spr_test2 ORDER BY S_NUM DESC");
		// return
		m.addAttribute("message", list);
		return "test_yourbatis";
	}

	@RequestMapping(value = { "/test_hikari" }, method = RequestMethod.GET)
	public String testHikari(Model m) {
		/* 3.2. SELECT SQL실행 */
		Statement s = null;
		ResultSet rs = null;
		try {
			s = con.createStatement();
			rs = s.executeQuery("SELECT * FROM spr_test2 ORDER BY S_NUM DESC");
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
		// return
		m.addAttribute("message", list);
		return "test_hikari";
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(ModelMap m) {
		System.out.println("homePage");
		m.addAttribute("message", "HI MESSAGE");
		return "home";
	}
}
