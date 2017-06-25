package com.nancy.testscripts;

import org.testng.annotations.Test;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.DriverBase;
import com.nancy.util.MyReporter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class AddMultipleDressToCart extends DriverBase {
	@Test
	public void AddMultipleDressToCart() throws Exception {
		MyReporter.StartTestCase(this.getClass().getName());
		CommonFunction.Click(driver, "HomePage.WOMEN.Menu2");
		CommonFunction.Click(driver, "ShowingProducts.ViewList.button");
		List<WebElement> addToCartButtons = CommonFunction.findElements(driver,
				"ShowingProducts.ProductList.AddToCard.Buttons");
		System.out.println(addToCartButtons.size());
		String itemsCountMessage;
		String productTitleInPopUp;
		int i = 0;
		for (WebElement we : addToCartButtons) {
			i = i + 1;
			we.click();
			Thread.sleep(20000l);
			itemsCountMessage = CommonFunction.getText(driver, "AddToCartPopUp.itemsCount.text");
			productTitleInPopUp = CommonFunction.getText(driver, "AddToCartPopUp.productTitle.text");
			System.out.println(productTitleInPopUp);
			System.out.println(itemsCountMessage);
			CommonFunction.createReport(this.getClass().getName(), String.valueOf(i), itemsCountMessage, "商品成功添加到购物车");
			CommonFunction.Click(driver, "AddToCartPopUp.close.button");
		}
		MyReporter.EndTestCase(this.getClass().getName());
	}

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browser) {
		this.setDriver(browser);
		driver.get(ProjectConstants.BaseUrl);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		driver.close();
		driver.quit();
	}

}
