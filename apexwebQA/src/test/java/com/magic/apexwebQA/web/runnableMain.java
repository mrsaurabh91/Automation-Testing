package com.magic.apexwebQA.web;
import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;


public class runnableMain {
	

	public static void main(String[] args) throws Exception {
		
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add("testng.xml");
		testng.setTestSuites(suites);
		testng.run();
	}

}
