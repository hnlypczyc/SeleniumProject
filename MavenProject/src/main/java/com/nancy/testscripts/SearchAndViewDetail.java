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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.nancy.commonfunction.CommonFunction;
import com.nancy.constants.ProjectConstants;
import com.nancy.util.DriverBase;
import com.nancy.util.ExcelManager;
import com.nancy.util.LocatorUtil;
import com.nancy.util.MyReporter;

public class SearchAndViewDetail extends DriverBase {
	private ExcelManager em;
	private String className;
	private String testCaseName;

	@DataProvider(name = "SearchProduct")
	public Iterator<Object[]> getTestData(){
		System.out.println("getTestData:");
		return em.getAllTestData(testCaseName);
	}

	@Test(dataProvider = "SearchProduct")
	public void SearchAndViewDetail(String rowIndex,String searchProduct) throws Exception {
		String currentTestCase = this.getClass().getSimpleName();

		MyReporter.StartTestCase(this.getClass().getName());
		CommonFunction.sendKeys(driver, "HomePage.Search.Edit", searchProduct);
		CommonFunction.Click(driver, "HomePage.Search.Button");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		CommonFunction.Click(driver, "SearchResultPage.FirstProduct.Image");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		String productTitle = CommonFunction.getText(driver, "DetailPage.ProductTitle.Text");
		CommonFunction.createReport(currentTestCase, "true",
				String.valueOf(CommonFunction.ContainText(productTitle.toLowerCase(), searchProduct.toLowerCase())),
				"验证商品名称是否符合search条件要求");
		String productQuantity = CommonFunction.getAttributeValue(driver, "DetailPage.ProductQuantityDisplay.Edit",
				"value");
		CommonFunction.Click(driver, "DetailPage.ProductQuantityAdd.Button");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String AddProductQuantity = CommonFunction.getAttributeValue(driver, "DetailPage.ProductQuantityDisplay.Edit",
				"value");
		CommonFunction.createReport(currentTestCase, AddProductQuantity,
				String.valueOf((Integer.parseInt(productQuantity) + 1)), "验证点击商品数量后的加号，显示的商品总数量相应的添加1");
		CommonFunction.Click(driver, "DetailPage.ProductQuantityMinus.Button");
		String MinusProductQuantity = CommonFunction.getAttributeValue(driver, "DetailPage.ProductQuantityDisplay.Edit",
				"value");

		CommonFunction.createReport(currentTestCase, AddProductQuantity,
				String.valueOf((Integer.parseInt(MinusProductQuantity) + 1)), "验证点击商品数量后的减号，显示的商品总数量相应的减1");
		CommonFunction.selectByVisibleText(driver, "DetailPage.ProductSize.Select", "M");

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		CommonFunction.selectByVisibleText(driver, "DetailPage.ProductSize.Select", "M");
		CommonFunction.Click(driver, "DetailPage.ProductColor.Image");
		String productColor = CommonFunction.getAttributeValue(driver, "DetailPage.ProductColor.Image", "name");

		CommonFunction.Click(driver, "DetailPage.Product.AddToCart.Button");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Thread.sleep(10000L);
		CommonFunction.Click(driver, "AddToCardPopUp.Div");
		String addSuccess = CommonFunction.getText(driver, "AddToCardPopUp.SuccessMessage.Text");
		CommonFunction.createReport(currentTestCase, "Product successfully added to your shopping cart",
				addSuccess.trim(), "验证添加商品到购物车成功信息");
		CommonFunction.Click(driver, "AddToCardPopUp.ProceedToCheckOut.Button");
		MyReporter.EndTestCase(currentTestCase);
	}
	@Parameters("browser")
	@BeforeClass
	public void BeforeTest(String browser) {
		System.setProperty("webdriver.chrome.driver", ProjectConstants.ChromeDrivePath);
		this.setDriver(browser);
		className= this.getClass().getSimpleName();
		testCaseName = CommonFunction.getTestCaseNameByClassName(className);
		em = new ExcelManager(ProjectConstants.testSuiteExcelPath);
		driver.get(ProjectConstants.BaseUrl);
	}

	@AfterClass
	public void AfterTest() {
		driver.close();
	}
}
