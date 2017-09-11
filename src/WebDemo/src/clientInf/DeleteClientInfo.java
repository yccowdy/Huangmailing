package clientInf;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DeleteClientInfo extends Info{
	InfoBean infobean = new InfoBean();
	
	public DeleteClientInfo(int xport,int xcategory,String xsqlname,int xch){

		this.setTitle("删除客户端信息");
		this.setSize(500, 400);
		this.setResizable(false);
		port.setEditable(true);
		category.setEditable(true);
		sqlname.setEditable(true);
		port.setText(String.valueOf(xport));
		if(xcategory == 0){
			category.setSelectedIndex(0);
		}else if(xcategory == 1){
			category.setSelectedIndex(1);
		}else if(xcategory == 2){
			category.setSelectedIndex(2);
		}else if(xcategory == 3){
			category.setSelectedIndex(3);
		}else if(xcategory == 4){
			category.setSelectedIndex(4);
		}
		sqlname.setText(xsqlname);
		ch.setText(String.valueOf(xch));
		//设置运行时的窗口位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-500)/2, (screenSize.height-400)/2+45);
	}
	
	public void downInit(){
		
		deleteInfo.setText("删除");
		deleteInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(deleteInfo);
		
		clearInfo.setText("清空");
		deleteInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(clearInfo);
		
		exitInfo.setText("退出");
		exitInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(exitInfo);
		//添加事件监听器
		deleteInfo.addActionListener(this);  
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
		}else if(obj == deleteInfo){
			port.setEnabled(false);
			category.setEnabled(false);
			sqlname.setEnabled(false);
			deleteInfo.setEnabled(false);
			clearInfo.setEnabled(false);
			exitInfo.setEnabled(false);
			
			InfoBean deleteInfo = new InfoBean();
			deleteInfo.clientDelete(sqlname.getText());
			//JOptionPane.showMessageDialog(this,"删除成功！\n\n","删除信息",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		}else if(obj == clearInfo){
			setNull();
		}
	}

}
