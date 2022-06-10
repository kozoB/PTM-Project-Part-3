package test;


import java.io.BufferedReader;    // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.IOException;       // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.InputStream;       // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.InputStreamReader; // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.OutputStream;      // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.PrintWriter;       // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.util.ArrayList;
import java.util.Scanner;         // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET

import test.Commands.AlgoSettingsCommand;
import test.Commands.Command;
import test.Commands.DetectAnomaliesCommand;
import test.Commands.DisplayResultsCommand;
import test.Commands.ExitCommand;
import test.Commands.UploadAnomaliesCommand;
import test.Commands.UploadCommand;

import test.Commands.DefaultIO;
import test.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler
{
	BufferedReader in;
	PrintWriter out;
	public class SocketIO implements DefaultIO
	{
		@Override
		public String readText()
		{
			// TODO Auto-generated method stub
			String text = "";
			try
			{
				text = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return text;
		}

		@Override
		public void write(String text)
		{
			// TODO Auto-generated method stub
			out.println(text);
			
		}

		@Override
		public float readVal()
		{
			// TODO Auto-generated method stub
			String text = "";
			float val = 0;
			try
			{
				text = in.readLine();
				val = Float.valueOf(text);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return val;
		}

		@Override
		public void write(float val)
		{
			// TODO Auto-generated method stub
			out.println(String.valueOf(val));
		}
	}

	@Override
	public void handle(InputStream inFromClient, OutputStream outToClient)
	{
		// TODO Auto-generated method stub
		try
		{
			in = new BufferedReader(new InputStreamReader(inFromClient));
			out = new PrintWriter(outToClient, true);
			
			SocketIO sio = new SocketIO();
			ArrayList<Command> commands;
			Commands c;
			
			c=new Commands(sio); 
			commands=new ArrayList<>();
			
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
			
			commands.add(c.new UploadCommand());
			commands.add(c.new AlgoSettingsCommand());
			commands.add(c.new DetectAnomaliesCommand());
			commands.add(c.new DisplayResultsCommand());
			commands.add(c.new UploadAnomaliesCommand());
			commands.add(c.new ExitCommand());
			
			ArrayList<Map> commadIndexes = new ArrayList<Map>(); // ArrayList containing each command and it's index
			
			int i = 0;
			for (Command command : commands)
			{
				commadIndexes.add(i,new Map(i+1, command)); // Initialize command indexes array
				i++;
			}
			
			String line;
			//int input = 0;
			
			while(!(line=in.readLine()).equals("6"))
			{
				out.println("Welcome to the Anomaly Detection Server."); // Print introduction to CLI
				out.println("Please choose an option:"); // Print introduction to CLI
				
				i = 0;
				for (Command command : commands)
				{
					out.println(i+1 + ". " + command.description);  // Print each command in the command array list to CLI
					i++;
				}
				
				int input = Integer.parseInt(line);
				commadIndexes.get(input-1).cmd.execute(); // Execute the command of the given index (the index is the given number - 1)
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close()
	{
		// TODO Auto-generated method stub
		try
		{
			out.println("bye");
			in.close();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
