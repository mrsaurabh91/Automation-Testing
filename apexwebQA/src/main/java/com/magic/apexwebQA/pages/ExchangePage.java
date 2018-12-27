package com.magic.apexwebQA.pages;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.magic.apexwebQA.utils.GenericUtil;
import com.magic.apexwebQA.utils.SeleniumUtil;

public class ExchangePage {

	private WebDriver driver;
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));

	private static final By COIN_SELECTION_MENU = By.xpath("//button[@class='instrument-selector__trigger']");
	private static final By AMOUNT_INPUTBOX = By.xpath("//input[@name='quantity']");
	private static final By LIMIT_INPUTBOX = By.xpath("//input[@name='limitPrice']");
	private static final By SELL_TAB = By.xpath("//label[@data-test='Sell Side']");


	public ExchangePage (WebDriver driver) {
		this.driver = driver;
	}

	public void clickCoinSelectionMenu() {

		SeleniumUtil.waitForElementPresence(driver, COIN_SELECTION_MENU, WAIT_SECONDS).click();
	}


	public void selectCoinAsPerParameter(String coinName) {

		SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@class='flex-table__body instrument-selector-popup__body']//div[text()='"+coinName+"']"), WAIT_SECONDS).click();
	}


	public void enterBuyAmountUnderOrderEntry(String value) {
		SeleniumUtil.waitForElementPresence(driver, AMOUNT_INPUTBOX, WAIT_SECONDS).clear();
		SeleniumUtil.waitForElementPresence(driver, AMOUNT_INPUTBOX, WAIT_SECONDS).sendKeys(value);
	}


	public void enterLimitPriceUnderOrderEntry(String value) {
		SeleniumUtil.waitForElementPresence(driver, LIMIT_INPUTBOX, WAIT_SECONDS).clear();
		SeleniumUtil.waitForElementPresence(driver, LIMIT_INPUTBOX, WAIT_SECONDS).sendKeys(value);
	}

	public void clickSellTab() {
		SeleniumUtil.waitForElementPresence(driver, SELL_TAB, WAIT_SECONDS).click();

	}

	public void clickOrderButton(String value) {

		SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='Place "+value+" Order']"), WAIT_SECONDS).click();

	}

	public ArrayList<String> waitForButtonDisable(String buttonTitle) throws InterruptedException {
		
		ArrayList<String> dateTimeList = new ArrayList<>();
		
		String dateTime = "";
		String dateTimeMinusOne = "";
		for(int i=0;i<=100;i++) {

			String cssCursorValue = SeleniumUtil.waitForElementPresence(driver, By.xpath("//button[text()='"+buttonTitle+"']"), WAIT_SECONDS).getCssValue("cursor");
			if(cssCursorValue.equals("not-allowed")) {
				
				String currentTime = GenericUtil.getCurrentTime();
				String currentTimeMinusOne = GenericUtil.getCurrentTimeMinusSecond();
				String currentDate = GenericUtil.getCurrentDate();
				
				dateTime = currentDate+" "+currentTime;
				dateTimeMinusOne = currentDate+" "+currentTimeMinusOne;
				
				dateTimeList.add(dateTime);
				dateTimeList.add(dateTimeMinusOne);
				
				System.out.println("Button got disabled");
				break;
			}

			Thread.sleep(100);
		}

		return dateTimeList;
	}


	public ArrayList<String> getListOfOpenOrders() {

		ArrayList<String> openOrderList = new ArrayList<>();

		int countOfOpenOrders = driver.findElements(By.xpath("//div[@class='flex-table__body order-history-table__body']/div")).size();

		for(int i=1;i<=countOfOpenOrders;i++) {

			String textFinal = "";

			int countItems = driver.findElements(By.xpath("(//div[@class='flex-table__body order-history-table__body']/div)["+i+"]/div")).size();

			for(int j=1;j<=(countItems-2);j++) {
				String text = SeleniumUtil.waitForElementPresence(driver, By.xpath("(//div[@class='flex-table__body order-history-table__body']/div)["+i+"]/div["+j+"]"), WAIT_SECONDS).getText();
				if(j==1) {
					textFinal = text;
				}else {
					textFinal = textFinal+" || "+text;
				}

			}

			openOrderList.add(textFinal);

		}
		return openOrderList;
	}
}
