package com.github.geekarist.jira2odt;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public JiraIssue get(String url) {
		try {
			Document document = Jsoup.connect(url).get();
			System.out.println(document.html());
			return new JiraIssue()
				.setTitle(document.select("#summary-val").text())
				.setId(document.select("#key-val").text())
				.setLabels(document.select("#wrap-labels .labels").text());
		} catch (IOException e) {
			System.err.println(String.format("IO error while getting [%s]", url));
			e.printStackTrace(System.err);
			return null;
		}
	}
}
