package example;

import java.nio.file.Path;
import java.nio.file.Paths;

import junitaward.plugin.AwardsMojo;

import org.junit.Assert;
import org.junit.Test;


public class ExampleTest  {

	/**
	 * 
	 * @throws Exception
	 * @author ggefaell
	 * 18/3/2015
	 */
	protected void setUp() throws Exception {
		// required
	}

	/**
	 * 
	 * @throws Exception
	 * @author dcabrera
	 * 18/3/2015
	 */
	protected void tearDown() throws Exception {
	}

	/**
	 * @author ggefaell
	 * @throws Exception 
	 * @date 17/3/2015
	 */
	@Test
	public void test1() throws Exception {
		Path root = Paths.get("C:\\j-everis3.1\\ws_bip\\junitaward\\src\\test\\java\\example\\ExampleTest.java");
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		Assert.assertEquals(Integer.valueOf(2),mojo.getResult().get("ggefaell"));
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("dcabrera"));
	}
	
	@Test
	public void testGenerateHtml() throws Exception {
		Path root = Paths.get("C:\\j-everis3.1\\ws_bip\\junitaward\\src\\test\\java\\example\\EmptyTests.java");
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		mojo.generateHtml(mojo.getResult(),Paths.get("C:\\j-everis3.1\\ws_bip\\junitaward\\"));
	}
}
