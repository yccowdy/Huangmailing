package clientInf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *@name ResultInfo.java
 *
 *@author hzy
 *
 *@version 创建时间：2016年9月12日下午3:50:19
 *
 *类说明
 */
public class ResultInfo extends JFrame{

	JLabel jLabel = new JLabel();
	JButton jBExit = new JButton();
	JScrollPane js;
	JTable jt;
	String sqlName;
	String[] 列名 = {"端口号","传感器类型","数据库表名","连接端子号"};
	String[][] 列值;
	String sColValue; 
	String sColName;
	String insetName;
	
	public ResultInfo(String colname, String colvalue){
		this.sColName = colname;
		this.sColValue = colvalue;
		this.setTitle("查询结果");
		
		//设置运行位置，居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - 400)/2, 
						(int)(screenSize.height - 300)/2 + 45);
		
		InfoBean ib = new InfoBean();
		try{
			列值 = ib.InfoAllSearch(sColName, sColValue);
			if(列值 == null){
				JOptionPane.showMessageDialog(null, "not found!");
			}else{
				jt = new JTable(列值 , 列名);     //创建表格对象
				js = new JScrollPane(jt);
				this.getContentPane().add(js, BorderLayout.CENTER);
				this.pack();
				this.setVisible(true);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
}
