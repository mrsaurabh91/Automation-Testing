package com.magic.apexwebQA.web.tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.magic.apexwebQA.pages.DashboardPage;
import com.magic.apexwebQA.pages.ExchangePage;
import com.magic.apexwebQA.utils.GenericUtil;
import com.magic.apexwebQA.utils.SeleniumUtil;
import com.magic.apexwebQA.web.AbstractWebTest;

public class PlacingOrderTest extends AbstractWebTest{

	String menuToSelect = "Exchange";
	String coinTypeToSelect = "BTCUSD";
	
	String buyAmount = SeleniumUtil.fetchUserDetails("BUY_AMOUNT");
	String limitPriceForBuy = SeleniumUtil.fetchUserDetails("BUY_LIMIT_PRICE");
	
	String sellAmount = SeleniumUtil.fetchUserDetails("SELL_AMOUNT");
	String limitPriceForSell = SeleniumUtil.fetchUserDetails("SELL_LIMIT_PRICE");

	@Test(priority = 1)
	public void placeBuyLimitOrderTest() throws InterruptedException {

		test = extent.createTest("Place Buy Limit Order Verification","This test verify whether user is able to Place Buy Limit Order or not");
		test.assignCategory("Buy Limit Order");

		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickMenu();
		dashboardPage.selectMenuWithParameter(menuToSelect);

		ExchangePage exchangePage = new ExchangePage(driver);
		exchangePage.clickCoinSelectionMenu();
		exchangePage.selectCoinAsPerParameter(coinTypeToSelect);

		exchangePage.enterBuyAmountUnderOrderEntry(buyAmount);
		exchangePage.enterLimitPriceUnderOrderEntry(limitPriceForBuy);

		exchangePage.clickOrderButton("Buy");

		ArrayList<String> currentDateTimeList = exchangePage.waitForButtonDisable("Place Buy Order");

		String expectedRow = "BTCUSD || Buy || Limit || "+buyAmount+".00000000 || "+limitPriceForBuy+".00 || "+currentDateTimeList.get(0);
		String expectedRow2 = "BTCUSD || Buy || Limit || "+buyAmount+".00000000 || "+limitPriceForBuy+".00 || "+currentDateTimeList.get(1);

		
		System.out.println("Possibility 1 is: "+expectedRow2);
		test.info("Possibility 1 is: "+expectedRow2);
		
		System.out.println("Possibility 2 is: "+expectedRow);
		test.info("Possibility 2 is: "+expectedRow);
		
		Thread.sleep(3000);

		ArrayList<String> listOfOpenOrders = exchangePage.getListOfOpenOrders();
		System.out.println("List of Open Orders: "+listOfOpenOrders);
		test.info("List of Open Orders: "+listOfOpenOrders);

		boolean result = false;

		if(listOfOpenOrders.contains(expectedRow) || listOfOpenOrders.contains(expectedRow2)) {
			result = true;
		}

		Assert.assertTrue(result);


	}

	@Test(priority = 2)
	public void placeSellLimitOrderTest() throws InterruptedException {

		test = extent.createTest("Place Sell Limit Order Verification","This test verify whether user is able to Place Sell Limit Order or not");
		test.assignCategory("Sell Limit Order");

		ExchangePage exchangePage = new ExchangePage(driver);
		exchangePage.clickCoinSelectionMenu();
		exchangePage.selectCoinAsPerParameter(coinTypeToSelect);

		exchangePage.clickSellTab();

		exchangePage.enterBuyAmountUnderOrderEntry(sellAmount);
		exchangePage.enterLimitPriceUnderOrderEntry(limitPriceForSell);

		exchangePage.clickOrderButton("Sell");
				
		ArrayList<String> currentDateTimeList = exchangePage.waitForButtonDisable("Place Sell Order");

		String expectedRow = "BTCUSD || Sell || Limit || "+sellAmount+".00000000 || "+limitPriceForSell+".00 || "+currentDateTimeList.get(0);
		String expectedRow2 = "BTCUSD || Sell || Limit || "+sellAmount+".00000000 || "+limitPriceForSell+".00 || "+currentDateTimeList.get(1);

		
		System.out.println("Possibility 1 is: "+expectedRow2);
		test.info("Possibility 1 is: "+expectedRow2);
		
		System.out.println("Possibility 2 is: "+expectedRow);
		test.info("Possibility 2 is: "+expectedRow);
		
		Thread.sleep(3000);

		ArrayList<String> listOfOpenOrders = exchangePage.getListOfOpenOrders();
		System.out.println("List of Open Orders: "+listOfOpenOrders);
		test.info("List of Open Orders: "+listOfOpenOrders);

		boolean result = false;

		if(listOfOpenOrders.contains(expectedRow) || listOfOpenOrders.contains(expectedRow2)) {
			result = true;
		}

		Assert.assertTrue(result);
	}

}
