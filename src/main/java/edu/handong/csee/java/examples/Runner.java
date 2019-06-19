package edu.handong.csee.java.examples;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Runner {

	int five = 5;
	String inputPath;
	boolean help;
	boolean inOrder;
	boolean reverseOrder;
	boolean absolutePath;
	
	public static void main(String[] args) {
		Runner hi = new Runner();
		hi.run(args);
	}
	
	public void run(String[] args) {
		Options options = createOptions();		
		
		if (parseOptions(options, args)) {
			
			//print help; like usage.
			if (help)
			{
				printHelp(options);
				return;
			}
			
			// inputPath Option
			if (inputPath == null)
			{
				System.out.println("You did not type correct path as the value of the option p");
				System.out.println("Run in project directory");
				inputPath = System.getProperty("user.dir");
			}
			else
			{
				System.out.println("You typed \""+inputPath+"\" as the value of the option p");
				if (!new File(inputPath).isDirectory())
				{
					System.out.println("\nWrong path or Directory.");
				}
			}
			
			File file = new File(inputPath);
			String[] files = file.list();
			
			if( (reverseOrder==true) && (inOrder==true) )
			{
				System.out.println("Both commands cannot be executed at the same time(Confliction; In order and reverse order).");
				System.exit(1);
			}
			
			
			// print files in the directory in reverse order.
			if (reverseOrder)
			{
				List<String> list = Arrays.asList(files);
				Collections.reverse(list);
				files = list.toArray(new String[list.size()]);
			}
			
			// print absolute path of the directory using .getCanonicalPath
			if (absolutePath)
			{
				System.out.printf("\nAbsolute path of the directory is: ");
				try {
					System.out.printf(file.getCanonicalPath() + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// print files in the directory in descending order.
			if (inOrder)
			{
				int count = 0;
				boolean defaultPrint = false;
				System.out.println("File list:");
				for (String fileName : files)
				{
					System.out.print(fileName + "\t");
					count++;
					if (count == five)
					{
						System.out.println("");
						count = 0;
						defaultPrint = true;
					}
				}
				System.out.println("\nPrinted in order...");
				if (reverseOrder)
				{
					System.out.println("Printed in reverse order...");
				}
			}
			else {
				int count = 0;
				boolean defaultPrint = false;
				System.out.println("File list:");
				for (String fileName : files)
				{
					System.out.print(fileName + "\t");
					count++;
					if (count == five)
					{
						System.out.println("");
						count = 0;
						defaultPrint = true;
					}
				}
				if (defaultPrint)
				{
					System.out.println();
				}
			}
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			
			CommandLine cmd = parser.parse(options,  args);

			help = cmd.hasOption("h");
			inputPath = cmd.getOptionValue("p");
			absolutePath = cmd.hasOption("a");
			inOrder = cmd.hasOption("l");
			reverseOrder = cmd.hasOption("r");
			
		} catch (Exception e) {
			printHelp(options);
			
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("h")
				.longOpt("help")
				.desc("Help")
				.build());
		
		options.addOption(Option.builder("p")
				.longOpt("path")
				.desc("Set a path of a directory to display [Only directory]")
				.hasArg()
				.argName("Path name to display")
				.required()
				.build());
		
		options.addOption(Option.builder("a")
				.longOpt("absolutePath")
				.desc("Print out absolute path of present directory")
				.build());
		
		options.addOption(Option.builder("l")
				.longOpt("line")
				.desc("Print out list per line")
				.build());

		options.addOption(Option.builder("r")
				.longOpt("reverseOrder")
				.desc("Display list reverse order")
				.build());

		return options;
	}
	
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Java List Directory Command using Common CLI; CLIExample";
		String footer = "";
		formatter.printHelp("Bonus Homework", header, options, footer, true);
	}
}