package kr.co.steamsale.backend.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapHelper {
	private static Connection connection;

	// Header Generator
	@SuppressWarnings("serial")
	private static List<String> browsers = new ArrayList<String>() {
		{
			add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
			add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
			add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		}
	};

	// Header Generator
/*	@SuppressWarnings("serial")
	private static List<String> mobileBrowsers = new ArrayList<String>() {
		{
			add("Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
			add("Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5");
		}
	};*/

	public static Document scrap(String url) throws IOException {
		return scrap(url, true);
	}

	public static Document scrap(String url, boolean followRedirects) throws IOException {
		return scrap(url, followRedirects, "UTF-8");
	}

	/**
	 * bodydsize = -1
	 * 
	 * @param url
	 * @param followRedirects
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static Document scrap(String url, boolean followRedirects, String encoding) throws IOException {
		return scrap(url, followRedirects, encoding, -1);
	}

	/**
	 * 페이지를 스크랩한다.
	 * 
	 * @param url
	 *            : 스크랩 대상 URL
	 * @param followRedirects
	 *            : redirect 속성 설정
	 * @param encoding
	 *            : Document 인코딩
	 * @return Scrap 결과 Document
	 * @throws IOException
	 */
	public static Document scrap(String url, boolean followRedirects, String encoding, int maxBodySize) throws IOException {
		connection = Jsoup.connect(url)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3")
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
				.cookie("birthtime", "504950401")
				.cookie("lastagecheckage", "1-January-1986")
				.userAgent(browsers.get((int) (Math.random() * browsers.size())))
				.ignoreContentType(true).timeout(50000)
				.followRedirects(followRedirects)
				.ignoreHttpErrors(true);

		if (maxBodySize >= 0)
			connection.maxBodySize(maxBodySize);

		Response response = connection.execute();

		return Jsoup.parse(new String(response.bodyAsBytes(), encoding));
	}
}
