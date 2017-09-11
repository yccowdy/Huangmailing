package clientInf;

import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import connSQL.ConnectMysql;

/**
 * 数据处理的类
 * 接收从窗口输入的数据，编写SQL语句，最后创建对象访问数据库
 * @author Administrator
 *
 */
public class InfoBean {
	String sql;
	ResultSet rs = null;
	String port;        //端口号
	JComboBox<String> category ;    //类别
	String s_category;  //类别的String类型
	String sqlname;     //数据库名
	String ch;             //传感器在MCU32的连接位置
	String colName;     //列名
	String colValue;    //列值	
	CallableStatement call;//将执行的SQL存储过程


	//获取当前时间
		public static String getDate(){
		
			Calendar c = Calendar.getInstance();

			int year = c.get(Calendar.YEAR); 
			int month = c.get(Calendar.MONTH); 
			int date = c.get(Calendar.DATE); 
			int hour = c.get(Calendar.HOUR_OF_DAY); 
			int minute = c.get(Calendar.MINUTE); 
			int second = c.get(Calendar.SECOND); 
			String time =year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second;
			System.out.println(time);
			return time; 
		}
	
	/**
	 * 添加客户端信息
	 * @param clinet_port
	 * @param category2
	 * @param client_sqlname
	 * @param ch
	 */
	public void clientAdd(String client_port, String client_category, String client_sqlname,
			String client_ch){
		
		ConnectMysql connect = new ConnectMysql();
		this.port = client_port;
		this.s_category = client_category;
		this.sqlname = client_sqlname;
		this.ch = client_ch;
		
		if(port == null || port.equals("") || Integer.parseInt(port)<0){
			JOptionPane.showMessageDialog(null, "请输入端口信息！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else if(sqlname == null || sqlname.equals("")){
			JOptionPane.showMessageDialog(null, "请输入数据库表名！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else if(String.valueOf(ch) == null || String.valueOf(ch).equals("") 
				|| Integer.parseInt(ch) < 0){
			JOptionPane.showMessageDialog(null, "请输入正确的传感器连接端子号！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else{System.out.println("测试插入功能");
			//创建新的表
			try{
				//连接数据库
				Connection con = null; //定义一个MYSQL链接对象
	            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
	            System.out.println("成功加载MySQL驱动程序!");
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hmlproj", 
	            		"cug", "cug"); //链接本地MYSQL
	            if(!con.isClosed())
	            	System.out.println("数据库已连接!\n");

	            //检查表名是否已存在
	            DatabaseMetaData meta = ( DatabaseMetaData)con.getMetaData();
	            ResultSet rs = meta.getTables(null, null, sqlname, null);
	            Statement stmt = null;
	            
	            if(rs.next()){
	            	JOptionPane.showMessageDialog(null, "数据库表已存在！","错误",
	    					JOptionPane.ERROR_MESSAGE);
	            }else{//新建表
	            	System.out.println("新增信息为：\n"+port+"\n"+s_category+"\n"+sqlname+"\n"+ch);
	            	stmt = con.createStatement();
	            	
	            	String createTable = "create table "+sqlname+"(id INT(20) not null AUTO_INCREMENT,date varchar(20),xdata varchar(20),temp varchar(20),primary key(id))";
	            	System.out.println(createTable);
	            	int result = stmt.executeUpdate(createTable);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	                 if (result != -1) {
	                	 JOptionPane.showMessageDialog(null, "数据库表创建成功！");
	                	 
	                	 PreparedStatement psql;
	                     ResultSet res;
	                     //预处理添加数据，其中有5个参数--“？”
	                     psql = con.prepareStatement("insert into allClient values(?,?,?,?,?)");
	                     String s_time = getDate();
	                     psql.setString(1, s_time);             
	                     psql.setInt(2, Integer.valueOf(port));     
	                     psql.setString(3, s_category);
	                     psql.setString(4, sqlname);
	                     psql.setInt(5, Integer.valueOf(ch));
	                     psql.executeUpdate();           //执行更新
	                     System.out.println("数据插入成功！");
	                     
	                     //打印输出数据库内容
	                     sql = "select * from allClient";
	                     ResultSet rs1 = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
	                     System.out.println("date"+"\t"+"port"+"\t"+"category"+"\t"+"sqlname"+"\t"+"ch");
	                     while (rs1.next()) {
	       
	                    	 System.out.println(rs1.getString(1)+"\t"+rs1.getString(2)+"\t"+rs1.getString(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5));// 入如果返回的是int类型可以用getInt()
	                     }
	                 }else{
	                	 JOptionPane.showMessageDialog(null, "数据库表创建失败！","错误",
		 	    					JOptionPane.ERROR_MESSAGE);
	                 }
	            }
	            if (rs != null) { // 关闭记录集
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (stmt != null) { // 关闭声明
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (con != null) { // 关闭连接对象
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			   
			}catch(SQLException se){
				Logger.getLogger(InfoBean.class.getName()).log(Level.SEVERE, null, se);
			}catch (ClassNotFoundException ex) {              
				Logger.getLogger(InfoBean.class.getName()).log(Level.SEVERE, null, ex);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//connect.closeStmt();
				//connect.closeConn();
			}
		}
	}
	
	
	/**
	 * modify
	 */
	public void clientModify(String client_port, String client_category, String client_sqlname,
			String client_ch){
		
		ConnectMysql connect = new ConnectMysql();
		this.port = client_port;
		this.s_category = client_category;
		this.sqlname = client_sqlname;
		this.ch = client_ch;
		
		if(port == null || port.equals("")){
			JOptionPane.showMessageDialog(null, "请输入端口信息！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else if(sqlname == null || sqlname.equals("")){
			JOptionPane.showMessageDialog(null, "请输入数据库表名！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else if(String.valueOf(ch) == null || String.valueOf(ch).equals("") 
				|| Integer.parseInt(ch) < 0){
			JOptionPane.showMessageDialog(null, "请输入正确的传感器连接端子号！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}else{
			sql = "update allClient set port = ' "+ port + " ', " +
					"category = ' " + s_category + " ', " +
							"sqlname =' " + sqlname + " ' " +
									"where sqlname ='"+sqlname+"' ";
			
			try{
				connect.connect();
				connect.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"成功修改客户端信息！");
				
			}catch(Exception e){
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "更新失败！", "错误", 
						JOptionPane.ERROR_MESSAGE);
			}finally{
				connect.closeStmt();
				connect.closeConn();
			}
		}
	}
	
	/**
	 * delete
	 */
	public void clientDelete(String client_sqlname){
		
		ConnectMysql connect = new ConnectMysql();
		this.sqlname = client_sqlname;
		sql = "delete from allClient where sqlname = '"+sqlname+"' ";
		String sql0 = "drop table "+sqlname+"";
		
		try{
			connect.connect();
			connect.executeUpdate(sql);
			connect.executeUpdate(sql0);
			JOptionPane.showMessageDialog(null,"成功删除客户端信息！");
			
		}catch(Exception e){
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "删除失败！", "错误", 
					JOptionPane.ERROR_MESSAGE);
		}finally{
			connect.closeStmt();
			connect.closeConn();
		}
	}
	
	/**
	 * 根据数据表名查询信息
	 */
	public String[] InfoSearch(String client_sqlname){
		
		ConnectMysql connect = new ConnectMysql();
		this.sqlname = client_sqlname;
		String[] s = new String[4];
		sql = "select * from allClient where sqlname = " + sqlname + " ";
		try{
			connect.connect();
			rs = connect.executeQuery(sql);
			if(rs.next()){
				s[0] = rs.getString("port");
				s[1] = rs.getString("category");
				s[2] = rs.getString("sqlname");
				s[3] = rs.getString("ch");
			}else
				s = null;
		}catch(Exception e){
			
		}finally{
			connect.closeStmt();
			connect.closeConn();
		}
		return s;
	}
	
	/**
	 * 根据端口号查询
	 */
	public String[][] InfoAllSearch(String xh, String h){
		this.colName = xh;
		this.colValue = h;
		ConnectMysql connect = new ConnectMysql();
		String[][] sn = null;    //返回二维数组
		int row = 0;
		int i = 0;
		sql = "select * from allClient where " + colName + "  =  " + Integer.parseInt(colValue) +" ";
		try{
			connect.connect();
			rs = connect.executeQuery(sql);
			if(rs.last()){
				row = rs.getRow();
			}
			if(row == 0){
				sn = null;
			}else{
				sn = new String[row][4];
				rs.first();rs.previous();
				while(rs.next()){
					sn[i][0]=rs.getString("date"); //初始化数组内容
				    sn[i][1]=Integer.toString(rs.getInt("port"));
				    sn[i][2]=rs.getString("category");
				    sn[i][3]=rs.getString("sqlname");
				    sn[i][5]=Integer.toString(rs.getInt("ch"));
					i++;
				}
			}
		}catch(Exception e){}finally{
			connect.closeStmt();
			connect.closeConn();
		}
		return sn;
	}
	
	
	/**
	 * 获得所有信息
	 */
	public String[] getInfo(){
		String[] s = null;
		int row =0;
		int i = 0;
		ConnectMysql connect = new ConnectMysql();
		sql = "select * from allClient";
		try{
			connect.connect();
			rs = connect.executeQuery(sql);
			if(rs.last()){
				row = rs.getRow();
			}
			if(row == 0){
				s = null;
			}else{
				s = new String[row];
				rs.first();
				rs.previous();
				while(rs.next()){
					s[i] = rs.getString(i);
					i++;
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}finally{
			connect.closeStmt();
			connect.closeConn();
		}
		System.out.println(s);
		return s;
	}

}
