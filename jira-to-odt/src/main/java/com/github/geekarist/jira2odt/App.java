package com.github.geekarist.jira2odt;

import java.io.IOException;

import org.jsoup.Jsoup;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public JiraIssue get(String url) {
		try {
			return new JiraIssue()
				.setTitle(Jsoup.connect(url).get().select("#summary-val").text())
				.setId(Jsoup.connect(url).get().select("#key-val").text());
		} catch (IOException e) {
			System.err.println(String.format("IO error while getting [%s]", url));
			e.printStackTrace(System.err);
			return null;
		}
	}
}
