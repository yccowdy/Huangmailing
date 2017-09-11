package ylj;

import java.io.InputStream;
import java.net.Socket;

/**
 * 此类为雨量计采集部分
 * 
 * @author He Li
 *
 */
public class Test_YLJ {

	static int port = 9217;
	static String ip = "10.88.61.166";

	public int Yuliang() throws Exception {

		int i = 0;
		long time1, time2 = 0;
		while (true) {
			time1 = time();
			i++;
			time2 += time1;
			if (time2 > 5 * 1000)
				break;
		}
		System.out.println(i * 5);
		return i * 5;

	}

	public static long time() throws Exception {
		Socket socket = new Socket(ip, port);
		InputStream is = socket.getInputStream();
		long starTime = System.currentTimeMillis();
		is.read();
		long endTime = System.currentTimeMillis();
		socket.close();
		is.close();
		return endTime - starTime;
	}

}
