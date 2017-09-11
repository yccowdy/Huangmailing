package clientInf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 连接管理窗口模板类
 * @author Administrator
 *
 */
public class Info extends JFrame implements ActionListener{
	Container contentPane;
	JPanel centerPanel = new JPanel();
	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	//用于存放category的字符串变量
	String s_category = null;
	
	//定义标签
	JLabel jport = new JLabel();
	JLabel jcategory = new JLabel();
	JLabel jsqlname = new JLabel();
	JLabel jch = new JLabel();
	
	//定义文本框
	JTextField port = new JTextField(12);
	JComboBox<String> category = new JComboBox<String>();
	JTextField sqlname = new JTextField(12);
	JTextField ch = new JTextField(12);
	
	//定义按钮
	JButton searchInfo = new JButton();
	JButton addInfo = new JButton();
	JButton modifyInfo = new JButton();
	JButton deleteInfo = new JButton();
	JButton showInfo = new JButton();
	JButton clearInfo = new JButton();
	JButton exitInfo = new JButton();
	
	//定义网格包布局管理器与网格包面板
	GridBagLayout girdBag = new GridBagLayout();
	GridBagConstraints girdBagCon;
	
	public Info(){
		this.setSize(800, 600);
		this.setTitle("连接管理");
		this.setResizable(false);
		try{
			Init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 中部面板布局
	 * @throws Exception
	 */
	public void Init() throws Exception {
		
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//port
		centerPanel.setLayout(girdBag);
		jport.setText("连接端口号：");
		jport.setFont(new Font("Dialog",0,14));
		jport.setVisible(true);
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 0;
		girdBagCon.insets = new Insets(10,10,10,1);
		girdBag.setConstraints(jport, girdBagCon);
		centerPanel.add(jport);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx =1;
		girdBagCon.gridy =0;
		girdBagCon.insets = new Insets(10,1,10,15);
		girdBag.setConstraints(port, girdBagCon);
		centerPanel.add(port);
		
		//category
		category.addItem("振弦模块");
		category.addItem("电流电压");
		category.addItem("电阻模块");
		category.addItem("水文模块");
		category.addItem("智能模块");
		category.addActionListener(this);
		contentPane.add(category);
		category.setVisible(true);
		centerPanel.setLayout(girdBag);
		
		jcategory.setText("客户端类别：");
		jcategory.setFont(new Font("Dialog",0,14));
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 1;
		girdBagCon.insets = new Insets(10,10,10,1);
		girdBag.setConstraints(jcategory, girdBagCon);
		centerPanel.add(jcategory);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx =1;
		girdBagCon.gridy =1;
		girdBagCon.insets = new Insets(10,1,10,15);
		girdBag.setConstraints(category, girdBagCon);
		centerPanel.add(category);
		
		//sqlname
		centerPanel.setLayout(girdBag);
		jsqlname.setText("数据库表名：");
		jsqlname.setFont(new Font("Dialog",0,14));
		jsqlname.setVisible(true);
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 2;
		girdBagCon.insets = new Insets(10,10,10,1);
		girdBag.setConstraints(jsqlname, girdBagCon);
		centerPanel.add(jsqlname);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx =1;
		girdBagCon.gridy =2;
		girdBagCon.insets = new Insets(10,1,10,15);
		girdBag.setConstraints(sqlname, girdBagCon);
		centerPanel.add(sqlname);
		
		//ch
		centerPanel.setLayout(girdBag);
		jch.setText("端子号(CH)：");
		jch.setFont(new Font("Dialog",0,14));
		jch.setVisible(true);
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 3;
		girdBagCon.insets = new Insets(10,10,10,1);
		girdBag.setConstraints(jch, girdBagCon);
		centerPanel.add(jch);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx =1;
		girdBagCon.gridy =3;
		girdBagCon.insets = new Insets(10,1,10,15);
		girdBag.setConstraints(ch, girdBagCon);
		centerPanel.add(ch);
		
		contentPane.add(centerPanel,BorderLayout.CENTER);
		port.setEditable(false);
		category.setEditable(false);
		sqlname.setEditable(false);
		ch.setEditable(false);
	}
	
	/**
	 * 下部面板布局
	 */
	public void downInit(){
		searchInfo.setText("查询");
		searchInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(searchInfo);
		addInfo.setText("添加");
		addInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(addInfo);
		modifyInfo.setText("修改");
		modifyInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(modifyInfo);
		deleteInfo.setText("添加");
		deleteInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(deleteInfo);
		showInfo.setText("显示");
		showInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(showInfo);
		clearInfo.setText("清空");
		clearInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(clearInfo);
		exitInfo.setText("退出");
		exitInfo.setFont(new Font("Dialog",0,14));
		downPanel.add(exitInfo);
		contentPane.add(downPanel,BorderLayout.SOUTH);
		
		//添加事件监听器
		searchInfo.addActionListener(this);
		addInfo.addActionListener(this);
		modifyInfo.addActionListener(this);
		deleteInfo.addActionListener(this);
		showInfo.addActionListener(this);
		clearInfo.addActionListener(this);
		exitInfo.addActionListener(this);
		
		modifyInfo.setEnabled(false);
		deleteInfo.setEnabled(false);
		showInfo.setEnabled(false);
		clearInfo.setEnabled(false);
	}
	
	/**
	 * 事件处理方法
	 */
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == searchInfo){
			new SearchInfo().setVisible(true);
		}else if(obj == addInfo){
			new AddClientInfo().setVisible(true);
		}else if(obj == modifyInfo){
			new ModifyClientInfo(0,0,null,0).setVisible(true);
		}else if(obj == deleteInfo){
			new DeleteClientInfo(0,0,null,0).setVisible(true);
		}else if(obj == showInfo){
			//new AllClientInfo().setVisible(true);
		}else if(obj == clearInfo){
			setNull();
		}else if(obj == exitInfo){
			this.dispose();
		}
	}
	
	public void itemStateChanged(ItemEvent e)
	 {
	       if(e.getStateChange() == ItemEvent.SELECTED)
	             {
	                 s_category=category.getSelectedItem().toString();
	                     
	                 System.out.println("选择了："+s_category);//测试
	             }
	}  
	
	void setNull(){
		port.setText(null);
		//category.setText(null);
		sqlname.setText(null);
		ch.setText(null);
	}
	
}
