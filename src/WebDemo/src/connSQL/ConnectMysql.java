package connSQL;

import java.sql.*;

public class ConnectMysql 
{
	static Connection conn = null;  //定义一个MYSQL链接对象
	Statement stmt = null; //将执行的SQL语句
	ResultSet rs = null;
	
	String sql;
	String url="jdbc:mysql://localhost:3306/hmlproj";
    String user="cug";
    String passwd="cug";
    String driver="com.mysql.jdbc.Driver";
    
	public ConnectMysql(){}
	
    public void connect() throws Exception{
        try {
        
            Class.forName(driver).newInstance(); //加载MYSQL驱动
            System.out.println("成功加载MySQL驱动程序");
            
            conn = DriverManager.getConnection(url,user,passwd); //链接本地MYSQL
            
            if(!conn.isClosed()){
            	System.out.println("成功连接数据库!");
            }
        } catch (Exception e) {
            System.out.print("数据库连接出错:" + e.getMessage());
        }
    }
    
    public Connection getConnection(){
    	try {
			conn = DriverManager.getConnection(url,user,passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //链接本地MYSQL
		return conn;
	}
    
    /**
     * 查询数据
     * @param sql 
     * @return
     */
    public ResultSet executeQuery(String sql){
    	stmt = null;
    	rs = null;
    	try{
    		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
    				ResultSet.CONCUR_READ_ONLY);
    		rs = stmt.executeQuery(sql);
    	}catch(SQLException e){
    		System.err.println("查询数据"+e.getMessage());
    	}
    	return rs;
    }
    
    /**
     * 更新数据
     * @param sql
     */
    public void executeUpdate(String sql){
    	stmt = null;
    	rs = null;
    	try{
    		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
    				ResultSet.CONCUR_READ_ONLY);
    		stmt.executeUpdate(sql);
    	}catch(SQLException e){
    		System.err.println("更新数据"+e.getMessage());
    	}
    }
    
    /**
     * 释放对象
     */
    public void closeStmt(){
    	try{
    		stmt.close();
    	}catch(SQLException e){
    		System.err.println("释放对象"+e.getMessage());
    	}
    }
    
    public void closeConn(){
    	try{
    		conn.close();
    	}catch(SQLException ex){
    		System.err.println("释放对象"+ex.getMessage());
    	}
    }
    
    /**
     * 将字符串转换为简体汉字
     * @param str
     * @return
     */
    public static String toGBK(String str){
    	try{
    		if(str == null) {
    			str = "";
    		}else{
    			str = new String(str.getBytes("ISO-8859-1"),"GBK");
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return str;
    }
    
}