package com.nancy.testscripts;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.LocatorUtil;
import com.nancy.util.Log;

public class SearchAndViewDetail {

	private WebDriver wd;

	@DataProvider(name = "SearchProduct")
	public Object[][] GetSearchProduct() {
		return new Object[][] { { "dress" } };
	}

	@Test(dataProvider = "SearchProduct")
	public void SearchAndViewDetail(String searchProduct) throws Exception {
		Log.StartTestCase(this.getClass().getName());
		CommonFunction.sendKeys(wd, "HomePage.Search.Edit", searchProduct);
		CommonFunction.Click(wd, "HomePage.Search.Button");
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		CommonFunction.Click(wd, "SearchResultPage.FirstProduct.Image");
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		String productTitle = CommonFunction.getText(wd, "DetailPage.ProductTitle.Text");
		if (CommonFunction.ContainText(productTitle.toLowerCase(), searchProduct.toLowerCase())) {
			Log.info("验证商品名称是否符合search条件要求, Pass");
			Assert.assertTrue(true, "验证商品名称是否符合search条件要求");
		} else {
			Log.error("验证商品名称是否符合search条件要求, Fail, 实际结果为：" + productTitle);
			Log.EndTestCase(this.getClass().getName());
			Assert.assertTrue(false, "验证商品名称是否符合search条件要求");
		}

		String productQuantity = CommonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit",
				"value");
		CommonFunction.Click(wd, "DetailPage.ProductQuantityAdd.Button");
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String AddProductQuantity = CommonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit",
				"value");
		try {
			Log.info("AddProductQuantity" + AddProductQuantity);
			Log.info("productQuantity:" + productQuantity);
			Assert.assertEquals(AddProductQuantity, String.valueOf((Integer.parseInt(productQuantity) + 1)));
			Log.info("验证点击商品数量后的加号，显示的商品总数量相应的添加1, Pass");
		} catch (AssertionError error) {
			Log.error("验证点击商品数量后的加号，显示的商品总数量相应的添加1, Fail");
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("验证点击商品数量后的加号，显示的商品总数量相应的添加1");
		}

		CommonFunction.Click(wd, "DetailPage.ProductQuantityMinus.Button");
		String MinusProductQuantity = CommonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit",
				"value");

		try {
			Assert.assertEquals(AddProductQuantity, String.valueOf((Integer.parseInt(MinusProductQuantity) + 1)));
			Log.info("验证点击商品数量后的减号，显示的商品总数量相应的减1, Pass");
		} catch (AssertionError error) {
			Log.error("验证点击商品数量后的减号，显示的商品总数量相应的减1, Fail");
			Log.EndTestCase(this.getClass().getName());

			Assert.fail("验证点击商品数量后的减号，显示的商品总数量相应的减1");
		}

		CommonFunction.selectByVisibleText(wd, "DetailPage.ProductSize.Select", "M");

		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		CommonFunction.selectByVisibleText(wd, "DetailPage.ProductSize.Select", "M");
		CommonFunction.Click(wd, "DetailPage.ProductColor.Image");
		String productColor = CommonFunction.getAttributeValue(wd, "DetailPage.ProductColor.Image", "name");

		CommonFunction.Click(wd, "DetailPage.Product.AddToCart.Button");
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		Thread.sleep(10000L);
		CommonFunction.Click(wd, "AddToCardPopUp.Div");
		String addSuccess = CommonFunction.getText(wd, "AddToCardPopUp.SuccessMessage.Text");
		Log.info("addSuccess: " + addSuccess);

		try {
			Assert.assertEquals(addSuccess.trim(), "Product successfully added to your shopping cart");
			Log.info("验证添加商品到购物车成功信息, Pass");
		} catch (AssertionError error) {
			Log.error("验证添加商品到购物车成功信息, Fail");
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("验证添加商品到购物车成功信息");
		}
		CommonFunction.Click(wd, "AddToCardPopUp.ProceedToCheckOut.Button");
		Log.EndTestCase(this.getClass().getName());
	}

	@BeforeClass
	public void BeforeTest() {
		System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
		wd = new ChromeDriver();
		//
		// System.setProperty("webdriver.firefox.marionette",ProjectConstants.FireFoxGeckoDriverPath);
		// wd=new FirefoxDriver();
		wd.get(ProjectConstants.BaseUrl);
	}

	@AfterClass
	public void AfterTest() {
		 wd.close();
	}
}
