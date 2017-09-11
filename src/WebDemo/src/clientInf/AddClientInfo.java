package clientInf;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class AddClientInfo extends Info{
	InfoBean infobean = new InfoBean();
	
	public AddClientInfo(){

		this.setTitle("添加客户端信息");
		this.setSize(500, 400);
		this.setResizable(false);
		port.setEditable(true);
		category.setEditable(true);
		sqlname.setEditable(true);
		ch.setEditable(true);
		//设置运行时的窗口位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-500)/2, (screenSize.height-400)/2+45);
	}
	
	public void downInit(){
		
		addInfo.setText("增加");
		addInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(addInfo);
		
		clearInfo.setText("清空");
		addInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(clearInfo);
		
		exitInfo.setText("退出");
		exitInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(exitInfo);
		
		//添加事件监听器
		addInfo.addActionListener(this);  
		clearInfo.addActionListener(this);
		exitInfo.addActionListener(this);
		
		this.contentPane.add(downPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * 事件处理方法
	 */
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == exitInfo){
			this.dispose();
		}else if(obj == addInfo){
			port.setEnabled(false);
			category.setEnabled(false);
			sqlname.setEnabled(false);
			ch.setEnabled(false);
			addInfo.setEnabled(false);
			clearInfo.setEnabled(false);
			exitInfo.setEnabled(false);
			
			InfoBean addInfo = new InfoBean();//创建执行SQL操作的对象addInfo
			//通过对象addInfo的clientAdd方法将数据添加到数据库（所有传感器的列表）
			System.out.println(port.getText()+"\t"+category.getSelectedItem().toString()+"\t"+sqlname.getText()+"\t"+ch.getText());
			addInfo.clientAdd(port.getText(), category.getSelectedItem().toString(), sqlname.getText(), ch.getText());
			this.dispose();
		}else if(obj == clearInfo){
			setNull();
		}
	}

}
