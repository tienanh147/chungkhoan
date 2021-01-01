package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.*;
import java.util.Iterator;

public class CrawlStatisticsIndexPoint {
	private String link = "http://ra.vcsc.com.vn/Market/PriceHistory/-(chooseIndex)?page=1&filter=0&from=(startDate)&to=(endDate) ";

	public CrawlStatisticsIndexPoint() {
		
	}

	public void crawlStatisticsIndexPoint(String date, String nameIndex) throws IOException, ArrayIndexOutOfBoundsException {
		String year = date.split("/")[2];
		String month = date.split("/")[1];
		String day = date.split("/")[0];
		String endDate = year + "-" + month + "-" + day;
		
		String startDate = "2009-06-02";
	
		
		link = link.replace("(startDate)", startDate);
		link = link.replace("(endDate)", endDate);
		if(nameIndex == "VNINDEX") link = link.replace("(chooseIndex)", "1");
		else if(nameIndex == "HNX-INDEX") link = link.replace("(chooseIndex)", "2");
		else if(nameIndex == "UPCOM-INDEX") link = link.replace("(chooseIndex)", "3");
		
		
		Document doc = Jsoup.connect(link).get();
		PrintWriter pw7 = new PrintWriter("E:\\java\\chungkhoan\\crawlStatistics.txt");
		pw7.println(nameIndex);
		Element table = doc.select("table.stock-lsg-table").first();

		Iterator<Element> it = table.select("tr").iterator();
		it.next();
		for (int a = 0; a<20 ; a++ ) {
			String s = it.next().text();
			
			s = s.replace("  ", "");
			s = s.replace(".", "");
			s = s.replace(",", ".");
			s = s.replace("(", "");
			s = s.replace(")", "");
			s = s.replace("%", "");
			
			String [] split = s.split(" ",12);
			s = split[0] + " " + split[6] + " " + split[1] + " " + split[2] + " " + split[7]
				+ " " + split[8] + " " + split[9] + " " + split[10] + " " + split[3] + " " + split[4] + " " + split[5];
			
			pw7.println(s);
			
			}
		pw7.close();
	}

	public static void main(String[] args) throws IOException {
		CrawlStatisticsIndexPoint cr = new CrawlStatisticsIndexPoint();
		cr.crawlStatisticsIndexPoint("29/02/2020","HNX-INDEX");
	}
}
