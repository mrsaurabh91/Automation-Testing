package com.magic.hmh.testscript;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.magic.hmh.generic.BaseClass;

public class TestScript extends BaseClass{

	@Test
	public void main() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		TestRun testRun = new TestRun(this, dvr);
		testRun.runSript();
	}
}
