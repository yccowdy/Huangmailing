package clientInf;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author Administrator 类说明：显示表格
 */
public class AllClientInfo extends JFrame

{
	boolean state;
	public Object[][] rowData = new Object[32][6]; // 表格数据
	public JTable table;
	DefaultTableModel dModel; // 表格模型对象
	private String url = "jdbc:mysql://localhost:3306/HMLproj?"
			+ "user=cug&password=cug&useUnicode=true&characterEncoding=UTF8&useSSL=false";

	public AllClientInfo() {
		super("客户端信息"); // 调用父类构造函数
		//String[] columnNames = { "创建日期", "端口号", "类别", "数据库名", "连接端子", "运行状态" }; // 列名
		String[] columnNames = { "创建日期", "端口号", "类别", "数据库名", "连接端子"};
		int num = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);

			String sqlStr = "select * from allClient"; // 查询语句
			PreparedStatement ps = con.prepareStatement(sqlStr); // 获取PreparedStatement对象
			ResultSet rs = ps.executeQuery(); // 执行查询

			int count = 0;
			while (rs.next()) { // 遍历查询结果
				rowData[count][0] = rs.getString("date"); // 初始化数组内容
				rowData[count][1] = Integer.toString(rs.getInt("port"));
				rowData[count][2] = rs.getString("category");
				rowData[count][3] = rs.getString("sqlname");
				rowData[count][4] = Integer.toString(rs.getInt("ch"));
				//rowData[count][5] = false;
				count++;
			}
			num = count;

			con.close(); // 关闭连接
		} catch (Exception ex) {
			ex.printStackTrace(); // 输出出错信息
		}

		Container container = getContentPane(); // 获取窗口容器
		JButton addButton = new JButton("增加");
		JButton modifyButton = new JButton("修改");
		JButton deleteButton = new JButton("删除");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(deleteButton);
		add(buttonPanel, BorderLayout.SOUTH);
		addButton.setBounds(10, 120, 70, 20);
		modifyButton.setBounds(100, 120, 70, 20);
		deleteButton.setBounds(190, 120, 70, 20);

		dModel = new DefaultTableModel(rowData, columnNames);
		dModel.setRowCount(num);
		table = new JTable(dModel); // 实例化表格
		table.setShowGrid(true);
		table.setGridColor(Color.BLUE);
		table.setRowHeight(20);
		table.setFont(new Font("", Font.PLAIN, 12));
		container.add(new JScrollPane(table), BorderLayout.CENTER); // 增加组件

		// StyleTable st = new StyleTable();
		// st.fitTableColumns(table);

		// 捕捉鼠标选中事件
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
		table.addMouseListener(new MouseAdapter() { // 鼠标事件
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow(); // 获得选中行索引

			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					AddClientInfo aci = new AddClientInfo(); // 创建添加信息对话框对象
					aci.downInit(); // 实例化对话框
					aci.pack();
					aci.setVisible(true); // 打开对话框
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int selectedRow = table.getSelectedRow();// 获得选中行的索引
				if (selectedRow != -1) // 是否存在选中行
				{
					int sPort = Integer
							.valueOf((String) rowData[selectedRow][1]);
					int sCategory = 0;
					String iCategory = (String) rowData[selectedRow][2];
					if (iCategory.equals("振弦模块")) {
						sCategory = 0;
					} else if (iCategory.equals("电流电压")) {
						sCategory = 1;
					} else if (iCategory.equals("电阻模块")) {
						sCategory = 2;
					} else if (iCategory.equals("水文模块")) {
						sCategory = 3;
					} else if (iCategory.equals("智能模块")) {
						sCategory = 4;
					}
					String sName = (String) rowData[selectedRow][3];
					int sch = Integer.valueOf((String) rowData[selectedRow][4]);
					// System.out.println(sPort+"\t"+sCategory+"\t"+sName+"\t"+sch);
					try {
						ModifyClientInfo mci = new ModifyClientInfo(sPort,
								sCategory, sName, sch);
						mci.downInit();
						mci.pack();
						mci.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int selecteRow = table.getSelectedRow();// 获得选中行的索引
				if (selecteRow != -1) // 是否存在选中行
				{
					int dPort = Integer
							.valueOf((String) rowData[selecteRow][1]);
					int dCategory = 0;
					String iCategory = (String) rowData[selecteRow][2];
					if (iCategory.equals("振弦模块")) {
						dCategory = 0;
					} else if (iCategory.equals("电流电压")) {
						dCategory = 1;
					} else if (iCategory.equals("电阻模块")) {
						dCategory = 2;
					} else if (iCategory.equals("水文模块")) {
						dCategory = 3;
					} else if (iCategory.equals("智能模块")) {
						dCategory = 4;
					}
					String dName = (String) rowData[selecteRow][3];
					int dch = Integer.valueOf((String) rowData[selecteRow][4]);
					// System.out.println(dPort+"\t"+dCategory+"\t"+dName+"\t"+dch);
					try {
						DeleteClientInfo dci = new DeleteClientInfo(dPort,
								dCategory, dName, dch);
						dci.downInit();
						dci.pack();
						dci.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		// setSize(300,200); //设置窗口尺寸
		// setVisible(true); //设置窗口可视
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void run() {
		/*
		 * table.updateUI(); table.revalidate();
		 */
		// new AllClientInfo();
		table.invalidate();
		dModel.fireTableDataChanged();
	}

}