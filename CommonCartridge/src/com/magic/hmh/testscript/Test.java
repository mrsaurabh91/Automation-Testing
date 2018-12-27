package com.magic.hmh.testscript;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TestScript.class });
		testng.addListener(tla);
		testng.run();
	}

}
