package clientInf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;

import javax.swing.*;

/**
 *@name SearchInfo.java
 *
 *@author hzy
 *
 *@version 创建时间：2016年9月12日下午3:26:27
 *
 *类说明：查询数据表信息
 */
public class SearchInfo extends JFrame implements ActionListener{

	Container c;
	JLabel jLabel = new JLabel();
	JTextField sqlName = new JTextField(6);
	JButton ok = new JButton();
	
	public SearchInfo(){
		
		this.setTitle("查询");
		this.setResizable(false);
		
		try{
			Init();
		}catch(Exception e){
			e.printStackTrace();
		}
		//设置运行位置，是对话框居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - 400)/2, 
				(int)(screenSize.height - 300)/2 + 45);
	}
	
	public void Init() throws Exception{
		this.setSize(300 , 150);
		c = this.getContentPane();
		c.setLayout(new FlowLayout());
		
		jLabel.setText("请输入需查询的数据表：");
		jLabel.setFont(new Font("Dialog", 0 , 12));
		c.add(jLabel);
		
		sqlName.setText(null);
		sqlName.setFont(new Font("Dialog", 0 , 12));
		c.add(sqlName);
		
		ok.setText("确定");
		ok.setFont(new Font("Dialog", 0 , 12));
		c.add(ok);
		ok.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if(obj == ok){//执行查询
			ResultInfo ri = new ResultInfo("sqlname", sqlName.getText());
			this.dispose();
		}
		
	}

	
}
