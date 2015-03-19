package example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.github.konum.junitaward.AwardsMojo;


public class AwardsTest  {

 
	/**
	 * @author ggefaell
	 * @throws Exception 
	 * @date 12/03/2015
	 */
	@Test
	public void testCountWithOutDate() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		Assert.assertEquals(Integer.valueOf(2),mojo.getResult().get("ggefaell"));
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("dcabrera"));
	}
	
	/**
	 * @author ggefaell
	 * @throws Exception 
	 * @date 17/03/2015
	 */
	@Test
	public void noTestAuthortDate() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		Assert.assertNull(mojo.getResult().get("noTestAuthor"));
	}
	
	/**
	 * @author ggefaell2
	 * @throws Exception 
	 * @date 12/03/2015
	 */
	@Test
	public void testCountWithDate() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.setDatePatter("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,25);
		cal.set(Calendar.MONTH,2);
		cal.set(Calendar.YEAR,2015);
		mojo.setTestsSince(cal.getTime());
		mojo.analyzeFile(root);
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("ggefaell"));
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("dcabrera"));
		Assert.assertEquals(Integer.valueOf(1),mojo.getResult().get("sergio"));
		Assert.assertNull(mojo.getResult().get("jgomez"));
	}
	
	/**
	 * @author ggefaell2
	 * @throws Exception 
	 * @date 17/03/2015
	 */
	@Test
	public void noTestAuthorWithDate() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.setDatePatter("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,25);
		cal.set(Calendar.MONTH,2);
		cal.set(Calendar.YEAR,2015);
		mojo.setTestsSince(cal.getTime());
		mojo.analyzeFile(root);
		Assert.assertNull(mojo.getResult().get("noTestAuthor"));
	}
	
	/**
	 * @author ggefaell2
	 * @throws Exception 
	 * @date 17/03/2015
	 */
	@Test
	public void testGenerateHtml() throws Exception {
		Path root = (new File(this.getClass().getResource("/EmptyTests.java").getPath())).toPath();
		AwardsMojo mojo = new AwardsMojo();
		mojo.analyzeFile(root);
		mojo.generateHtml(mojo.getResult(),(new File(this.getClass().getResource("/").getPath())).toPath());
		Assert.assertTrue(Files.exists((new File(this.getClass().getResource("/target/junitaward/junitAwards.html").getPath())).toPath()));
	}
}
