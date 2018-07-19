package com.org.adobevalidations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderMode;
import de.sstoehr.harreader.model.Har;

/**
 * Class repository for Home screen properties
 * 
 * @author 557743
 *
 */
public class ComscoreFeature extends HBValidationsEpisode {

	int j = 2;
	int i = 0;
	public static List<String> assetDetails;
	CommonFunctions2 common = new CommonFunctions2();
	String hbUrl = "http://nbcume.hb.omtrdc.net/";
	String comscoreUrl = "http://b.scorecardresearch.com/p2?";
		String adBeaconUrl = "http://29773.s.fwmrm.net";

	ArrayList<String> HBUrls = new ArrayList<String>();
	ArrayList<String> comscoreUrls = new ArrayList<String>();
	ArrayList<String> adBeaconsUrls = new ArrayList<String>();
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
	HashMap<String, String> excelValueMap = new HashMap<String, String>();
	HashMap<String, String> actualValueMap;
	ReadDataSheet rd = new ReadDataSheet();

	// Comscore Valiadtion
	public void comscoreValidation(String sheetName, String Event_Type, String HarFile_Path, String feedUrl)
			throws Exception {
		// platFormName = TestRunner.platFormName();
		try {

			//
			HarReader harReader = new HarReader();
	         Har har = harReader.readFromFile(new File(HarFile_Path), HarReaderMode.LAX);
               de.sstoehr.harreader.model.HarLog log = har.getLog();
			
			List<de.sstoehr.harreader.model.HarEntry> entries1 = har.getLog().getEntries();
			for (de.sstoehr.harreader.model.HarEntry entry : entries1) {
				if (entry.getRequest().getUrl().contains(comscoreUrl)) {
					System.out.println("im n Comscore call validation");
					comscoreUrls.add(entry.getRequest().getUrl());
					comscoreUrlsTimeStamp.add(entry.getStartedDateTime().toString());

				}

				//common.seriesOfEvents(comscoreUrls, comscoreUrl, comscoreUrlsTimeStamp, "ns_ap_ev");

				test.log(LogStatus.INFO,
						"<u><b><font color=\"purple\"><-------Start -" + sheetName + " -------></font></b></u>");
				common.validateComscoreLive(comscoreUrls, comscoreUrl, sheetName, "ns_ap_ev", Event_Type,
						comscoreUrlsTimeStamp, feedUrl);
				test.log(LogStatus.INFO,
						"<u><b><font color=\"purple\"><-------End --" + sheetName + "  -------></font></b></u>");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e);
		}
	}
}
