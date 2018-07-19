package com.org.adobevalidations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ComscoreAnalyticsClip {
	public static String currentTest;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ReadDataSheet rds = new ReadDataSheet();
	public static String reportFolder = "";
	
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
	public void ComscoreAnalyticsClip(Method method) throws Exception {
		// Create object for the pageobject 
		currentTest = method.getName();
		test = extent.startTest(currentTest);
		String Event_Type = rds.getValue("DATA", currentTest, "Event_Type");
		String HarFile_Path = rds.getValue("DATA", currentTest, "HarFile_Path");
		String FeedURL = rds.getValue("DATA", currentTest, "FeedURL");
		String sheetName = rds.getValue("DATA", currentTest, "SheetName");
		// Create object for the pageobject 
		ComscoreFeature comscore = new ComscoreFeature();
		// Method to validate comscore app launch parameters
		comscore.comscoreValidation(sheetName, Event_Type, HarFile_Path, FeedURL);
	}

}