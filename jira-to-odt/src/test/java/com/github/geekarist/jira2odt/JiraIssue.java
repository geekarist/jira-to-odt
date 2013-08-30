package com.github.geekarist.jira2odt;

public class JiraIssue {

	private String title;
	private String id;
	private String labels;

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

	public String getLabels() {
		return labels;
	}

	public JiraIssue setLabels(String text) {
		this.labels = text;
		return this;
	}

}
