# JUnit-Awards
Maven plugin for counting Junits done by team members.

This plugin scans the entire src/test/java (or other) fould of your project searching for the @author tag of javadoc, getting the author name and increasing the count for it.

The @author tag must be enclosed in a javadoc block like:

	/**
	 * 
	 * @author ggefaell
	 * @date 30/03/2015
	 */
	@Test
	public void test4(){
	}

Or

	/**
	 * 
	 * @author Guillermo Gefaell
	 * @date 30/03/2015
	 */
	@Test
	public void test4(){
	}

It generates a simple html page with the results.

To use it follow this steps:

    1. Add the plugin to your project's pom. 
    <plugin>
        <groupId>com.github.konum</groupId>
        <artifactId>junitaward-maven-plugin</artifactId>
        <version>1.2</version>
    </plugin> 
    2. Run mvn junitward:awards on your project.
    3. The results are generated in the folder /target/junitaward/junitAward.html

Opcional parameters
Ther are three optional parameters:

    <configuration>
        <datePatter>dd/MM/yyyy</datePatter>
        <testsFolder>src/test/java</testsFolder>
        <testsSince>2015-03-14 0:00:00.0 AM</testsSince>
    </configuration>

testsFolder = Define the root folder for searching Junit. Default is src/test/java

testsSince = Displays results for test made since this date. The test must have @date in its javadoc. Default date pattern is dd-MM-yyyy. Without this parameter all test are given in thre result.

datePatter = Allow to change date pattern. Default is dd-MM-yyyy. Only useful if testsSince is set.

Release under GNU General Public License, version 3 (GPL-3.0)
