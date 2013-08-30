package com.github.geekarist.jira2odt;

import org.junit.Assert;
import org.junit.Test;


public class AppTest {

	private App app = new App();

	@Test
	public void shouldGetIssueAttributes() {
		// GIVEN
		String url = "https://issues.apache.org/jira/browse/ZOOKEEPER-1748";
		// WHEN
		JiraIssue issue = app.get(url);
		// THEN
		Assert.assertEquals("TCP keepalive for leader election connections", issue.getTitle());
		Assert.assertEquals("ZOOKEEPER-1748", issue.getId());
	}
}
