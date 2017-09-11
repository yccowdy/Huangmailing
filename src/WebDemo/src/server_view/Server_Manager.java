package server_view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import serverController.ConnectToClient;
import tcp_GL1.TestGL1;
import ylj.Test_YuLiangJi;
import clientInf.*;

import java.net.*;

/**
 * 主界面窗口类
 * @author Huzhiyuan
 *
 */
public class Server_Manager extends JFrame implements ActionListener 
{     
	JMenuBar mainMenu = new JMenuBar();//建立菜单栏
	
	JMenu itemStart = new JMenu("启动/关闭");//建立启动菜单
	JMenuItem itemConnect = new JMenuItem("启动连接");//
	JMenuItem itemStop = new JMenuItem("关闭连接");//
	
	JMenu linkManager = new JMenu("连接管理");//建立连接管理菜单及选项
	JMenuItem itemAdd = new JMenuItem("添加连接");
	JMenuItem itemModify = new JMenuItem("修改连接");
	JMenuItem itemDelete = new JMenuItem("删除连接");
	
	JMenu linkStat = new JMenu("连接状态");
	JMenuItem allStat = new JMenuItem("所有状态");//
	
	JMenu itemExit = new JMenu("退出");
	JMenuItem itemOver = new JMenuItem("退出");//
	
	JMenu itemHelp = new JMenu("帮助");
	JMenuItem itemAbout = new JMenuItem("关于软件");//
	JMenuItem itemIntroduce = new JMenuItem("使用说明");//
	
	public Container contentPane = this.getContentPane();
	
	//创建窗体模板对象
	public static Info info = new Info();

	//内容面板对象
	Container c;
	/*JTextArea editor = new JTextArea();//创建默认大小的文本区对象
	Font t = new Font("sanserif",Font.PLAIN,12);*/
	
	//Image icon;
	
	JButton refreshButton;
	JPanel buttonPanel;
	AllClientInfo allInfo = new AllClientInfo();
	
	public Server_Manager()//构造方法
	{
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//添加窗口的关闭事件处理
		this.pack();
		setSize(800,500);
		setTitle("湖北省黄麦岭尾矿库监测系统");
		//设置运行时的窗口位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-800)/2, (screenSize.height-600)/2+45);
	
		//icon = getImage("icon.gif");//程序图标
		//this.setIconImage(icon);
		try{
			Init();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void Init() throws Exception 
	{
		/**
		 * 初始化方法，显示表格
		 */
		
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(allInfo.table));
		refreshButton = new JButton("刷新数据");
		buttonPanel = new JPanel();
		buttonPanel.add(refreshButton);
		add(buttonPanel, BorderLayout.SOUTH);
		refreshButton.setBounds(10,120,70,20);
		refreshButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) 
		      {
		          try {
		        	  
		        	  allInfo = new  AllClientInfo();
		        	  contentPane.removeAll();
		        	  contentPane.setLayout(new BorderLayout());
		        	  contentPane.add(new JScrollPane(allInfo.table));
		        	  add(buttonPanel, BorderLayout.SOUTH);
		      		  setContentPane (contentPane);
		        	  //allInfo.table.invalidate();
		        	  System.out.println("******刷新******");
		          } catch (Exception e) {
		              e.printStackTrace();
		          }
		      }
		  });
		
		/**
		 * 启动/关闭连接
		 */
		itemStart.setText("启动/关闭");
		itemStart.setFont(new Font("Dialog",0,20));
		itemStart.setFont(new Font("楷体",Font.BOLD,20));
		itemStart.setForeground(Color.BLUE);
		
		itemConnect.setText("启动连接");
		itemConnect.setFont(new Font("Dialog",0,20));
		itemConnect.setFont(new Font("楷体",Font.BOLD,20));
		itemConnect.setForeground(Color.BLUE);
		
		itemStop.setText("关闭连接");
		itemStop.setFont(new Font("Dialog",0,20));
		itemStop.setFont(new Font("楷体",Font.BOLD,20));
		itemStop.setForeground(Color.BLUE);
		/**
		 * 退出
		 */
		itemExit.setText("退出");
		itemExit.setFont(new Font("Dialog",0,20));
		itemExit.setFont(new Font("楷体",Font.BOLD,20));
		itemExit.setForeground(Color.BLUE);
		
		itemOver.setText("退出");
		itemOver.setFont(new Font("Dialog",0,20));
		itemOver.setFont(new Font("楷体",Font.BOLD,20));
		itemOver.setForeground(Color.BLUE);
		/**
		 * 帮助
		 */
		itemHelp.setText("帮助");
		itemHelp.setFont(new Font("Dialog",0,20));
		itemHelp.setFont(new Font("楷体",Font.BOLD,20));
		itemHelp.setForeground(Color.BLUE);
		
		itemAbout.setText("关于软件");
		itemAbout.setFont(new Font("Dialog",0,20));
		itemAbout.setFont(new Font("楷体",Font.BOLD,20));
		itemAbout.setForeground(Color.BLUE);
		
		itemIntroduce.setText("使用说明");
		itemIntroduce.setFont(new Font("Dialog",0,20));
		itemIntroduce.setFont(new Font("楷体",Font.BOLD,20));
		itemIntroduce.setForeground(Color.BLUE);
		/**
		 * 连接状态
		 */
		linkStat.setText("连接状态");
		linkStat.setFont(new Font("Dialog",0,20));
		linkStat.setFont(new Font("楷体",Font.BOLD,20));
		linkStat.setForeground(Color.BLUE);
		
		allStat.setText("所有状态");
		allStat.setFont(new Font("Dialog",0,20));
		allStat.setFont(new Font("楷体",Font.BOLD,20));
		allStat.setForeground(Color.BLUE);
		/**
		 * 连接管理
		 */
		linkManager.setText("连接管理");
		linkManager.setFont(new Font("Dialog",0,20));
		linkManager.setFont(new Font("楷体",Font.BOLD,20));
		linkManager.setForeground(Color.BLUE);
		
		itemAdd.setText("添加连接");
		itemAdd.setFont(new Font("Dialog",0,20));
		itemAdd.setFont(new Font("楷体",Font.BOLD,20));
		itemAdd.setForeground(Color.BLUE);
		
		itemModify.setText("修改连接");
		itemModify.setFont(new Font("Dialog",0,20));
		itemModify.setFont(new Font("楷体",Font.BOLD,20));
		itemModify.setForeground(Color.BLUE);
		
		itemDelete.setText("删除连接");
		itemDelete.setFont(new Font("Dialog",0,20));
		itemDelete.setFont(new Font("楷体",Font.BOLD,20));
		itemDelete.setForeground(Color.BLUE);
		
		/**
		 * 添加菜单组和菜单选项
		 */
		itemStart.add(itemConnect);
		itemStart.addSeparator();
		itemStart.add(itemStop);
		linkManager.add(itemAdd);
		linkManager.addSeparator();
		linkManager.add(itemModify);
		linkManager.addSeparator();
		linkManager.add(itemDelete);
		linkStat.add(allStat);
		itemExit.add(itemOver);
		itemHelp.add(itemAbout);
		itemHelp.add(itemIntroduce);
		
		//组合菜单栏
		mainMenu.add(itemStart);
		mainMenu.add(linkManager);
		mainMenu.add(linkStat);
		mainMenu.add(itemExit);
		mainMenu.add(itemHelp);
		this.setJMenuBar(mainMenu);
		
		//给菜单选项添加addActionListener事件侦听器
		itemConnect.addActionListener(this);
		itemStop.addActionListener(this);
		itemAdd.addActionListener(this);
		itemModify.addActionListener(this);
		itemDelete.addActionListener(this);
		allStat.addActionListener(this);
		itemOver.addActionListener(this);
		itemAbout.addActionListener(this);
		itemIntroduce.addActionListener(this);
		
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){//关闭程序操作
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		
		if(obj == itemOver){//退出
			
			System.exit(0);
			
		}else if(obj == itemConnect){//启动
			final Test_YuLiangJi tYLJ = new Test_YuLiangJi();
			final TestGL1 tGL = new TestGL1();
		/**
		 * 必须新开一个线程处理，否则程序会因数据采集未结束而卡死
		 */
				//MCU32数据
			    new Thread(new Runnable(){
					public void run(){
						System.out.println("MCU32数据\n");
					try {
						new ConnectToClient().service();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					}).start();
				//雨量采集
				new Thread(new Runnable(){
					public void run(){
						System.out.println("雨量数据\n");
					try {
						tYLJ.runTask();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					}).start();
				
				//水位采集
				new Thread(new Runnable(){
					public void run(){
						System.out.println("水位数据\n");
					try {
						tGL.runTask();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					}).start();
				
				JOptionPane.showMessageDialog(this,"信息提示\n\n" +
						"服务器已启动！\n\n正在采集数据……","启动服务器",JOptionPane.INFORMATION_MESSAGE);

		}else if(obj == itemStop){
			
			JOptionPane.showMessageDialog(this,"信息提示\n\n" +
					"采集通道已关闭！","关闭采集通道",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			
		}else if(obj == itemAdd){//添加连接
		
			AddClientInfo aci = new AddClientInfo(); //创建添加信息对话框对象
			aci.downInit();                          //实例化对话框
			aci.pack();
			aci.setVisible(true);                    //打开对话框
		
		}else if(obj == itemModify){//修改连接
			
			ModifyClientInfo mci = new ModifyClientInfo(0,0,null,0);
			mci.downInit();
			mci.pack();
			mci.setVisible(true);
			
		}else if(obj == itemDelete){//删除连接
			
			DeleteClientInfo dci = new DeleteClientInfo(0,0,null,0);
			dci.downInit();
			dci.pack();
			dci.setVisible(true);
			
		}else if(obj == allStat){//查看连接状态
			AllClientInfo all = new AllClientInfo();
			//all.run();
			all.pack();
			all.setVisible(true);
			
		}else if(obj == itemAbout){
			JOptionPane.showMessageDialog(this,"湖北黄麦岭尾矿库检测系统\n\n" +
					"CUG计算机学院\n\n2016年7月","软件信息",JOptionPane.INFORMATION_MESSAGE);
		}else if(obj == itemIntroduce){
			JOptionPane.showMessageDialog(this,"\n\n******************************" +
					"系统使用说明******************************\n\n\n" +
					"1、首先点击【连接管理】==>【添加连接】，按要求添加连接信息；\n\n" +
					"2、添加完成后，点击【启动/关闭】==>【启动连接】启动连接开始采集；\n\n" +
					"3、若需要查看连接状态，可点击【连接状态】==>【所有状态】查看；\n\n" +
					"4、系统工作在TCP Server模式，需将串口服务器工作模式设置成TCP Client。\n\n",
					"使用说明",JOptionPane.INFORMATION_MESSAGE);
		}
	}
/**	
	Image getImage(String filename){//获得图像的方法
		URLClassLoader urlLoader = (URLClassLoader)this.getClass().getClassLoader();
		URL url = null;
		Image image = null;
		url = urlLoader.findResource(filename);
		image = Toolkit.getDefaultToolkit().getImage(url);
		MediaTracker mediatracker = new MediaTracker(this);
		try{
			mediatracker.addImage(image, 0);
			mediatracker.waitForID(0);
		}catch(InterruptedException _ex){
			image = null;
		}
		if (mediatracker.isErrorID(0)){
			image = null;
		}
		return image;
	}
**/
	
	public static void main(String[] args)
	{/**
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace();
		}
		**/
		new Server_Manager();
	}
	
} 