package com.github.geekarist.jira2odt;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.w3c.dom.NodeList;

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
		Assert.assertEquals("", issue.getLabels());
	}

	@Test
	public void shouldLoginAndGetIssueAttributes() {
		// GIVEN
		String url = "http://jira.sfrdev.fr/browse/SRR-113";
		// WHEN
		JiraIssue issue = app.get(url);
		// THEN
		Assert.assertEquals("[SRR]CHANGE_OFFRE] Eligiblité commerciale", issue.getTitle());
		Assert.assertEquals("SRR-113", issue.getId());
		Assert.assertEquals("MigrationChangeSRR", issue.getLabels());
	}

	@Test
	public void shouldCreateOdtFromJira() throws Exception {
		// GIVEN
		String jiraUrl = "http://jira.sfrdev.fr/browse/SRR-113";
		// WHEN
		app.createOdt(jiraUrl);
		// THEN
		Assert.assertTrue(new File("SRR-113.odt").exists());
		OdfContentDom contentDom = OdfTextDocument.loadDocument("SRR-113.odt").getContentDom();
		NodeList elementsByTagName = contentDom.getElementsByTagName("text:p");
		Assert.assertEquals(3, elementsByTagName.getLength());
		Assert.assertEquals("SRR-113", elementsByTagName.item(0).getTextContent());
		Assert.assertEquals("[SRR]CHANGE_OFFRE] Eligiblité commerciale", elementsByTagName.item(1).getTextContent());
		Assert.assertEquals("MigrationChangeSRR", elementsByTagName.item(2).getTextContent());
	}

}
