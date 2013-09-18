package com.github.geekarist.jira2odt;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;

public class AppTest {
	
	// TODO Use https://github.com/cucumber/cucumber-jvm

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
	public void shouldCreateOdtFromJiraUsingTemplate() throws Exception {
		// GIVEN
		String jiraUrl = "http://jira.sfrdev.fr/browse/SRR-113";
		// WHEN
		app.createOdt(jiraUrl);
		// THEN
		Assert.assertTrue(new File("target/SRR-113.odt").exists());
		OdfContentDom contentDom = OdfTextDocument.loadDocument("target/SRR-113.odt").getContentDom();
		Assert.assertEquals("SRR-113", fieldValue(contentDom, "id"));
		Assert.assertEquals("3", fieldValue(contentDom, "size"));
		Assert.assertEquals("[SRR]CHANGE_OFFRE] Eligiblité commerciale", fieldValue(contentDom, "description"));
		Assert.assertEquals("MigrationChangeSRR", fieldValue(contentDom, "project"));
	}

	private String fieldValue(OdfContentDom contentDom, String name) throws XPathExpressionException {
		XPath xPath = contentDom.getXPath();
		String expression = String.format("//text:user-field-decl[@text:name='%s']", name);
		TextPElement element = (TextPElement) xPath.evaluate(expression, contentDom, XPathConstants.NODE);
		return element.getAttribute("office:string-value");
	}

}
