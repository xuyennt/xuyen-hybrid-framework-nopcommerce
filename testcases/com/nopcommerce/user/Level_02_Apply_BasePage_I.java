package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;

public class Level_02_Apply_BasePage_I {
	WebDriver driver;
	String emailAddress;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	//khai bao
	BasePage basePage;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		//khoi tao
		basePage = new BasePage();
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");
	}
	@Test
	public void TC_01_Register_Empty_Data() {
		basePage.waitForElementClickable(driver, "//a[@class='ico-register']");
		
		basePage.clickToElement(driver, "//a[@class='ico-register']");
		
		basePage.waitForElementClickable(driver, "//button[@id='register-button']");
		basePage.clickToElement(driver, "//button[@id='register-button']");
	
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required.");
	}
	@Test
	public void TC_02_Register_Invalid_Email() {
	//	basePage.waitForElementClickable(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");
		basePage.sendKeyToElement(driver, "//input[@id='FirstName']", "Automation");
		basePage.sendKeyToElement(driver, "//input[@id='LastName']", "FC");
		basePage.sendKeyToElement(driver, "//input[@id='Email']", "12345@6789");
		basePage.sendKeyToElement(driver, "//input[@id='Password']", "123456");
		basePage.sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
		
		//basePage.waitForElementClickable(driver, "//a[@id='register-button']");
		basePage.clickToElement(driver, "//button[@id='register-button']");
		
		Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Wrong email");
		
	}
	
	@Test
	public void TC_03_Register_Success() {
		//basePage.waitForElementClickable(driver, "//a[@class='ico-register']");
		basePage.clickToElement(driver, "//a[@class='ico-register']");
		basePage.sendKeyToElement(driver, "//input[@id='FirstName']", "Automation");
		basePage.sendKeyToElement(driver, "//input[@id='LastName']", "FC");
		basePage.sendKeyToElement(driver, "//input[@id='Email']",emailAddress);
		basePage.sendKeyToElement(driver, "//input[@id='Password']", "123456");
		basePage.sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");
		
		//basePage.waitForElementClickable(driver, "//a[@id='register-button']");
		basePage.clickToElement(driver, "//button[@id='register-button']");

		Assert.assertEquals(basePage.getElementText(driver, "//div[@class='result']"), "Your registration completed");
	
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
