package serverController;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ConnectToClient {

	private int port = 9210;
	private String ip = "10.88.61.165";
	//private ServerSocket serverSocket;
	public String datas;

	// 启动服务器，等待客户端连接
	public ConnectToClient() throws Exception {

		//serverSocket = new ServerSocket(port);
		System.out.println("正在连接服务器，请等待……\n");
	}

	public void service() {

		//while (true) {
			Socket socket = null;

			try {
				// 接收客户连接
				//socket = serverSocket.accept();
				socket = new Socket(ip,port);
				// 创建一个工作进程
				Thread workThread = new Thread(new Handler(socket));
				System.out.println("***创建新线程！***");
				// 启动工作进程
				workThread.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}

	class Handler implements Runnable {

		private Socket socket;

		public Handler(Socket socket) {

			this.socket = socket;
		}

		public void run() {

			AnalyzeSocketPackage asp = new AnalyzeSocketPackage();
			SelectSqlname ssn = new SelectSqlname();
			Iterator it;
			Map.Entry entry;
			int ch; // 端子号
			String date; // 时间
			double xdata; // 数据
			double temp; // 温度
			ArrayList value;

			try {

				System.out.println("服务器已连接！信息如下：\n" + "服务器IP地址："
						+ socket.getInetAddress() + "\n服务器连接端口： "
						+ socket.getPort());

				// 打开输出流
				OutputStream os = socket.getOutputStream();
				// 封装输出流

				DataOutputStream dos = new DataOutputStream(os);
				// 打开输入流
				InputStream is = socket.getInputStream();
				// 封装输入流

				DataInputStream dis = new DataInputStream(is);
				// 读取键盘输入流
				InputStreamReader isr = new InputStreamReader(System.in);
				// 封装键盘输入流

				BufferedReader br = new BufferedReader(isr);

				int j = 0;

				while (true) {

					// 向串口服务器发送指令
					System.out.println("Sending Command ...");
					os.write("MCU32 00023 Begin\r\n".getBytes());
					System.out.println("***********************************");

					int count0 = 0;
					while (count0 == 0) {
						count0 = is.available();
					}

					byte[] data0 = new byte[count0];

					for (int i = is.read(data0);; i = is.read(data0)) {

						if (i > 0) {

							System.out.println(new String(data0, 0, i));
							System.out.println("MCU32 已就绪！\n");
							break;

						}
					}

					/**
					 * 开始读取回传数据 因采集需要时间，故等待50s开始读取
					 */

					System.out.println("等待读取 MCU32 回传数据……");
					Thread.sleep(50 * 1000); // 休眠一段时间后读取数据

					j++; // 记录第几次采集数据

					os.write("MCU32 00023 Read\r\n".getBytes());

					int count = 0;
					while (count == 0) {
						count = is.available();
					}

					byte[] data = new byte[count];

					for (int i = is.read(data);; i = is.read(data)) {

						if (i > 0) {
							
							datas = new String(data,0,i);
							System.out.println("返回信息：\n"+datas);
							System.out.println("接收字节长度为：" + data.length);
							System.out.println("第" + j + "次" + "成功收到指令返回值。");
							break;
						}
					}
					// 数据包解析
					System.out.println("数据为：\n"+datas);
					asp.run1(datas);
					it = asp.rf.entrySet().iterator();
					while (it.hasNext()) {
						entry = (Map.Entry) it.next();
						ch = (int) entry.getKey();
						date = asp.sdate; // 获得时间数据
						value = (ArrayList) entry.getValue();
						xdata = (double) value.get(0);
						temp = (double) value.get(1);
						// 存入数据库
						ssn.SelectSqlname(ch, date, xdata, temp);
						System.out.println(ch + "\t" + date + "\t" + value);
					}

					try {
						Thread.sleep(60 * 1000);// 每隔一段时间采集一次
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// dis.close();//关闭输入流
				// dos.close();//关闭输出流
				// socket.close();//关闭socket对象
				// serverSocket.close();

			} catch (SocketException e) {
				System.out.println("网络连接异常，程序退出!");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * new ConnectToClient().service(); }
	 */
}