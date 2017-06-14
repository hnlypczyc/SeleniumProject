package com.nancy.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nancy.commonfunction.CommonFunction;

public class LoginAction {

	private String LoginAccount;
	private String password;
	private CommonFunction commonFunction;
	private WebDriver wd;
	public LoginAction(WebDriver wd, String LoginAccount, String password){
		this.LoginAccount=LoginAccount;
		this.password = password;
		this.wd = wd;
		commonFunction = new CommonFunction();
	}
	
	public void doLogin(){
		WebDriverWait wdw = new WebDriverWait(wd,20);

		commonFunction.Click(wd, "HomePage.Signin");
		wdw.until(ExpectedConditions.titleIs("Login - My Store"));
		commonFunction.sendKeys(wd, "LoginPage.EmailAddress.Edit", this.LoginAccount);
		commonFunction.sendKeys(wd, "LoginPage.Password.Edit", this.password);
		commonFunction.Click(wd, "LoginPage.SignIn.Button");
		wdw.until(ExpectedConditions.titleIs("My account - My Store"));
		
	}
}
