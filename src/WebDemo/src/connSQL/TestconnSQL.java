package connSQL;

import org.junit.Test;
/**
 * 数据库的JUnit测试类
 * @author HZY
 *
 */
public class TestconnSQL {

	@Test
	public void testconnect() throws Exception{
		ConnectMysql cm = new ConnectMysql();
		cm.connect();
	}
	
}
