package com.tb.web.controller.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class text {

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

	public static String Demo2() {
		String keys = "";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 100);
		map.put("b", 200);
		map.put("c", 5);
		map.put("d", 3);

		Collection<Integer> count = map.values();
		Object[] objArray = count.toArray();
		
		int max = (int)objArray[0];
		for (int i = 1; i < objArray.length; i++) {
			if ((int)objArray[i] > max) {
				max = (int)objArray[i];
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
	
	public static String Demo1(int a){
		String str ="";
		String[] strs={"零","一","二","三","四","五","六","七","八","九","十"};
		if(a>9){
			String str1 =Integer.toString(a);
			 int b = Integer.valueOf(str1.substring(0, 1)).intValue();
			 int c =Integer.valueOf(str1.substring(1, 2)).intValue();
			 str =strs[b]+strs[c];
		}else{
			str =strs[a];
		}
		return str;
	}

	public static void main(String[] args) {
		
		 // Map<String,Integer> map =text.Demo3(); 
		 
		 
		System.out.println(text.Demo1(321));
	}
}
