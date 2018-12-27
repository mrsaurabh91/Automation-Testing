package com.magic.axis360.listeners;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.magic.axis360.common.BaseTest;
import com.magic.util.Log;
import com.magic.util.SeleniumUtil;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ResultListener extends BaseTest implements ITestListener {
	
	@Override
	public void onStart(ITestContext context) {
		DOMConfigurator.configure("log4j.xml");			
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		Log.startTestCase(getTestMethodName(result));	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.endTestCase(getTestMethodName(result));
		extentTest.log(Status.PASS, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Passed: ", ExtentColor.GREEN));
		extentTest.pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.info(getTestMethodName(result)+ " Test failed");
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Failed due to below issues: ", ExtentColor.RED));
		extentTest.fail(result.getThrowable());
		String screenShotPath = "";
		try {
			screenShotPath = SeleniumUtil.getScreenshot(driver, getTestMethodName(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			extentTest.addScreenCaptureFromPath(screenShotPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getTestMethodName(ITestResult result) {
		 return result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info(getTestMethodName(result)+ " test case skipped");
        extentTest.log(Status.SKIP, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Skipped: ", ExtentColor.ORANGE));
		extentTest.skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub		
	}	

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();		
	}
}
