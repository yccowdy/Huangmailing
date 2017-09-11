package tcp_GL1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 此类为水位计数据采集部分
 * 
 * @author He Li
 *
 */
public class TCPClient {

	private int port = 9211;
	private String ip = "10.88.61.167";

	public int Data() {
		int b4 = 0;
		try {
			Socket socket = new Socket(ip, port);
			InputStream iStream = socket.getInputStream();
			int count = 0;
			while (count == 0) {
				count = iStream.available();
			}
			System.out.println(count);
			System.out.println("=====================");

			int[] b1 = new int[count];
			int[] b2 = new int[count];
			String b3 = null;
			int a = 171;// 171代表0xab

			// do{

			for (int i = 0; i < b1.length; i++) {
				b1[i] = iStream.read();
				b2[i] = b1[i];

			}

			while (iStream.available() == 0
					|| Integer.toHexString(b1[0]) == "ffffffff") {
				for (int j = 0; j < b2.length; j++) {

					if (b2[j] == a && (j + 3) <= b2.length) {
						b3 = Integer.toHexString(b2[j + 2])
								+ Integer.toHexString(b2[j + 3]);
					}
				}

				System.out.print(Integer.parseInt(b3, 16) + " ");
				b4 = Integer.parseInt(b3, 16);
				break;

			}

			// }while(b3=="0");

			iStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return b4;

	}

}
