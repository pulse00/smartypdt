/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.smarty.test;

import java.io.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.internal.core.documentModel.parser.PHPTokenizer;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.test.infra.project.IPhpTestProject;
import org.eclipse.php.test.infra.project.ProjectDescriptor;
import org.eclipse.php.test.infra.testcase.Golden;
import org.eclipse.php.test.infra.testcase.HeadlessTest;
import org.eclipse.php.test.infra.testcase.PhpProjectTestCase;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Description: Test for class {@link PHPTokenizer}   
 * @author Eden K.,2006
 */
@HeadlessTest
public class TestPhpTokenizer extends PhpProjectTestCase {

	@BeforeClass
	public static void loadProject() throws Exception {
		myProject = loadProject(getDescriptor());
	}

	@AfterClass
	public static void unloadProject() throws Exception {
		unloadProject(getDescriptor());
		myProject = null;
	}

	protected void doTestTokenizer(String fileName, IPhpTestProject phpProject) throws Exception {

		IFile file = phpProject.getFile(fileName);
		final InputStream fileContent = file.getContents();

		final PhpSourceParser phpSourceParser = new PhpSourceParser();

		PhpSourceParser.editFile.set(phpProject.getProject());
		final PHPTokenizer tokenizer = (PHPTokenizer) phpSourceParser.getTokenizer();
		tokenizer.setProject(phpProject.getProject());

		phpSourceParser.reset(new InputStreamReader(fileContent));

		// create actual result file and write it 
		final File tempFile = File.createTempFile(fileName, "");
		tempFile.createNewFile();
		writeActual(tokenizer, new FileOutputStream(tempFile));

		// comparee with golden
		final Golden golden = getGolden(fileName);
		assertGolden(golden, tempFile);

		tempFile.deleteOnExit();

	}

	protected void doTestTokenizerTasks(String fileName, String testString, IPhpTestProject phpProject) throws Exception {

		final PhpSourceParser phpSourceParser = new PhpSourceParser();

		PhpSourceParser.editFile.set(phpProject.getProject());
		final PHPTokenizer tokenizer = (PHPTokenizer) phpSourceParser.getTokenizer();
		tokenizer.setProject(phpProject.getProject());

		phpSourceParser.reset(testString);

		// create actual result file and write it 
		final File tempFile = File.createTempFile(fileName, "");
		tempFile.createNewFile();
		writeActual(tokenizer, new FileOutputStream(tempFile));

		// comparee with golden
		final Golden golden = getGolden(fileName);
		assertGolden(golden, tempFile);

		tempFile.deleteOnExit();

	}

	protected void writeActual(PHPTokenizer tokenizer, OutputStream out) throws IOException {

		PrintStream pout = new PrintStream(out);
		ITextRegion region = null;

		region = tokenizer.getNextToken();
		try {
			while (region != null) {
				if (region != null) {
					pout.println(region.getType());
				}
				region = tokenizer.getNextToken();
			}
		} catch (StackOverflowError e) {
			Logger.logException(getClass().getName() + ": input could not be tokenized correctly at position " + tokenizer.getOffset(), e);//$NON-NLS-1$
			throw e;
		} catch (Exception e) {
			Logger.logException("Exception not handled retrieving regions: " + e.getLocalizedMessage(), e);//$NON-NLS-1$
		}
		pout.close();
	}

	@Test
	public void testTokenizePhpGenerictokenizer_php5_sample() throws IOException, Exception {
		doTestTokenizer("tokenizer_php5_sample.php", getProject());
	}

	@Test
	public void testTokenizePhpGenerictokenizer_php5_strings() throws IOException, Exception {
		doTestTokenizer("tokenizer_php5_strings.php", getProject());
	}

	@Test
	public void testTokenizePhpGenerictokenizer_php5_heredocs() throws IOException, Exception {
		doTestTokenizer("tokenizer_php5_heredocs.php", getProject());
	}

	@Test
	public void testEmbeddedCode() throws Exception {
		doTestTokenizer("HTMLembedPHP.php", getProject());
	}

	@Test
	public void testTokenizePhp() throws IOException, Exception {
		doTestTokenizer("tokenizer_phpPure.php", getProject());
		doTestTokenizer("tokenizer_php5_comments.php", getProject());
	}

	// this test is not getting the input from a file, instead it is sending specific strings
	@Test
	public void testTokenizeTaskstokenizer_phpTasks1() throws IOException, Exception {
		doTestTokenizerTasks("tokenizer_phpTasks1.php", "<?\n //This is a comment TODO task6/n?>", getProject());
	}

	@Test
	public void testTokenizeTaskstokenizer_phpTasks2() throws IOException, Exception {

		doTestTokenizerTasks("tokenizer_phpTasks2.php", "<?\n/**\n* This is a Doc Block\n* TODO task1\n*/?>", getProject());
	}

	@Test
	public void testTokenizeTaskstokenizer_phpTasks3() throws IOException, Exception {
		doTestTokenizerTasks("tokenizer_phpTasks3.php", "<?\n/**\n* This is a\n* much\n* bigger\n* doc\n* block\n* TODO - task2\n*/\n?>", getProject());
	}

	@Test
	public void testTokenizeTaskstokenizer_phpTasks4() throws IOException, Exception {
		doTestTokenizerTasks("tokenizer_phpTasks4.php", "<?\n/*\n\n	this one as well\n\n	TODO - task3\n	*/\n?>", getProject());
	}

	@Test
	public void testTokenizeTaskstokenizer_phpTasks5() throws IOException, Exception {
		doTestTokenizerTasks("tokenizer_phpTasks5.php", "<?\n/*\nTODO - task4\n*/\n?>", getProject());
	}

	@Test
	public void testTokenizeTaskstokenizer_phpTasks6() throws IOException, Exception {
		doTestTokenizerTasks("tokenizer_phpTasks6.php", "<?\n/**\n* This is a Doc Block\n* TODO - task5\n*/\n?>", getProject());
	}

	@Test
	public void testTokenizePhpWithHtmltokenizer_phpInHtml() throws IOException, Exception {
		doTestTokenizer("tokenizer_phpInHtml.php", getProject());
	}

	@Test
	public void testTokenizePhpWithHtmltokenizer_phpCodeAsHtmlAttributeKey() throws IOException, Exception {
		doTestTokenizer("tokenizer_phpCodeAsHtmlAttributeKey.php", getProject());
	}

	@Test
	public void testTokenizePhpWithHtmltokenizer_phpCodeAsHtmlAttributeValue() throws IOException, Exception {
		doTestTokenizer("tokenizer_phpCodeAsHtmlAttributeValue.php", getProject());
	}

	public static ProjectDescriptor getDescriptor() {
		return ProjectDescriptor.SMOKE_TEST_PHP5_PROJECT;
	}
}
