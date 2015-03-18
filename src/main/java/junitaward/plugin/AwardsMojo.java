package junitaward.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@Mojo(name = "awards")
public class AwardsMojo extends AbstractMojo {
	private Pattern r = Pattern.compile("\\*\\s*\\@author\\s*[\\:]?\\s*(.+)");
	private HashMap<String, Integer> result = new HashMap<String, Integer>();
	private int totalTest = 0;
	private int totalTestWithAuthor = 0;
	public void execute() throws MojoExecutionException {
		MavenProject project = (MavenProject) getPluginContext().get("project");
		Path pathToTest = project.getBasedir().toPath();
		pathToTest = pathToTest.resolve(Paths.get("src\\test\\java\\"));
		getLog().info(pathToTest.toString());
		try {
			Files.walkFileTree(pathToTest, new VisitTest());
			generateHtml(result, project.getBasedir().toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void generateHtml(Map<String, Integer> results, Path path) throws IOException, URISyntaxException {
		ArrayList<Map<String,String>> list = new ArrayList();
		for (Entry<String, Integer> entry : result.entrySet()) {
			Map<String, String> ma = new HashMap<String, String>(1);
			ma.put("name", entry.getKey());
			ma.put("testsDone", entry.getValue().toString());
			setTotalTestWithAuthor(getTotalTestWithAuthor() + entry.getValue());
			list.add(ma);
		}
		Collections.sort(list, new Comparator<Map<String,String>>() {
			public int compare(Map<String,String> o1, Map<String,String> o2) {
				return -Integer.valueOf(o1.get("testsDone")).compareTo(Integer.valueOf(o2.get("testsDone")));
			}
		});
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("resultTemplate.vm");
		VelocityContext context = new VelocityContext();
		context.put("authorList", list);
		context.put("totalTest", totalTest);
		context.put("totalTestWithAuthor", totalTestWithAuthor);
		Path target = path.resolve("target"+File.separator +" jUnitAwards" + File.separator);
		Files.createDirectories(target);
		FileWriter writer = new FileWriter(target.resolve("junitAwards.html").toString());
		Files.copy(this.getClass().getClassLoader().getResourceAsStream("junitAward.jpg"), target.resolve("junitAward.jpg"), StandardCopyOption.REPLACE_EXISTING);
		t.merge(context, writer);
		writer.close();
	}

	public void analyzeFile(Path file) throws IOException {
		for (String line : Files.readAllLines(file, Charset.defaultCharset())) {
			Matcher matcher = r.matcher(line);
			if (matcher.find()) {
				String author = matcher.group(1);
				if (result.containsKey(author)) {
					result.put(author, result.get(author) + 1);
				} else {
					result.put(author, 1);
				}
			}else{
				if (line.contains("@Test"))
					setTotalTest(getTotalTest() + 1);
			}
		}
	}

	private class VisitTest implements FileVisitor<Path> {
		private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");

		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Path name = file.getFileName();
			if (name != null && matcher.matches(name)) {
				analyzeFile(file);
			}
			return FileVisitResult.CONTINUE;
		}

		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}

	public HashMap<String, Integer> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Integer> result) {
		this.result = result;
	}

	public int getTotalTest() {
		return totalTest;
	}

	public void setTotalTest(int totalTest) {
		this.totalTest = totalTest;
	}

	public int getTotalTestWithAuthor() {
		return totalTestWithAuthor;
	}

	public void setTotalTestWithAuthor(int totalTestWithAuthor) {
		this.totalTestWithAuthor = totalTestWithAuthor;
	}
}
