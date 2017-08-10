package com.tb.web.controller.index;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class text extends HttpServlet{
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
	}

	private static Student student1 = null;
	private static Student student2 = null;
	
	public static void aa(){}

	public static void textss() {
		student1 = new Student();
		student1.setId(5237);
		student1.setName("jingshou");
		student1.setBirthday(new Date());
		student2 = new Student();
		student2.setId(5237654);
		student2.setName("jingsaddffffffffffffshou");
		student2.setBirthday(new Date());
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"));
		String Json;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Json = mapper.writeValueAsString(student1);
			System.out.println("Change Object to JSON String: " + Json);

			Student student3 = mapper.readValue(Json, Student.class);
			System.out.println(student3.toString());

			List<Student> stuList = new ArrayList<Student>();
			stuList.add(student2);
			stuList.add(student1);

			String jsonnn = mapper.writeValueAsString(stuList);
			
			JSONArray jsonArray = JSONArray.parseArray(jsonnn);
			 Iterator<Object> it = jsonArray.iterator();
			 List<Student> list=new ArrayList<Student>();
			  while (it.hasNext()) {
				  JSONObject ob = (JSONObject) it.next();
				  Student s = new Student();
				  s.setBirthday(DateUtils.parseDate(ob.getString("birthday"), new String[]{"yyyy-MM","yyyyMM","yyyy/MM",   
			                "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",   
			                "yyyyMMddHHmmss",   
			                            "yyyy-MM-dd HH:mm:ss",   
			                            "yyyy/MM/dd HH:mm:ss"}));
				  s.setId(Integer.parseInt(ob.getString("id")));

				  s.setName(ob.getString("name"));
				  list.add(s);
			  }
			  for (Student student : list) {
					System.out.println(student.toString());
			}
			
			List<Student> stuListss = mapper.readValue(jsonnn,
					mapper.getTypeFactory().constructParametricType(ArrayList.class, Student.class));
			for (Student student : stuListss) {
				System.out.println(student.toString());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 第三题
	public static Map<String, Integer> Demo3() {
		String sentence = "this is  realy  good news you can never good things like this";
		String[] str = sentence.split("\\s{1,}");
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (int i = 0; i < str.length; i++) {

			String words = str[i];
			if (words.length() > 0) {
				if (map.get(words) == null) {
					map.put(words, 1);
				} else {
					int count = map.get(words);
					map.put(words, ++count);
				}
			}
		}

		return map;

	}

	public static class basestudent  {

		private Date birthday;
		
		

	
		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
	}

	public static class Student extends basestudent {
		@Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", birthday=" +super.getBirthday()  + "]";
		}

		private int id;
		private String name;
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static String Demo2() {
		String keys = "";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 100);
		map.put("b", 200);
		map.put("c", 5);
		map.put("d", 3);

		Collection<Integer> count = map.values();
		Object[] objArray = count.toArray();

		int max = (int) objArray[0];
		for (int i = 1; i < objArray.length; i++) {
			if ((int) objArray[i] > max) {
				max = (int) objArray[i];
			}
		}

		for (String key : map.keySet()) {

			if (map.get(key) == max) {
				keys = key;
				break;
			}

		}
		return keys;
	}

	public static String Demo1(int a) {
		String str = "";
		String[] strs = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		if (a > 9) {
			String str1 = Integer.toString(a);
			int b = Integer.valueOf(str1.substring(0, 1)).intValue();
			int c = Integer.valueOf(str1.substring(1, 2)).intValue();
			str = strs[b] + strs[c];
		} else {
			str = strs[a];
		}
		return str;
	}

	public static boolean foo(char c) {
		System.out.print(c);
		return true;
	}

	public static void main(String[] args) {

		// Map<String,Integer> map =text.Demo3();

		// System.out.println(text.Demo1(321));

		/*
		 * int i =0; for (foo('A');foo('b')&&(i<2); foo('c')) { i++; foo('d');
		 * 
		 * }
		 */
		//System.out.println(~0010101);
		/*Map<String,Object> resp = new  HashMap<String,Object>();
		for (int i = 0; i < 13; i++) {
			resp.put("1"+i, "2"+i);
		}
		
		System.out.println("2017-07-14 10:21:26".substring(11, 13));*/
		
		SimpleDateFormat sdf= new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date a = sdf.parse("2017/7/28 15:00:25".replaceAll("/", "-"));
			Date b = sdf.parse("2017/7/28 15:03:26".replaceAll("/", "-"));
			long x=(b.getTime()-a.getTime())/1000;
			System.out.println(x);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
