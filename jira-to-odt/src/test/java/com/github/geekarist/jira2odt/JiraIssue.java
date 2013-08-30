package com.github.geekarist.jira2odt;

public class JiraIssue {

	private String title;
	private String id;

	public String getTitle() {
		return title;
	}

	public JiraIssue setTitle(String text) {
		this.title = text;
		return this;
	}

	public String getId() {
		return id;
	}

	public JiraIssue setId(String text) {
		this.id = text;
		return this;
	}

}
