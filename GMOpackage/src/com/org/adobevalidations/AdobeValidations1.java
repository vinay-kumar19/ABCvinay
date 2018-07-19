package com.org.adobevalidations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderMode;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarLog;
import de.sstoehr.harreader.model.HarPostDataParam;

/**
 * Class repository for Home screen properties
 * 
 * @author 557743
 *
 */
public class AdobeValidations1 extends HBValidationsEpisode {

	public static List<String> assetDetails;
	CommonFunctions2 common = new CommonFunctions2();

	String hbUrl = "https://nbcume.hb.omtrdc.net/";
	String comscoreUrl = "http://b.scorecardresearch.com/";
	String adBeaconUrl = "http://29773.s.fwmrm.net";
	/* String optionsWE="name=Options"; */
	/* CPC Elements************ */

	int j = 2;
	int i = 0;
	// Android Elements

	ArrayList<String> HBUrls = new ArrayList<String>();
	ArrayList<String> comscoreUrls = new ArrayList<String>();
	ArrayList<String> configURLs = new ArrayList<String>();
	ArrayList<String> nielsonUrls = new ArrayList<String>();
	ArrayList<String> HBUrlsTimeStamp = new ArrayList<String>();
	ArrayList<String> comscoreUrlsTimeStamp = new ArrayList<String>();
	ArrayList<String> adBeaconsUrlsTimeStamp = new ArrayList<String>();
	ArrayList<String> configURLsTimeStamp = new ArrayList<String>();
	ArrayList<String> nielsonUrlsTimeStamp = new ArrayList<String>();
	ArrayList<String> feedUrls = new ArrayList<String>();
	ArrayList<String> feedUrlsTimeStamp = new ArrayList<String>();
	String URL = null;
	String Response;
	String appKey;
	String secretKey;
	String responseUrl;
	HashMap<String, String> excelValueMap = new HashMap<String, String>();
	HashMap<String, String> actualValueMap;
	ReadDataSheet rd = new ReadDataSheet();
	String platFormName = "iOS";

	public void HBValidation(String ShowType, String SheetName, String HarFile_Path, String feedUrl) throws Exception {

		try {
			if (platFormName.equalsIgnoreCase("iOS")) {
				ArrayList<String> adBeaconsUrls = new ArrayList<String>();

				String hbUrl = "http://nbcume.hb.omtrdc.net/?";
				// String hbUrl = "http://nbcume.hb.omtrdc.net/?";
				String comscoreUrl = "http://b.scorecardresearch.com/p2?";
				String analytics = "http://b.scorecardresearch.com/p2?";
				String adBeaconUrl = "http://29773.s.fwmrm.net/";
				String nielsonUrl = "https://secure-dcr-cert.imrworldwide.com";

				HarReader harReader = new HarReader();
		         Har har = harReader.readFromFile(new File(HarFile_Path), HarReaderMode.LAX);
	                de.sstoehr.harreader.model.HarLog log = har.getLog();
				/*Har har = HarReader.fromFile(new File(HarFile_Path), HarReaderMode.LAX);
                de.sstoehr.harreader.model.HarLog log = har.getLog();*/
				List<de.sstoehr.harreader.model.HarEntry> entries1 = har.getLog().getEntries();
				for (de.sstoehr.harreader.model.HarEntry entry : entries1) {
					if (entry.getRequest().getUrl().contains(hbUrl)) {
						System.out.println("im n HB call validation");
						HBUrls.add(entry.getRequest().getUrl());
						HBUrlsTimeStamp.add(entry.getStartedDateTime().toString());
					}
				}

				common.seriesOfEvents(HBUrls, hbUrl, HBUrlsTimeStamp);

				// code for validating and printing the values in a single file

				common.eventsIterator(HBUrls, hbUrl, SheetName, ShowType, feedUrl);
				

			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
		}
	}

	public void HBValidation_analytics_VOD(String HarFile_Path) throws Exception {
		try { // platFormName = TestRunner.platFormName();
			if (platFormName.equalsIgnoreCase("iOS")) {
				ArrayList<String> adBeaconsUrls = new ArrayList<String>();
				Boolean flag = false;
				String vid = "";
				String hbUrl = "http://nbcume.sc.omtrdc.net";
				String hbUrl1 = "http://nbcume.sc.omtrdc.net";
				String comscoreUrl = "http://b.scorecardresearch.com/p2?";
				String analytics = "http://b.scorecardresearch.com/p2?";
				String adBeaconUrl = "http://29773.s.fwmrm.net/";
				String nielsonUrl = "https://secure-dcr-cert.imrworldwide.com";
				int counterVid = 0, counterAd = 0;
				// String feedUrl =
				// "https://feed.theplatform.com/f/HNK2IC/nbcd_app_adstitch_v3_prod?byGUID=";
				// String path =
				// "/Users/557743/Desktop/CableBrandPOC/ConsoleOutput/HBAnalyticCalls-Run1-iOS.txt";
				HarReader harReader = new HarReader();
		         Har har = harReader.readFromFile(new File(HarFile_Path), HarReaderMode.LAX);
	                de.sstoehr.harreader.model.HarLog log = har.getLog();

				List<de.sstoehr.harreader.model.HarEntry> entries1 = har.getLog().getEntries();
				for (de.sstoehr.harreader.model.HarEntry entry : entries1) {
					// we need to use below mehtod for the requests
					List<HarPostDataParam> list = entry.getRequest().getPostData().getParams();
					HashMap<String, String> mapVideo = new HashMap<String, String>();
					// System.out.println(list);
					if ((entry.getRequest().getUrl().contains(hbUrl) | entry.getRequest().getUrl().contains(hbUrl1))) {
						for (HarPostDataParam harPostDataParam : list) {

							/*
							 * System.out.println(harPostDataParam.getName());
							 * System.out.println(harPostDataParam.getValue());
							 */
							if ((harPostDataParam.getValue().contains("videoAd"))
									&& (harPostDataParam.getName().contains("pev3"))) {
								flag = true;
								vid = "ad";
								break;

							} else if ((harPostDataParam.getValue().contains("video"))
									&& (harPostDataParam.getName().contains("pev3"))) {
								flag = true;
								vid = "video";
								break;

							} else {
								flag = false;
								vid = "";
							}

						}

					}
					if (flag) {
						for (HarPostDataParam harPostDataParam : list) {
							if (list.size() <= 1)
								continue;
							String Name = harPostDataParam.getName();
							String Value = harPostDataParam.getValue();
							System.out.println(Name + "-----" + Value);
							mapVideo.put(Name, Value);
							System.out.println(mapVideo);
						}
					}

					if ((flag) && (vid.contains("video") && (counterVid == 0))) {
						System.out.println("gg");

						common.validateAnalytics(adBeaconsUrls, mapVideo, "Analytics-VOD");

						flag = false;
						vid = "";
						counterVid++;
					}

					else if ((flag) && (vid.contains("ad") && (counterAd == 0))) {
						System.out.println("gg");
						common.validateAnalytics(adBeaconsUrls, mapVideo, "Analytics-AD");
						flag = false;
						vid = "";
						counterAd++;
					}
					mapVideo.clear();
					if ((counterVid > 0) && (counterAd > 0)) {
						return;
					}

				}

			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
		}
	}

	public void HBValidation_analytics_Live(String HarFile_Path) throws Exception {
		try { // platFormName = TestRunner.platFormName();
			if (platFormName.equalsIgnoreCase("iOS")) {
				ArrayList<String> adBeaconsUrls = new ArrayList<String>();
				Boolean flag = false;
				String vid = "";
				String hbUrl = "http://nbcume.sc.omtrdc.net";
				String hbUrl1 = "http://nbcume.sc.omtrdc.net";
				String comscoreUrl = "http://b.scorecardresearch.com/p2?";
				String analytics = "http://b.scorecardresearch.com/p2?";
				String adBeaconUrl = "http://29773.s.fwmrm.net/";
				String nielsonUrl = "https://secure-dcr-cert.imrworldwide.com";
				int counterVid = 0, counterAd = 0;
				// String feedUrl =
				// "https://feed.theplatform.com/f/HNK2IC/nbcd_app_adstitch_v3_prod?byGUID=";
				// String path =
				// "/Users/557743/Desktop/CableBrandPOC/ConsoleOutput/HBAnalyticCalls-Run1-iOS.txt";
				HarReader harReader = new HarReader();
		         Har har = harReader.readFromFile(new File(HarFile_Path), HarReaderMode.LAX);
	                de.sstoehr.harreader.model.HarLog log = har.getLog();

				List<de.sstoehr.harreader.model.HarEntry> entries1 = har.getLog().getEntries();
				for (de.sstoehr.harreader.model.HarEntry entry : entries1) {
					// we need to use below mehtod for the requests
					List<HarPostDataParam> list = entry.getRequest().getPostData().getParams();
					HashMap<String, String> mapVideo = new HashMap<String, String>();
					// System.out.println(list);
					if ((entry.getRequest().getUrl().contains(hbUrl) | entry.getRequest().getUrl().contains(hbUrl1))) {
						for (HarPostDataParam harPostDataParam : list) {

							/*
							 * System.out.println(harPostDataParam.getName());
							 * System.out.println(harPostDataParam.getValue());
							 */
							if ((harPostDataParam.getValue().contains("videoAd"))
									&& (harPostDataParam.getName().contains("pev3"))) {
								flag = true;
								vid = "ad";
								// System.out.println("ADDD");
								break;
								/*
								 * for (HarPostDataParam harPostDataParam1 :
								 * list) {
								 * mapVideo.put(harPostDataParam.getName(),
								 * harPostDataParam.getName()); }
								 */
							} else if ((harPostDataParam.getValue().contains("video"))
									&& (harPostDataParam.getName().contains("pev3"))) {
								flag = true;
								vid = "video";
								// System.out.println("ADDDVId");
								break;

							} else {
								flag = false;
								vid = "";
							}

						}

					}
					if (flag) {
						for (HarPostDataParam harPostDataParam : list) {
							if (list.size() <= 1)
								continue;
							String Name = harPostDataParam.getName();
							String Value = harPostDataParam.getValue();
							System.out.println(Name + "-----" + Value);
							mapVideo.put(Name, Value);
							System.out.println(mapVideo);
						}
					}

					if ((flag) && (vid.contains("video") && (counterVid == 0))) {
						System.out.println("gg");

						common.validateAnalytics(adBeaconsUrls, mapVideo, "LiveAnalytics-VOD");
						flag = false;
						vid = "";
						counterVid++;
					}

					else if ((flag) && (vid.contains("ad") && (counterAd == 0))) {
						System.out.println("gg");
						common.validateAnalytics(adBeaconsUrls, mapVideo, "LiveAnalytics-AD");
						flag = false;
						vid = "";
						counterAd++;
					}
					mapVideo.clear();
					if ((counterVid > 0) && (counterAd > 0)) {
						return;
					}
				}

			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
		}
	}

}