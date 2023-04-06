package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;

public class Level_02_Apply_BasePage_III extends BasePage{
	WebDriver driver;
	String emailAddress;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");
	}
	@Test
	public void TC_01_Register_Empty_Data() {
		waitForElementClickable(driver, "//a[@class='ico-register']");
		
		clickToElement(driver, "//a[@class='ico-register']");
		
		waitForElementClickable(driver, "//button[@id='register-button']");
		clickToElement(driver, "//button[@id='register-button']");
	
		Assert.assertEquals(getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required.");
	}
	@Test
	public void TC_02_Register_Invalid_Email() {
	//	waitForElementClickable(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");
		sendKeyToElement(driver, "//input[@id='FirstName']", "Automation");
		sendKeyToElement(driver, "//input[@id='LastName']", "FC");
		sendKeyToElement(driver, "//input[@id='Email']", "12345@6789");
		sendKeyToElement(driver, "//input[@id='Password']", "123456");
		sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
		
		//waitForElementClickable(driver, "//a[@id='register-button']");
		clickToElement(driver, "//button[@id='register-button']");
		
		Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Wrong email");
		
	}
	
	@Test
	public void TC_03_Register_Success() {
		//waitForElementClickable(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//a[@class='ico-register']");
		sendKeyToElement(driver, "//input[@id='FirstName']", "Automation");
		sendKeyToElement(driver, "//input[@id='LastName']", "FC");
		sendKeyToElement(driver, "//input[@id='Email']",emailAddress);
		sendKeyToElement(driver, "//input[@id='Password']", "123456");
		sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
		
		//waitForElementClickable(driver, "//a[@id='register-button']");
		clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(getElementText(driver, "//div[@class='result']"), "Your registration completed");
	
	}
	
	@AfterClass
	public void afterClass() {
	  driver.quit();
		}
	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
		
	}

}
