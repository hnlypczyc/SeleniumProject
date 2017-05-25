package com.nancy.testscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
	private LocatorUtil locatorUtil;
	private CommonFunction commonFunction;

	@DataProvider(name = "SearchProduct")
	public Object[][] GetSearchProduct() {
		return new Object[][] { { "dress" } };
	}

	@Test(dataProvider = "SearchProduct")
	public void SearchAndViewDetail(String searchProduct) {
		Log.StartTestCase(this.getClass().getName());	
		commonFunction.sendKeys(wd, "HomePage.Search.Edit", searchProduct);
		commonFunction.Click(wd, "HomePage.Search.Button");
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		commonFunction.Click(wd, "SearchResultPage.FirstProduct.Image");
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		String productTitle = commonFunction.getText(wd,"DetailPage.ProductTitle.Text");
		if (commonFunction.ContainText(productTitle.toLowerCase(), searchProduct.toLowerCase())) {
			Log.info("验证商品名称是否符合search条件要求, Pass");
			Assert.assertTrue(true,"验证商品名称是否符合search条件要求");
		} else {
			Log.error("验证商品名称是否符合search条件要求, Fail, 实际结果为："+ productTitle);
			Log.EndTestCase(this.getClass().getName());
			Assert.assertTrue(false, "验证商品名称是否符合search条件要求");
		}
		
		String productQuantity = commonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit","value");
		commonFunction.Click(wd, "DetailPage.ProductQuantityAdd.Button");
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String AddProductQuantity = commonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit","value");
		try{
			Log.info("AddProductQuantity"+AddProductQuantity);
			Log.info("productQuantity:"+productQuantity);
			Assert.assertEquals(AddProductQuantity, String.valueOf((Integer.parseInt(productQuantity)+1)));
			Log.info("验证点击商品数量后的加号，显示的商品总数量相应的添加1, Pass");
		}catch(AssertionError error){
			Log.error("验证点击商品数量后的加号，显示的商品总数量相应的添加1, Fail");
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("验证点击商品数量后的加号，显示的商品总数量相应的添加1");
		}
		
		commonFunction.Click(wd, "DetailPage.ProductQuantityMinus.Button");
		String MinusProductQuantity = commonFunction.getAttributeValue(wd, "DetailPage.ProductQuantityDisplay.Edit","value");
		
		try{
			Assert.assertEquals(AddProductQuantity, String.valueOf((Integer.parseInt(MinusProductQuantity)+1)));
			Log.info("验证点击商品数量后的减号，显示的商品总数量相应的减1, Pass");
		}catch(AssertionError error){
			Log.error("验证点击商品数量后的减号，显示的商品总数量相应的减1, Fail");
			Log.EndTestCase(this.getClass().getName());

			Assert.fail("验证点击商品数量后的减号，显示的商品总数量相应的减1");
		}
		
		commonFunction.selectByVisibleText(wd, "DetailPage.ProductSize.Select", "M");
		
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		commonFunction.selectByVisibleText(wd, "DetailPage.ProductSize.Select", "M");
		commonFunction.Click(wd, "DetailPage.ProductColor.Image");
		String productColor = commonFunction.getAttributeValue(wd, "DetailPage.ProductColor.Image", "name");
		
		commonFunction.Click(wd, "DetailPage.Product.AddToCart.Button");
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		commonFunction.Click(wd, "AddToCardPopUp.Div");
		String addSuccess= commonFunction.getText(wd, "AddToCardPopUp.SuccessMessage.Text");
		String addToCartMessage=commonFunction.getText(wd, "AddToCardPopUp.ThereIs1ItemInCart.Text");
		Log.info("addSuccess: "+addSuccess);
		Log.info("addToCartMessage: "+addToCartMessage);

		commonFunction.Click(wd, "AddToCardPopUp.ProceedToCheckOut.Button");
		try{
			Assert.assertEquals(addSuccess, "Product successfully added to your shopping cart");
			Log.info("验证添加商品到购物车成功信息, Pass");
		}catch(AssertionError error){
			Log.error("验证添加商品到购物车成功信息, Fail");
			Log.EndTestCase(this.getClass().getName());
			Assert.fail("验证添加商品到购物车成功信息");
		}
			Log.EndTestCase(this.getClass().getName());
	}

	@BeforeTest
	public void BeforeTest() {
		System.setProperty("webdriver.chrome.driver",
				ProjectConstants.ChromeDrivePath);
		commonFunction = new CommonFunction();
		wd = new ChromeDriver();
		wd.get(ProjectConstants.BaseUrl);
	}

	@AfterTest
	public void AfterTest() {
		//wd.close();
	}
}
