# JUnit-Awards
Maven plugin for counting Junits done by team members.

This plug in scans the entire src/test/java fould of your project searching for the @author tag of javadoc, getting the author name and increasing the count for it. Kind of simple for now.

The @author tag must be enclosed in a javadoc block like:

/**
* @author ggefaell
/*

/**
* @author Guillermo Gefaell
/* 

It generates a simple html page with the results.

For now i'm still seeing how to get the plugin to be avaliable at a repository, but you can follow this steps for runing it on your project.

    1. Fort the repo.
    2. Run mvn install on it  so you get the plugin on your local repo.
    3. Add the plugin to your project's pom. 
    <plugin>
      <groupId>junitaward.plugin</groupId>
      <artifactId>junitaward-maven-plugin</artifactId>
      <version>1.0-SNAPSHOT</version>
    </plugin>

    4. Run mvn junitward:awards on your project.
    
    The results are generated in the folder /target/JUnitAwards/junitAwards.html


The next improvements are:

    Get it to a repository so no forking-installing.
    Add a "Since Date" parameter so you can know how many tests have been written in a time span.
    Improving @author scan so it counts only in functions noted with @Test.
