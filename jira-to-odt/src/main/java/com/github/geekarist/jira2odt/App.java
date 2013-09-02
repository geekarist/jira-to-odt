package com.github.geekarist.jira2odt;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public JiraIssue get(String url) {
		try {
			// Login
			Map<String, String> cookies = Jsoup.connect("http://jira.sfrdev.fr/login.jsp")
					.data("os_username", "xx", "os_password", "xx")
					.method(Method.POST).execute().cookies();
			
			// Get issue attributes
			Document document = Jsoup.connect(url).cookies(cookies).get();
			System.out.println(document.html());
			return new JiraIssue()
				.setTitle(document.select("#summary-val").text())
				.setId(document.select("#key-val").text())
				.setLabels(document.select("#wrap-labels .labels .lozenge").text().replace(" OnBoard", ""));
		} catch (IOException e) {
			System.err.println(String.format("IO error while getting [%s]", url));
			e.printStackTrace(System.err);
			return null;
		}
	}
}
