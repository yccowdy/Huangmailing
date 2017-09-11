package serverController;

import org.junit.Test;

/**
 *@name TestSelect.java
 *
 *@author hzy
 *
 *@version 创建时间：2016年9月24日下午11:06:08
 *
 *类说明
 */
public class TestSelect {

	@Test
	public void TestSelect(){
		SelectSqlname ssn = new SelectSqlname();
		String s = "2016-9-24 23:59";
		ssn.SelectSqlname(11,s,20,10);
	}
}
