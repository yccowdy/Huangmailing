package tcp_GL1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connSQL.ConnectMysql;
/**
 * 此类为将水位计数据存入相应数据库表
 * @author He Li
 *
 */
public class TestGL1 
{
	/*public static void main(String[] args) {
		
			runTask();
		
	}
	*/
public static void runTask() {
	ConnectMysql connect = new ConnectMysql();
	final Connection con = connect.getConnection();
		final Runnable runnable = new Runnable() 
		{
			public void run() 
			{
				try
				{
					PreparedStatement preStatement;
					while (true) 
					{
						preStatement = con.prepareStatement("insert into shuiweiji values(?,?)");
						preStatement.setDate(1, new java.sql.Date((new java.util.Date()).getTime()));
						preStatement.setInt(2, new TCPClient().Data());
						System.out.println("数据进入数据库");
						preStatement.execute();
						System.out.println("写入MySQL数据库成功");     
						Thread.sleep(1000*10);					
					}					
				}catch (Exception e) {
					System.out.println("运行太快，写入不成功，请取出水位计电源重新启动！"+ e);
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
