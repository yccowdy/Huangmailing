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

public class ModifyClientInfo extends Info{
	InfoBean infobean = new InfoBean();
	
	public ModifyClientInfo(int iport,int icategory,String isqlname,int ich){

		this.setTitle("修改客户端信息");
		this.setSize(500, 400);
		this.setResizable(false);
		
		port.setEditable(true);
		category.setEditable(true);
		sqlname.setEditable(true);
		ch.setEditable(true);
		
		port.setText(String.valueOf(iport));
		if(icategory == 0){
			category.setSelectedIndex(0);
		}else if(icategory == 1){
			category.setSelectedIndex(1);
		}else if(icategory == 2){
			category.setSelectedIndex(2);
		}else if(icategory == 3){
			category.setSelectedIndex(3);
		}else if(icategory == 4){
			category.setSelectedIndex(4);
		}
		sqlname.setText(isqlname);
		ch.setText(String.valueOf(ich));
		
		//设置运行时的窗口位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-500)/2, (screenSize.height-400)/2+45);
	}
	
	public void downInit(){
		
		modifyInfo.setText("修改");
		modifyInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(modifyInfo);
		
		clearInfo.setText("清空");
		modifyInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(clearInfo);
		
		exitInfo.setText("退出");
		exitInfo.setFont(new Font("Dialog" , 0 ,12));
		downPanel.add(exitInfo);
		//添加事件监听器
		modifyInfo.addActionListener(this);  
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
		}else if(obj == modifyInfo){
			port.setEnabled(false);
			category.setEnabled(false);
			sqlname.setEnabled(false);
			ch.setEnabled(false);
			modifyInfo.setEnabled(false);
			clearInfo.setEnabled(false);
			exitInfo.setEnabled(false);
			
			InfoBean modifyInfo = new InfoBean();
			modifyInfo.clientModify(port.getText(), category.getSelectedItem().toString(), 
					sqlname.getText(), ch.getText());
			//JOptionPane.showMessageDialog(this,"修改成功！\n\n","修改信息",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		}else if(obj == clearInfo){
			setNull();
		}
	}

}
