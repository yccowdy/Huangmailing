package serverController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

/**
 * @name TestAnalyze.java
 *
 * @author hzy
 *
 * @version 创建时间：2016年9月20日上午9:56:28
 *
 *          类说明：Junit测试AnalyzeSocketPackage()是否正确
 */
public class TestAnalyze {

	@Test
	public void testAnalyzeSocketPackage() {
		AnalyzeSocketPackage asp = new AnalyzeSocketPackage();
		SelectSqlname ssn = new SelectSqlname();
		try {
			String s = "08/08/20 08:09:25 M1ZXN M1=2789.6 T1=-10.0 M2=4456.0 "
					+ "T2=20.5 M3=6635.7 T3=18.9 M4=4311.0 T4=25.0 M5=4564.8 "
					+ "T5=20.0 M6=3210.5 T6=40.0 M7=3333.6 T7=-15.0 M8=2.0 T8=17.6 ----\r\n"
					+ "08/08/29 08:23:25 M2ZXN M1=2789.6 T1=-10.0 M2=4456.0 "
					+ "T2=20.5 M3=6635.7 T3=18.9 M4=4311.0 T4=25.0 M5=4564.8 "
					+ "T5=20.0 M6=3210.5 T6=40.0 M7=3333.6 T7=-15.0 M8=0.0 T8=----\r\n"
					+ "12/09/28 14:22:05 M4SWNGA Water1=10.000 Water2=20.000 Water3=30.000 "
					+ "Water4=40.000 M5=0.000 M6=0.000 M7=0.000 M8=0.000 "
					+ "WinSpeed1=0.0 RealDir1=0.0 WinSpeed2=0.0 RealDir2=0.0 'Q";
			asp.run1(s);
			Iterator it = asp.rf.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				int ch = (int)entry.getKey();
				String date = asp.sdate; // 获得时间数据
				ArrayList value = (ArrayList)entry.getValue();
				double xdata = (double) value.get(0);
				double temp = (double) value.get(1);
				// 存入数据库
				ssn.SelectSqlname(ch, date, xdata, temp);
				System.out.println(ch + "\t" + date + "\t" + value);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
