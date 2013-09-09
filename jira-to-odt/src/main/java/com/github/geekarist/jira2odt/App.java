package com.github.geekarist.jira2odt;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public JiraIssue get(String url) {
		try {
			// Login
			Map<String, String> cookies = Jsoup.connect(JiraConf.URL)
					.data("os_username", JiraConf.LOGIN, "os_password", JiraConf.PASSWORD)
					.method(Method.POST).execute().cookies();
			
			// Get issue attributes
			Document document = Jsoup.connect(url).cookies(cookies).get();
			return new JiraIssue()
				.setTitle(document.select("#summary-val").text())
				.setId(document.select("#key-val").text())
				.setLabels(document.select("#wrap-labels .labels .lozenge").text().replace(" OnBoard", ""));
		} catch (IOException e) {
			ioError(url, e);
			return null;
		}
	}

	private void ioError(String url, IOException e) {
		System.err.println(String.format("IO error while getting [%s]", url));
		e.printStackTrace(System.err);
	}

	public void createOdt(String jiraUrl) {
		try {
			JiraIssue jiraIssue = get(jiraUrl);
			OdfTextDocument odt = OdfTextDocument.newTextDocument();
			odt.addText(jiraIssue.getId());
			odt.newParagraph(jiraIssue.getTitle());
			odt.newParagraph(jiraIssue.getLabels());
			odt.save(jiraIssue.getId() + ".odt");
		} catch (IOException e) {
			ioError(jiraUrl, e);
		} catch (Exception e) {
			unknownError(jiraUrl, e);
		}
	}

	private void unknownError(String jiraUrl, Exception e) {
		System.err.println(String.format("Unknown error while converting [%s] to odt", jiraUrl));
		e.printStackTrace(System.err);
	}
}
