package example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

import com.github.konum.junitaward.AwardsMojo;


public class AwardsTest  {

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
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		Assert.assertEquals(Integer.valueOf(2),mojo.getResult().get("ggefaell"));
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("dcabrera"));
	}
	
	@Test
	public void testGenerateHtml() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		mojo.generateHtml(mojo.getResult(),(new File(this.getClass().getResource("/").getPath())).toPath());
		Assert.assertTrue(Files.exists((new File(this.getClass().getResource("/target/junitaward/junitAwards.html").getPath())).toPath()));
	}
}
