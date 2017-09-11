package ylj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import connSQL.ConnectMysql;
/**
 * 此类为将雨量计数据存入相应数据库表
 * @author He Li
 *
 */
public class Test_YuLiangJi {
	
	/*public static void main(String[] args) {

		runTask();

	}*/

	public static void runTask() {

		ConnectMysql connect = new ConnectMysql();
		final Connection con = connect.getConnection();
		
		final Runnable runnable = new Runnable() {
			public void run() {
				try {
					PreparedStatement preStatement;
					while (true) {
						preStatement = con.prepareStatement("insert into yuliangji values(?,?)");
						preStatement.setDate(1, new java.sql.Date((new java.util.Date()).getTime()));
						preStatement.setInt(2, new Test_YLJ().Yuliang());
						System.out.println("数据进入数据库");
						preStatement.execute();
						System.out.println("写入mysql数据库成功");
						// Thread.sleep(1000*2);
					}
				} catch (Exception e) {
					System.out.println("运行太块，写入不成功:"+ e);
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
