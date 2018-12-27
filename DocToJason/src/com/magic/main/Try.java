package com.magic.main;


import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Try {
	
	public static void main(String[] args) {
		
		ArrayList list=new ArrayList<>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		JSONObject obj=new JSONObject();
		/*JSONObject obj2=new JSONObject();
		JSONObject obj3=new JSONObject();

		JSONArray array =new JSONArray();
		
		for(int i=0; i<list.size(); i++) {
			array.add(list.get(i));
		}
		System.out.println(array);
		obj2.put("xyz1", array);
		obj3.put("xyz2", obj2);
		obj.put("ttt", obj3);*/
		
		for(int i=0; i<4; i++) {
			
			JSONObject obj1=new JSONObject();
			obj1.put(i, "value"+i);
			obj.put("asd"+i,obj1);
		}
		
		
		/*JSONObject obj4=new JSONObject();
		obj4.put("asdf", "Taushif");
		obj4.clone();
		
		obj.putAll(obj4);
		obj.putAll(obj4);
		obj.putAll(obj4);*/
		
		System.out.println(obj);
	}

}
