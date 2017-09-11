package serverController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import connSQL.ConnectMysql;

/**
 * @name SelectSqlname.java
 *
 * @author hzy
 *
 * @version 创建时间：2016年9月20日下午4:44:41
 *
 *          类说明：根据解析的字符串判断传感器应该存入哪个表，并存入数据
 */
public class SelectSqlname {

	String fname;
	Connection con = null; // 定义一个MYSQL链接对象

	public void SelectSqlname(int ch,String date, double mdata, double tdata) {
		// 连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // MYSQL驱动
			System.out.println("成功加载MySQL驱动程序!");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hmlproj", "cug", "cug"); // 链接本地MYSQL
			if (!con.isClosed())
				System.out.println("数据库已连接!\n");

			String strsql = "select sqlname from allclient where ch = ?";
			PreparedStatement pstmt = con.prepareStatement(strsql);
			pstmt.setInt(1, ch);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				fname = rs.getString("sqlname");
				System.out.println("the fname is " + fname);
			}
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) { // 关闭声明
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String inSql = "insert into " + fname+ "(date,xdata,temp) values(?,?,?)";
		try {
			//检查表名是否已存在
            DatabaseMetaData meta = ( DatabaseMetaData)con.getMetaData();
            ResultSet res = meta.getTables(null, null, fname, null);
            PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(inSql);
            if(!res.next()){
            	JOptionPane.showMessageDialog(null, "数据表不存在！","错误",
    					JOptionPane.ERROR_MESSAGE);
            }else{//插入数据
			pstmt.setString(1, date);             
			pstmt.setDouble(2, mdata);     
			pstmt.setDouble(3, tdata);
			pstmt.executeUpdate();           //执行更新
			/*JOptionPane.showMessageDialog(null, "数据库插入成功！","提示",
					JOptionPane.INFORMATION_MESSAGE);
			*/
            }
            if (res != null) { // 关闭记录集
				try {
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) { // 关闭声明
				try {
					pstmt.close();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
