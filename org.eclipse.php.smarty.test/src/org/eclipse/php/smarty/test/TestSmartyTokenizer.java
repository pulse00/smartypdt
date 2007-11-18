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

import org.eclipse.php.internal.core.documentModel.parser.PHPTokenizer;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.test.infra.project.IPhpTestProject;
import org.eclipse.php.test.infra.project.ProjectDescriptor;
import org.eclipse.php.test.infra.testcase.Golden;
import org.eclipse.php.test.infra.testcase.PhpProjectTestCase;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSmartyTokenizer extends PhpProjectTestCase {

	public static ProjectDescriptor getDescriptor() {
		return ProjectDescriptor.SMOKE_TEST_PHP5_PROJECT;
	}

	@BeforeClass
	public static void loadProject() throws Exception {
		myProject = loadProject(getDescriptor());
	}

	@AfterClass
	public static void unloadProject() throws Exception {
		unloadProject(getDescriptor());
		myProject = null;
	}

	@Test
	public void testTokenizeSmartyComment() throws IOException, Exception {
		testhasSmartyToken("Comment.txt", "{* djhfdksj *}", getProject());
	}

	@Test
	public void testTokenizeSmarty1() throws IOException, Exception {
		testhasSmartyToken("variable.txt", "{$djhfdksj}", getProject());
	}

	@Test
	public void testTokenizeSmarty2() throws IOException, Exception {
		testhasSmartyToken("in_html.txt", "<html>{$djhfdksj}</html>", getProject());
	}

	@Test
	public void testTokenizeSmarty3() throws IOException, Exception {
		testhasSmartyToken("nested_html.txt", "<div> <div>{$djhfdksj} </div> </div>", getProject());
	}

	@Test
	public void testTokenizeSmarty4() throws IOException, Exception {
		testhasSmartyToken("empty_clause.txt", "{}", getProject());
	}

	@Test
	public void testTokenizeSmarty5() throws IOException, Exception {
		testhasSmartyToken("string.txt", "{'safdsa' \"dsafdsafds\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty6() throws IOException, Exception {
		testhasSmartyToken("function_call.txt", "{function helo='sddsa'}", getProject());
	}

	@Test
	public void testTokenizeSmarty7() throws IOException, Exception {
		testhasSmartyToken("double1.txt", "{\" \"}", getProject());
	}

	@Test
	public void testTokenizeSmarty8() throws IOException, Exception {
		testhasSmartyToken("double2.txt", "{func var=\"test $foo test\"}       ", getProject());
	}

	@Test
	public void testTokenizeSmarty9() throws IOException, Exception {
		testhasSmartyToken("double3.txt", "{func var=\"test $foo_bar test\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty10() throws IOException, Exception {
		testhasSmartyToken("double4.txt", "{func var=\"test $foo[bar] test\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty11() throws IOException, Exception {
		testhasSmartyToken("double5.txt", "{func var=\"test $foo[543] test\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty12() throws IOException, Exception {
		testhasSmartyToken("double7.txt", "{func var=\"test $foo.bar test\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty13() throws IOException, Exception {
		testhasSmartyToken("double8.txt", "{func var=\"test `$foo.bar` test\"}", getProject());
	}

	@Test
	public void testTokenizeSmarty14() throws IOException, Exception {
		testhasSmartyToken("double9.txt", "{func var=\"test `$foo->bar` test\"}", getProject());
	}

	@Test
	public void testTokenizeSmartyInAttribute() throws IOException, Exception {
		testhasSmartyToken("in_attr.txt", "<a href='{$djhfdksj}'>", getProject());
	}

	protected void testhasSmartyToken(String outfileName, String content, IPhpTestProject phpProject) throws Exception {

		final InputStream fileContent = new ByteArrayInputStream(content.getBytes());

		final PhpSourceParser phpSourceParser = new PhpSourceParser();

		PhpSourceParser.editFile.set(phpProject.getProject());
		final PHPTokenizer tokenizer = (PHPTokenizer) phpSourceParser.getTokenizer();
		tokenizer.setProject(phpProject.getProject());

		phpSourceParser.reset(new InputStreamReader(fileContent));

		// create actual result file and write it 
		final File tempFile = File.createTempFile(outfileName, "");
		tempFile.createNewFile();
		writeActual(tokenizer, new FileOutputStream(tempFile), content);

		// comparee with golden
		final Golden golden = getGolden(outfileName);
		assertGolden(golden, tempFile);

		tempFile.deleteOnExit();
	}

	protected void writeActual(PHPTokenizer tokenizer, OutputStream out, String content) throws IOException {

		PrintStream pout = new PrintStream(out);
		ITextRegion region = null;

		pout.println("content for this run: " + content);

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

}
