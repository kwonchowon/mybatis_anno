package anno_test.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface AnnoMap {

	// 5. 등록테스트
	@Insert(value = "{CALL P_INSERT_SPR2(#{s_name, mode=IN, jdbcType=NVARCHAR},#{s_age, mode=IN, jdbcType=INTEGER})}")
	@Options(statementType = StatementType.CALLABLE)
	public void doP_INSERT_SPR2(HashMap<String, Object> m);
	
	
//	// 4. 매개변수가 있는 select
//	@Select("SELECT * FROM SPR_TEST2 WHERE S_NUM > #{snum} ORDER BY S_NUM DESC")
//	public ArrayList<HashMap<String, String>> getListWhere(int snum);
//	
	// 3. 수정 select
	@Select("UPDATE SPR_TEST2 SET S_NAME = #{s_name} WHERE S_NUM = #{snum}")
	public HashMap<String, String> setUpdate(HashMap<String, Object> m);
	
	// 2. 매개변수가 있는 select
	@Select("SELECT * FROM SPR_TEST2 WHERE S_NUM > #{snum} ORDER BY S_NUM DESC")
	public ArrayList<HashMap<String, String>> getListWhere(int snum);

	// 1. 매개변수가 없는 select
	@Select("SELECT * FROM SPR_TEST2 ORDER BY S_NUM DESC")
	public ArrayList<HashMap<String, String>> getList();

}
