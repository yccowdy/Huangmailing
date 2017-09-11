package serverController;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import connSQL.ConnectMysql;

public class AnalyzeSocketPackage {

	static String sdate = null; // 日期
	static String x, x1; // 年月、时间
	static int mod; // 模块
	static int mch; // 端子号CH
	static// 使用TreeMap存储数据<mch,list>，list中存储M和T
	TreeMap<Integer, ArrayList<Double>> rf = new TreeMap<Integer, ArrayList<Double>>();
	Iterator it;
	Map.Entry entry;

	/**
	 * 使用正则表达式解析报文 提取数据
	 */
	public static void getStrings(String t) {

		/**
		 * 获取日期
		 */
		Pattern p = Pattern.compile("^\\d{2}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}");
		Matcher m = p.matcher(t);
		ArrayList<String> strs = new ArrayList<String>();
		while (m.find()) {
			strs.add(m.group());
		}
		/*
		 * for(int i=0;i<strs.length;i++){ String str=strs[i]; }
		 */
		for (String s : strs) {// 增强for循环
			System.out.println("日期为：" + s);
			x = s;
		}

		/**
		 * 获取时间
		 */

		Pattern p1 = Pattern.compile("\\d{2}(\\:)\\d{2}\\1\\d{2}");
		Matcher m1 = p1.matcher(t);
		ArrayList<String> strs1 = new ArrayList<String>();
		while (m1.find()) {
			strs1.add(m1.group());
		}
		for (String s1 : strs1) {
			System.out.println("时间为：" + s1);
			x1 = s1;
		}

		sdate = x + " " + x1;
		System.out.println(sdate);

		/**
		 * 获取模块号及类型
		 */
		Pattern p2 = Pattern.compile("M(\\d{1})\\w+");
		Matcher m2 = p2.matcher(t);
		ArrayList<String> strs2 = new ArrayList<String>();
		while (m2.find()) {
			strs2.add(m2.group(1));
		}
		for (String s2 : strs2) {
			int mode = Integer.parseInt(s2);
			if (mode == 1) {
				//mch = 1;
				//System.out.println("模块号为：" + s2 + " 振弦测量模块");
				System.out.println("模块号为：" + s2 + " 水文模块");

				/*Pattern p3 = Pattern
						.compile("M1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m3 = p3.matcher(t);
				while (m3.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM1="
							+ m3.group(1) + "\nT1=" + m3.group(2));

					ArrayList<Double> list1 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list1 == null) {
						list1 = new ArrayList<Double>();
						list1.add(Double.valueOf(m3.group(1)));
						list1.add(Double.valueOf(m3.group(2)));
						rf.put(mch, list1);
						mch++;
					}

				}

				Pattern p4 = Pattern
						.compile("M2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m4 = p4.matcher(t);
				while (m4.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM2="
							+ m4.group(1) + "\nT2=" + m4.group(2));

					ArrayList<Double> list2 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list2 == null) {
						list2 = new ArrayList<Double>();
						list2.add(Double.valueOf(m4.group(1)));
						list2.add(Double.valueOf(m4.group(2)));
						rf.put(mch, list2);
						mch++;

					}
				}

				Pattern p5 = Pattern
						.compile("M3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m5 = p5.matcher(t);
				while (m5.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM3="
							+ m5.group(1) + "\nT3=" + m5.group(2));

					ArrayList<Double> list3 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list3 == null) {
						list3 = new ArrayList<Double>();
						list3.add(Double.valueOf(m5.group(1)));
						list3.add(Double.valueOf(m5.group(2)));
						rf.put(mch, list3);
						mch++;

					}
				}

				Pattern p6 = Pattern
						.compile("M4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m6 = p6.matcher(t);
				while (m6.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM4="
							+ m6.group(1) + "\nT4=" + m6.group(2));

					ArrayList<Double> list4 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list4 == null) {
						list4 = new ArrayList<Double>();
						list4.add(Double.valueOf(m6.group(1)));
						list4.add(Double.valueOf(m6.group(2)));
						rf.put(mch, list4);
						mch++;

					}
				}

				Pattern p7 = Pattern
						.compile("M5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m7 = p7.matcher(t);
				while (m7.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM5="
							+ m7.group(1) + "\nT5=" + m7.group(2));

					ArrayList<Double> list5 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list5 == null) {
						list5 = new ArrayList<Double>();
						list5.add(Double.valueOf(m7.group(1)));
						list5.add(Double.valueOf(m7.group(2)));
						rf.put(mch, list5);
						mch++;

					}
				}

				Pattern p8 = Pattern
						.compile("M6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m8 = p8.matcher(t);
				while (m8.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM6="
							+ m8.group(1) + "\nT6=" + m8.group(2));

					ArrayList<Double> list6 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list6 == null) {
						list6 = new ArrayList<Double>();
						list6.add(Double.valueOf(m8.group(1)));
						list6.add(Double.valueOf(m8.group(2)));
						rf.put(mch, list6);
						mch++;

					}
				}

				Pattern p9 = Pattern
						.compile("M7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m9 = p9.matcher(t);
				while (m9.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM7="
							+ m9.group(1) + "\nT7=" + m9.group(2));

					ArrayList<Double> list7 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list7 == null) {
						list7 = new ArrayList<Double>();
						list7.add(Double.valueOf(m9.group(1)));
						list7.add(Double.valueOf(m9.group(2)));
						rf.put(mch, list7);
						mch++;

					}
				}

				Pattern p0 = Pattern
						.compile("M8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m0 = p0.matcher(t);
				while (m0.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM8="
							+ m0.group(1) + "\nT8=" + m0.group(2));
					// 根据key得到存放value的list
					ArrayList<Double> list8 = (ArrayList<Double>) rf.get(mch);
					if (list8 == null) {
						list8 = new ArrayList<Double>();
						list8.add(Double.valueOf(m0.group(1)));
						list8.add(Double.valueOf(m0.group(2)));
						rf.put(mch, list8);
						mch++;

					}
				}*/

				Pattern p11 = Pattern
						.compile("Water1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m11 = p11.matcher(t);
				while (m11.find()) {
					System.out.println("模块数据：\nWater1=" + m11.group(1));
					if(Double.valueOf(m11.group(1))!=0){
					mch = 1;
					// 根据key得到存放value的list
					ArrayList<Double> list33 = (ArrayList<Double>) rf.get(mch);
					if (list33 == null) {
						list33 = new ArrayList<Double>();
						list33.add((double) 1);
						list33.add(Double.valueOf(m11.group(1)));
						rf.put(mch, list33);
						}
					}
				}
				

				Pattern p12 = Pattern
						.compile("Water2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m12 = p12.matcher(t);
				while (m12.find()) {
					System.out.println("模块数据：\nWater2=" + m12.group(1));
					if(Double.valueOf(m12.group(1))!=0){
					mch = 2;
					// 根据key得到存放value的list
					ArrayList<Double> list34 = (ArrayList<Double>) rf.get(mch);
					if (list34 == null) {
						list34 = new ArrayList<Double>();
						list34.add((double) 2);
						list34.add(Double.valueOf(m12.group(1)));
						rf.put(mch, list34);
						}
					}
				}

				Pattern p13 = Pattern
						.compile("Water3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m13 = p13.matcher(t);
				while (m13.find()) {
					System.out.println("模块数据：\nWater3=" + m13.group(1));
					if(Double.valueOf(m13.group(1))!=0){
					mch = 3;
					// 根据key得到存放value的list
					ArrayList<Double> list35 = (ArrayList<Double>) rf.get(mch);
					if (list35 == null) {
						list35 = new ArrayList<Double>();
						list35.add((double) 3);
						list35.add(Double.valueOf(m13.group(1)));
						rf.put(mch, list35);
						}
					}
				}

				Pattern p14 = Pattern
						.compile("Water4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m14 = p14.matcher(t);
				while (m14.find()) {
					System.out.println("模块数据：\nWater4=" + m14.group(1));
					if(Double.valueOf(m14.group(1))!=0){
					mch = 4;
					// 根据key得到存放value的list
					ArrayList<Double> list36 = (ArrayList<Double>) rf.get(mch);
					if (list36 == null) {
						list36 = new ArrayList<Double>();
						list36.add((double) 4);
						list36.add(Double.valueOf(m14.group(1)));
						rf.put(mch, list36);
						}
					}
				}

				Pattern p15 = Pattern
						.compile("WinSpeed1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m15 = p15.matcher(t);
				while (m15.find()) {
					System.out.println("模块数据：\nWinSpeed1=" + m15.group(1)
							+ "\nRealDir1=" + m15.group(2));
				}

				Pattern p16 = Pattern
						.compile("WinSpeed2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m16 = p16.matcher(t);
				while (m16.find()) {
					System.out.println("模块数据：\nWinSpeed2=" + m16.group(1)
							+ "\nRealDir2=" + m16.group(2));
				}
				
				
			} else if (mode == 2) {
				mch = 9;
				//System.out.println("模块号为：" + s2 + " 电压电流测量模块");
				System.out.println("模块号为：" + s2 + " 振弦测量模块");

				Pattern p3 = Pattern
						.compile("M1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m3 = p3.matcher(t);
				while (m3.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM1="
							+ m3.group(1) + "\nT1=" + m3.group(2));

					ArrayList<Double> list9 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list9 == null) {
						list9 = new ArrayList<Double>();
						list9.add(Double.valueOf(m3.group(1)));
						list9.add(Double.valueOf(m3.group(2)));
						rf.put(mch, list9);
						
					}
				}

				mch = 10;
				Pattern p4 = Pattern
						.compile("M2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m4 = p4.matcher(t);
				while (m4.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM2="
							+ m4.group(1) + "\nT2=" + m4.group(2));

					ArrayList<Double> list10 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list10 == null) {
						list10 = new ArrayList<Double>();
						list10.add(Double.valueOf(m4.group(1)));
						list10.add(Double.valueOf(m4.group(2)));
						rf.put(mch, list10);
						
					}
				}

				mch = 11;
				Pattern p5 = Pattern
						.compile("M3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m5 = p5.matcher(t);
				while (m5.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM3="
							+ m5.group(1) + "\nT3=" + m5.group(2));

					ArrayList<Double> list11 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list11 == null) {
						list11 = new ArrayList<Double>();
						list11.add(Double.valueOf(m5.group(1)));
						list11.add(Double.valueOf(m5.group(2)));
						rf.put(mch, list11);
						
					}
				}

				mch = 12;
				Pattern p6 = Pattern
						.compile("M4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m6 = p6.matcher(t);
				while (m6.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM4="
							+ m6.group(1) + "\nT4=" + m6.group(2));

					ArrayList<Double> list12 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list12 == null) {
						list12 = new ArrayList<Double>();
						list12.add(Double.valueOf(m6.group(1)));
						list12.add(Double.valueOf(m6.group(2)));
						rf.put(mch, list12);
						
					}
				}

				mch = 13;
				Pattern p7 = Pattern
						.compile("M5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m7 = p7.matcher(t);
				while (m7.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM5="
							+ m7.group(1) + "\nT5=" + m7.group(2));

					ArrayList<Double> list13 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list13 == null) {
						list13 = new ArrayList<Double>();
						list13.add(Double.valueOf(m7.group(1)));
						list13.add(Double.valueOf(m7.group(2)));
						rf.put(mch, list13);
						
					}
				}

				mch = 14;
				Pattern p8 = Pattern
						.compile("M6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m8 = p8.matcher(t);
				while (m8.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM6="
							+ m8.group(1) + "\nT6=" + m8.group(2));

					ArrayList<Double> list14 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list14 == null) {
						list14 = new ArrayList<Double>();
						list14.add(Double.valueOf(m8.group(1)));
						list14.add(Double.valueOf(m8.group(2)));
						rf.put(mch, list14);
						
					}
				}

				mch = 15;
				Pattern p9 = Pattern
						.compile("M7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m9 = p9.matcher(t);
				while (m9.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM7="
							+ m9.group(1) + "\nT7=" + m9.group(2));

					ArrayList<Double> list15 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list15 == null) {
						list15 = new ArrayList<Double>();
						list15.add(Double.valueOf(m9.group(1)));
						list15.add(Double.valueOf(m9.group(2)));
						rf.put(mch, list15);
					
					}
				}

				mch = 16;
				Pattern p0 = Pattern
						.compile("M8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m0 = p0.matcher(t);
				while (m0.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM8="
							+ m0.group(1) + "\nT8=" + m0.group(2));

					ArrayList<Double> list16 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list16 == null) {
						list16 = new ArrayList<Double>();
						list16.add(Double.valueOf(m0.group(1)));
						list16.add(Double.valueOf(m0.group(2)));
						rf.put(mch, list16);
						
					}
				}

				Pattern p11 = Pattern
						.compile("Water1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m11 = p11.matcher(t);
				while (m11.find()) {
					System.out.println("模块数据：\nWater1=" + m11.group(1));
				}

				Pattern p12 = Pattern
						.compile("Water2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m12 = p12.matcher(t);
				while (m12.find()) {
					System.out.println("模块数据：\nWater2=" + m12.group(1));
				}

				Pattern p13 = Pattern
						.compile("Water3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m13 = p13.matcher(t);
				while (m13.find()) {
					System.out.println("模块数据：\nWater3=" + m13.group(1));
				}

				Pattern p14 = Pattern
						.compile("Water4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m14 = p14.matcher(t);
				while (m14.find()) {
					System.out.println("模块数据：\nWater4=" + m14.group(1));
				}

				Pattern p15 = Pattern
						.compile("WinSpeed1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m15 = p15.matcher(t);
				while (m15.find()) {
					System.out.println("模块数据：\nWinSpeed1=" + m15.group(1)
							+ "\nRealDir1=" + m15.group(2));
				}

				Pattern p16 = Pattern
						.compile("WinSpeed2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m16 = p16.matcher(t);
				while (m16.find()) {
					System.out.println("模块数据：\nWinSpeed2=" + m16.group(1)
							+ "\nRealDir2=" + m16.group(2));
				}
				
				
			} else if (mode == 3) {
				mch = 17;
				//System.out.println("模块号为：" + s2 + " 电阻测量模块");
				System.out.println("模块号为：" + s2 + " 振弦测量模块");
				Pattern p3 = Pattern
						.compile("M1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m3 = p3.matcher(t);
				while (m3.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM1="
							+ m3.group(1) + "\nT1=" + m3.group(2));

					ArrayList<Double> list17 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list17 == null) {
						list17 = new ArrayList<Double>();
						list17.add(Double.valueOf(m3.group(1)));
						list17.add(Double.valueOf(m3.group(2)));
						rf.put(mch, list17);
						
					}
				}

				mch = 18;
				Pattern p4 = Pattern
						.compile("M2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m4 = p4.matcher(t);
				while (m4.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM2="
							+ m4.group(1) + "\nT2=" + m4.group(2));

					ArrayList<Double> list18 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list18 == null) {
						list18 = new ArrayList<Double>();
						list18.add(Double.valueOf(m4.group(1)));
						list18.add(Double.valueOf(m4.group(2)));
						rf.put(mch, list18);
						
					}
				}

				mch = 19;
				Pattern p5 = Pattern
						.compile("M3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m5 = p5.matcher(t);
				while (m5.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM3="
							+ m5.group(1) + "\nT3=" + m5.group(2));

					ArrayList<Double> list19 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list19 == null) {
						list19 = new ArrayList<Double>();
						list19.add(Double.valueOf(m5.group(1)));
						list19.add(Double.valueOf(m5.group(2)));
						rf.put(mch, list19);
						
					}
				}

				mch = 20;
				Pattern p6 = Pattern
						.compile("M4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m6 = p6.matcher(t);
				while (m6.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM4="
							+ m6.group(1) + "\nT4=" + m6.group(2));

					ArrayList<Double> list20 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list20 == null) {
						list20 = new ArrayList<Double>();
						list20.add(Double.valueOf(m6.group(1)));
						list20.add(Double.valueOf(m6.group(2)));
						rf.put(mch, list20);
						
					}
				}

				mch = 21;
				Pattern p7 = Pattern
						.compile("M5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m7 = p7.matcher(t);
				while (m7.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM5="
							+ m7.group(1) + "\nT5=" + m7.group(2));

					ArrayList<Double> list21 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list21 == null) {
						list21 = new ArrayList<Double>();
						list21.add(Double.valueOf(m7.group(1)));
						list21.add(Double.valueOf(m7.group(2)));
						rf.put(mch, list21);
						
					}
				}

				mch = 22;
				Pattern p8 = Pattern
						.compile("M6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m8 = p8.matcher(t);
				while (m8.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM6="
							+ m8.group(1) + "\nT6=" + m8.group(2));

					ArrayList<Double> list22 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list22 == null) {
						list22 = new ArrayList<Double>();
						list22.add(Double.valueOf(m8.group(1)));
						list22.add(Double.valueOf(m8.group(2)));
						rf.put(mch, list22);
						
					}
				}

				mch = 23;
				Pattern p9 = Pattern
						.compile("M7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m9 = p9.matcher(t);
				while (m9.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM7="
							+ m9.group(1) + "\nT7=" + m9.group(2));

					ArrayList<Double> list23 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list23 == null) {
						list23 = new ArrayList<Double>();
						list23.add(Double.valueOf(m9.group(1)));
						list23.add(Double.valueOf(m9.group(2)));
						rf.put(mch, list23);
						
					}
				}

				mch = 24;
				Pattern p0 = Pattern
						.compile("M8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m0 = p0.matcher(t);
				while (m0.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM8="
							+ m0.group(1) + "\nT8=" + m0.group(2));

					ArrayList<Double> list24 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list24 == null) {
						list24 = new ArrayList<Double>();
						list24.add(Double.valueOf(m0.group(1)));
						list24.add(Double.valueOf(m0.group(2)));
						rf.put(mch, list24);
						
					}
				}

				Pattern p11 = Pattern
						.compile("Water1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m11 = p11.matcher(t);
				while (m11.find()) {
					System.out.println("模块数据：\nWater1=" + m11.group(1));
				}

				Pattern p12 = Pattern
						.compile("Water2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m12 = p12.matcher(t);
				while (m12.find()) {
					System.out.println("模块数据：\nWater2=" + m12.group(1));
				}

				Pattern p13 = Pattern
						.compile("Water3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m13 = p13.matcher(t);
				while (m13.find()) {
					System.out.println("模块数据：\nWater3=" + m13.group(1));
				}

				Pattern p14 = Pattern
						.compile("Water4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m14 = p14.matcher(t);
				while (m14.find()) {
					System.out.println("模块数据：\nWater4=" + m14.group(1));
				}

				Pattern p15 = Pattern
						.compile("WinSpeed1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m15 = p15.matcher(t);
				while (m15.find()) {
					System.out.println("模块数据：\nWinSpeed1=" + m15.group(1)
							+ "\nRealDir1=" + m15.group(2));
				}

				Pattern p16 = Pattern
						.compile("WinSpeed2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m16 = p16.matcher(t);
				while (m16.find()) {
					System.out.println("模块数据：\nWinSpeed2=" + m16.group(1)
							+ "\nRealDir2=" + m16.group(2));
				}
				
				
			} else if (mode == 4) {
				mch = 25;
				//System.out.println("模块号为：" + s2 + " 水文测量模块");
				System.out.println("模块号为：" + s2 + " 振弦测量模块");
				Pattern p3 = Pattern
						.compile("M1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m3 = p3.matcher(t);
				while (m3.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM1="
							+ m3.group(1) + "\nT1=" + m3.group(2));

					ArrayList<Double> list25 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list25 == null) {
						list25 = new ArrayList<Double>();
						list25.add(Double.valueOf(m3.group(1)));
						list25.add(Double.valueOf(m3.group(2)));
						rf.put(mch, list25);
						
					}
				}

				mch = 26;
				Pattern p4 = Pattern
						.compile("M2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m4 = p4.matcher(t);
				while (m4.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM2="
							+ m4.group(1) + "\nT2=" + m4.group(2));

					ArrayList<Double> list26 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list26 == null) {
						list26 = new ArrayList<Double>();
						list26.add(Double.valueOf(m4.group(1)));
						list26.add(Double.valueOf(m4.group(2)));
						rf.put(mch, list26);
					
					}
				}

				mch = 27;
				Pattern p5 = Pattern
						.compile("M3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m5 = p5.matcher(t);
				while (m5.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM3="
							+ m5.group(1) + "\nT3=" + m5.group(2));

					ArrayList<Double> list27 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list27 == null) {
						list27 = new ArrayList<Double>();
						list27.add(Double.valueOf(m5.group(1)));
						list27.add(Double.valueOf(m5.group(2)));
						rf.put(mch, list27);
						
					}
				}

				mch = 28;
				Pattern p6 = Pattern
						.compile("M4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m6 = p6.matcher(t);
				while (m6.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM4="
							+ m6.group(1) + "\nT4=" + m6.group(2));

					ArrayList<Double> list28 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list28 == null) {
						list28 = new ArrayList<Double>();
						list28.add(Double.valueOf(m6.group(1)));
						list28.add(Double.valueOf(m6.group(2)));
						rf.put(mch, list28);
						
					}
				}

				mch = 29;
				Pattern p7 = Pattern
						.compile("M5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m7 = p7.matcher(t);
				while (m7.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM5="
							+ m7.group(1) + "\nT5=" + m7.group(2));

					ArrayList<Double> list29 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list29 == null) {
						list29 = new ArrayList<Double>();
						list29.add(Double.valueOf(m7.group(1)));
						list29.add(Double.valueOf(m7.group(2)));
						rf.put(mch, list29);
						
					}
				}

				mch = 30;
				Pattern p8 = Pattern
						.compile("M6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m8 = p8.matcher(t);
				while (m8.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM6="
							+ m8.group(1) + "\nT6=" + m8.group(2));

					ArrayList<Double> list30 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list30 == null) {
						list30 = new ArrayList<Double>();
						list30.add(Double.valueOf(m8.group(1)));
						list30.add(Double.valueOf(m8.group(2)));
						rf.put(mch, list30);
						
					}
				}

				mch = 31;
				Pattern p9 = Pattern
						.compile("M7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m9 = p9.matcher(t);
				while (m9.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM7="
							+ m9.group(1) + "\nT7=" + m9.group(2));

					ArrayList<Double> list31 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list31 == null) {
						list31 = new ArrayList<Double>();
						list31.add(Double.valueOf(m9.group(1)));
						list31.add(Double.valueOf(m9.group(2)));
						rf.put(mch, list31);
						
					}
				}

				mch = 32;
				Pattern p0 = Pattern
						.compile("M8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m0 = p0.matcher(t);
				while (m0.find()) {
					System.out.println("对应端子数据CH" + mch + "：\nM8="
							+ m0.group(1) + "\nT8=" + m0.group(2));

					ArrayList<Double> list32 = (ArrayList<Double>) rf.get(mch);// 根据key得到存放value
																				// 的list
					if (list32 == null) {
						list32 = new ArrayList<Double>();
						list32.add(Double.valueOf(m0.group(1)));
						list32.add(Double.valueOf(m0.group(2)));
						rf.put(mch, list32);
						
					}
				}

				Pattern p11 = Pattern
						.compile("Water1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m11 = p11.matcher(t);
				while (m11.find()) {
					System.out.println("模块数据：\nWater1=" + m11.group(1));
				}

				Pattern p12 = Pattern
						.compile("Water2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m12 = p12.matcher(t);
				while (m12.find()) {
					System.out.println("模块数据：\nWater2=" + m12.group(1));
				}

				Pattern p13 = Pattern
						.compile("Water3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m13 = p13.matcher(t);
				while (m13.find()) {
					System.out.println("模块数据：\nWater3=" + m13.group(1));
				}

				Pattern p14 = Pattern
						.compile("Water4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m14 = p14.matcher(t);
				while (m14.find()) {
					System.out.println("模块数据：\nWater4=" + m14.group(1));
				}

				Pattern p15 = Pattern
						.compile("WinSpeed1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m15 = p15.matcher(t);
				while (m15.find()) {
					System.out.println("模块数据：\nWinSpeed1=" + m15.group(1)
							+ "\nRealDir1=" + m15.group(2));
				}

				Pattern p16 = Pattern
						.compile("WinSpeed2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+");
				Matcher m16 = p16.matcher(t);
				while (m16.find()) {
					System.out.println("模块数据：\nWinSpeed2=" + m16.group(1)
							+ "\nRealDir2=" + m16.group(2));
				}
			} else
				System.out.println("未检测到模块数据！");
			mod = 0;
		}

		/**
		 * 获取测量数据
		 */
		/*
		 * Pattern p3 = Pattern.compile(
		 * "M1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m3 = p3.matcher(t); while (m3.find()) {
		 * System.out.println("模块一数据：\nM1="+m3.group(1)+"\nT1="+m3.group(2)); }
		 * 
		 * Pattern p4 = Pattern.compile(
		 * "M2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m4 = p4.matcher(t); while (m4.find()) {
		 * System.out.println("模块二数据：\nM2="+m4.group(1)+"\nT2="+m4.group(2)); }
		 * 
		 * Pattern p5 = Pattern.compile(
		 * "M3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m5 = p5.matcher(t); while (m5.find()) {
		 * System.out.println("模块三数据：\nM3="+m5.group(1)+"\nT3="+m5.group(2)); }
		 * 
		 * Pattern p6 = Pattern.compile(
		 * "M4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m6 = p6.matcher(t); while (m6.find()) {
		 * System.out.println("模块四数据：\nM4="+m6.group(1)+"\nT4="+m6.group(2)); }
		 * 
		 * Pattern p7 = Pattern.compile(
		 * "M5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T5=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m7 = p7.matcher(t); while (m7.find()) {
		 * System.out.println("模块五数据：\nM5="+m7.group(1)+"\nT5="+m7.group(2)); }
		 * 
		 * Pattern p8 = Pattern.compile(
		 * "M6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T6=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m8 = p8.matcher(t); while (m8.find()) {
		 * System.out.println("模块六数据：\nM6="+m8.group(1)+"\nT6="+m8.group(2)); }
		 * 
		 * Pattern p9 = Pattern.compile(
		 * "M7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T7=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m9 = p9.matcher(t); while (m9.find()) {
		 * System.out.println("模块七数据：\nM7="+m9.group(1)+"\nT7="+m9.group(2)); }
		 * 
		 * Pattern p0 = Pattern.compile(
		 * "M8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+T8=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m0 = p0.matcher(t); while (m0.find()) {
		 * System.out.println("模块八数据：\nM8="+m0.group(1)+"\nT8="+m0.group(2)); }
		 * 
		 * Pattern p11 =
		 * Pattern.compile("Water1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m11 = p11.matcher(t); while (m11.find()) {
		 * System.out.println("模块数据：\nWater1="+m11.group(1)); }
		 * 
		 * Pattern p12 =
		 * Pattern.compile("Water2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m12 = p12.matcher(t); while (m12.find()) {
		 * System.out.println("模块数据：\nWater2="+m12.group(1)); }
		 * 
		 * Pattern p13 =
		 * Pattern.compile("Water3=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m13 = p13.matcher(t); while (m13.find()) {
		 * System.out.println("模块数据：\nWater3="+m13.group(1)); }
		 * 
		 * Pattern p14 =
		 * Pattern.compile("Water4=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m14 = p14.matcher(t); while (m14.find()) {
		 * System.out.println("模块数据：\nWater4="+m14.group(1)); }
		 * 
		 * Pattern p15 = Pattern.compile(
		 * "WinSpeed1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir1=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m15 = p15.matcher(t); while (m15.find()) {
		 * System.out.println
		 * ("模块数据：\nWinSpeed1="+m15.group(1)+"\nRealDir1="+m15.group(2)); }
		 * 
		 * Pattern p16 = Pattern.compile(
		 * "WinSpeed2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+RealDir2=([-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+)\\s+"
		 * ); Matcher m16 = p16.matcher(t); while (m16.find()) {
		 * System.out.println
		 * ("模块数据：\nWinSpeed2="+m16.group(1)+"\nRealDir2="+m16.group(2)); }
		 */

	}

	public void run1(String ss) throws IOException {

		/*
		 * ss = "08/08/20 08:09:25 M1ZXN M1=2789.6 T1=-10.0 M2=4456.0 " +
		 * "T2=20.5 M3=6635.7 T3=18.9 M4=4311.0 T4=25.0 M5=4564.8 " +
		 * "T5=20.0 M6=3210.5 T6=40.0 M7=3333.6 T7=-15.0 M8=0.0 T8=----\r\n" +
		 * "08/08/29 08:23:25 M1ZXN M1=2789.6 T1=-10.0 M2=4456.0 " +
		 * "T2=20.5 M3=6635.7 T3=18.9 M4=4311.0 T4=25.0 M5=4564.8 " +
		 * "T5=20.0 M6=3210.5 T6=40.0 M7=3333.6 T7=-15.0 M8=0.0 T8=----\r\n" +
		 * "12/09/28 14:22:05 M2SWNGA Water1=10.000 Water2=20.000 Water3=30.000 "
		 * + "Water4=40.000 M5=0.000 M6=0.000 M7=0.000 M8=0.000 " +
		 * "WinSpeed1=0.0 RealDir1=0.0 WinSpeed2=0.0 RealDir2=0.0 'Q";
		 */
		// byte[] b = st.getBytes();
		// String t = new String(b);
		System.out.println("begin……\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(ss.getBytes(Charset.forName("utf8"))),
				Charset.forName("utf8")));
		String line = null;
		while ((line = br.readLine()) != null) {
			// System.out.println(line);
			getStrings(line); // 用正则表达式获取指定字符串内容中的指定内容
		}

		System.out.println("\n存储在TreeMap中的数据输出：");
		it = rf.entrySet().iterator();
		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			ArrayList value = (ArrayList) entry.getValue();
			System.out.println("key=" + key + "\t" + " value=" + value.get(0)
					+ "\t" + value.get(1));
		}

	}

}
