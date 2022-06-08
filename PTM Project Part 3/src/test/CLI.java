package test;

import java.util.ArrayList;

import test.Commands.Command;
import test.Commands.DefaultIO;

public class CLI 
{
	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio)
	{
		this.dio=dio;
		c=new Commands(dio); 
		commands=new ArrayList<>();
		// example: commands.add(c.new ExampleCommand());
		// implement
		commands.add(c.new UploadCommand());
		commands.add(c.new AlgoSettingsCommand());
		commands.add(c.new DetectAnomaliesCommand());
		commands.add(c.new DisplayResultsCommand());
		commands.add(c.new UploadAnomaliesCommand());
		commands.add(c.new ExitCommand());
	}
	
	public void start()
	{
		// implement
		class Map
		{
			public int index;
			public Command cmd;
			
			public Map (int index, Command cmd)
			{
				this.index = index;
				this.cmd = cmd;
			}
		}
		
		ArrayList<Map> commadIndexes = new ArrayList<Map>(); // ArrayList containing each command and it's index
		
		int i = 0;
		for (Command command : commands)
		{
			commadIndexes.add(i,new Map(i+1, command)); // Initialize command indexes array
			i++;
		}
		
		int input = 0;
		
		while(input != 6)
		{
			dio.write("Welcome to the Anomaly Detection Server.\n"); // Print introduction to CLI
			dio.write("Please choose an option:\n"); // Print introduction to CLI
			
			i = 0;
			for (Command command : commands)
			{
				dio.write(i+1 + ". " + command.description + "\n");  // Print each command in the command array list to CLI
				i++;
			}
			input = Integer.parseInt(dio.readText()); // Get input from DefaultIO and convert it to int
			commadIndexes.get(input-1).cmd.execute(); // Execute the command of the given index (the index is the given number - 1)
			if (input == 6)
			{
				break; // DELETE
			}
		}
	}
}
