package com.org.adobevalidations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class HBValidationsEpisode {
	public static String reportFolder = "";
	private static ExtentReports extent;
	public static ExtentTest test;
	public ReadDataSheet rds = new ReadDataSheet();
	public static String currentTest;

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// dd/MM/yyyy
			Date now = new Date();
			reportFolder = "HtmlReport_" + sdfDateReport.format(now);
			String s = new File("ReportGenerator/" + reportFolder + "/TestReport.html").getPath();
			extent = new ExtentReports(s, true);

			// extent = new ExtentReports(s, true, Locale.ENGLISH);

		}
		return extent;
	}

	@BeforeSuite
	public void executeSuite(ITestContext ctx) {
		try {
			// ;
			extent = getReporter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@AfterMethod
	protected void afterMethod(ITestResult result) throws IOException {
		extent.endTest(test);
		extent.flush();

	}

	@AfterSuite
	public void finishExecution() throws Exception {
		try {
			extent.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Test
	public void HBValidationsEpisode(Method method) throws Exception {

		currentTest = method.getName();
		System.out.println("********4444444**********"+currentTest+"***********4444444444************");
		test = extent.startTest(currentTest);
		
		System.out.println(currentTest);
		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		String FeedURL = rds.getValue("DATA", currentTest, "FeedURL");
		String ShowType = rds.getValue("DATA", currentTest, "ShowType");

		String SheetName = rds.getValue("DATA", currentTest, "SheetName");

		

		// Create object for the pageobject 
		AdobeValidations hb = new AdobeValidations();

		hb.HBValidation(ShowType, SheetName, HarFile_Path, FeedURL);
	}

	@Test
	public void HBValidationsLive(Method method) throws Exception {
		currentTest = method.getName();
		System.out.println("********4444444**********"+currentTest+"***********4444444444************");
		test = extent.startTest(currentTest);

		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		String FeedURL = rds.getValue("DATA", currentTest, "FeedURL");
		String ShowType = rds.getValue("DATA", currentTest, "ShowType");

		String SheetName = rds.getValue("DATA", currentTest, "SheetName");
		String ExecutionStatus = rds.getValue("EvalutionLive", currentTest, "ExecutionStatus");
		String EventType = rds.getValue("EvalutionLive", currentTest, "EventType");
		String EventValue = rds.getValue("EvalutionLive", currentTest, "EventValue");
		String AssetType = rds.getValue("EvalutionLive", currentTest, "AssetType");
		String AssetValue = rds.getValue("EvalutionLive", currentTest, "AssetValue");

		// Create object for the pageobject 
		AdobeValidations hb = new AdobeValidations();

		hb.HBValidation(ShowType, SheetName, HarFile_Path, FeedURL);
	}

	@Test
	public void HBValidationsClip(Method method) throws Exception {
		currentTest = method.getName();
		System.out.println("********4444444**********"+currentTest+"***********4444444444************");
		test = extent.startTest(currentTest);
		// String Event_Type = rds.getValue("DATA", currentTest, "Event_Type");
		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		String FeedURL = rds.getValue("DATA", currentTest, "FeedURL");
		String ShowType = rds.getValue("DATA", currentTest, "ShowType");

		String SheetName = rds.getValue("DATA", currentTest, "SheetName");
		String ExecutionStatus = rds.getValue("Evalutaion", currentTest, "ExecutionStatus");
		String EventType = rds.getValue("Evalutaion", currentTest, "EventType");
		String EventValue = rds.getValue("Evalutaion", currentTest, "EventValue");
		String AssetType = rds.getValue("Evalutaion", currentTest, "AssetType");
		String AssetValue = rds.getValue("Evalutaion", currentTest, "AssetValue");

		// Create object for the pageobject 
		AdobeValidations hb = new AdobeValidations();

		hb.HBValidation(ShowType, SheetName, HarFile_Path, FeedURL);
	}

	@Test
	public void HBAnalyticsSCLive(Method method) throws Exception {
		currentTest = method.getName();
		System.out.println("********4444444**********"+currentTest+"***********4444444444************");
		test = extent.startTest(currentTest);
		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		System.out.println(HarFile_Path);
		AdobeValidations hb = new AdobeValidations();
		hb.HBValidation_analytics_Live(HarFile_Path);

	}

	@Test
	public void HBAnalyticsSCVOD(Method method) throws Exception {
		currentTest = method.getName();
		System.out.println("********4444444**********"+currentTest+"***********4444444444************");
		test = extent.startTest(currentTest);
		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		String FeedURL = rds.getValue("DATA", currentTest, "FeedURL");
		String ShowType = rds.getValue("DATA", currentTest, "ShowType");
		AdobeValidations hb = new AdobeValidations();
		hb.HBValidation_analytics_VOD(HarFile_Path);

	}
}
