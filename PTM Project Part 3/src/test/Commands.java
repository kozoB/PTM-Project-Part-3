package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Commands
{
	// Default IO interface
	public interface DefaultIO
	{
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);

		// you may add default methods here
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio)
	{
		this.dio=dio;
	}
	
	// you may add other helper classes here
	public class StandardIO implements DefaultIO // StandardIO implementation of DefalutIO (input and output with user input and console)
	{	
		@Override
		public String readText()
		{
			// TODO Auto-generated method stub
		    //Scanner myObj = new Scanner(System.in); // Create a Scanner object
		    //String userInput = myObj.nextLine(); // Scan input from the user
			return null;
		}

		@Override
		public void write(String text)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public float readVal()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void write(float val)
		{
			// TODO Auto-generated method stub
		}
	}
	
	public class SocketIO implements DefaultIO // SocketIO implementation of DefalutIO (input and output with sockets)
	{

		@Override
		public String readText() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void write(String text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public float readVal() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void write(float val) {
			// TODO Auto-generated method stub
			
		}
	}
	
	// the shared state of all commands
	private class SharedState
	{
		// implement here whatever you need
		
	}
	
	private  SharedState sharedState=new SharedState();

	
	// Command abstract class
	public abstract class Command
	{
		protected String description;
		
		public Command(String description)
		{
			this.description=description;
		}
		
		public abstract void execute();
	}
	
	// Command class for example:
	public class ExampleCommand extends Command{

		public ExampleCommand()
		{
			super("this is an example of command");
		}

		@Override
		public void execute()
		{
			// Example
		}		
	}
	
	// implement here all other commands
	public class UploadCommand extends Command
	{
		public UploadCommand() 
		{
			super("This is an upload command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "upload a time series csv file";
			return;
		}

		@Override
		public void execute()
		{
			// UPLOAD CSV FILE
			dio.write("Please upload your local train CSV file.\n");
			/*
				Send the csv file path to the server (train) 										TODO
				dio.write(dio.readText()); // Send csv values to the server							TODO
				need to implement the write/read on the StandardIO (console) and SocketIO (socket) to allow the above
			*/
			dio.write("Upload complete.\n");
			dio.write("Please upload your local test CSV file.\n");
			//((StandardIO) dio).readText(); // StandardIO read (input from user)									TODO
			/*
				Send the csv file path to the server (train) 										TODO
			*/
			dio.write("Upload complete.\n");
			// TEST:
			for (int i = 0; i < 404; i++) // TODO
			{
				/*System.out.println(*/dio.readText()/* + "\n")*/; // Write the file's lines   TODO DELETE PRINT
			}
		}		
	}
	
	public class AlgoSettingsCommand extends Command
	{
		public AlgoSettingsCommand() 
		{
			super("This is an algorithm settings command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "algorithm settings";
			return;
		}

		@Override
		public void execute()
		{
			// ALGORITHM SETTINGS (change threshold value)
			dio.write("The current correlation threshold is 0.9\n");  // TODO change 0.9 to variable from anomaly detector
			dio.write("Type a new threshold\n");
			
			/*Add writing new value of the threshold*/ //TODO
			while (true) // As long as a valid threshold value is not given (0 <= threshold <= 1)
			{
				String threshold = dio.readText();
				//System.out.println(threshold);  									  // TODO DELETE PRINT (new threshold value)
				if (Float.parseFloat(threshold) > 1 || Float.parseFloat(threshold) < 0) // if a valid threshold value is given
				{
					dio.write("please choose a value between 0 and 1.\n");
				}
				
				else // if a valid threshold value is not given
				{
					break;
				}
			}
		}		
	}
	
	public class DetectAnomaliesCommand extends Command
	{
		// DETECT ANOMALIES
		public DetectAnomaliesCommand() 
		{
			super("This is a detect anomalies command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "detect anomalies";
			return;
		}

		@Override
		public void execute()
		{
			dio.write("anomaly detection complete.\n");
		}		
	}
	
	public class DisplayResultsCommand extends Command
	{
		// DISPLAY RESULTS
		public DisplayResultsCommand() 
		{
			super("This is a display results command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "display results";
			return;
		}

		@Override
		public void execute()
		{
			/*Display result of anomalies file*/ 			// TODO
			dio.write("73	 A-B\n");
			dio.write("74	 A-B\n");
			dio.write("75	 A-B\n");
			dio.write("76	 A-B\n");
			dio.write("133	 C-D\n");
			dio.write("134	 C-D\n");
			dio.write("135	 C-D\n");
			dio.write("Done.\n");
		}		
	}
	
	public class UploadAnomaliesCommand extends Command
	{
		// UPLOAD ANOMALIES FILE
		public UploadAnomaliesCommand() 
		{
			super("This is an anomalies upload command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "upload anomalies and analyze results";
			return;
		}

		@Override
		public void execute()
		{
			int scenario = 0; 																				// TODO DELETE
			dio.write("Please upload your local anomalies file.\n");
			/*Upload the anomalies file by writing to the server the name of the file*/						// TODO
			while (true)  // TODO DELETE
			{
				String text = dio.readText();
				//System.out.println(text); // TODO DELETE
				if (text.equals("done"))
				{
					break;
				}
				
				else if (text.equals("60,70")) // TODO DELETE
				{
					scenario = 1; // TODO DELETE
				}
				
				else if (text.equals("72,74")) // TODO DELETE
				{
					scenario = 2; // TODO DELETE
				}
				
				else if (text.equals("70,73")) // TODO DELETE
				{
					scenario = 3; // TODO DELETE
				}
				
				else if (text.equals("70,75")) // TODO DELETE
				{
					scenario = 4; // TODO DELETE
				}
				else if (text.equals("74,75")) // TODO DELETE
				{
					scenario = 5; // TODO DELETE
				}
			}
			//System.out.println("complete"); // TODO DELETE
			
			
			dio.write("Upload complete.\n");
			/*Some tests to check if the calculation is: False Positive / False Negative / True Positive*/
			if (scenario == 1) // TODO DELETE
			{
				dio.write("True Positive Rate: 0.0\n"); // TODO DELETE
				dio.write("False Positive Rate: 0.01\n"); // TODO DELETE
			}
			
			else if (scenario == 2) // TODO DELETE
			{
				dio.write("True Positive Rate: 0.5\n"); // TODO DELETE
				dio.write("False Positive Rate: 0.005\n"); // TODO DELETE
			}
			
			else if (scenario == 3) // TODO DELETE
			{
				dio.write("True Positive Rate: 1.0\n"); // TODO DELETE
				dio.write("False Positive Rate: 0.005\n"); // TODO DELETE
			}
			
			else if (scenario == 4) // TODO DELETE
			{
				dio.write("True Positive Rate: 1.0\n"); // TODO DELETE
				dio.write("False Positive Rate: 0.0\n"); // TODO DELETE
			}
			
			else if (scenario == 5) // TODO DELETE
			{
				dio.write("True Positive Rate: 0.666\n"); // TODO DELETE
				dio.write("False Positive Rate: 0.0\n"); // TODO DELETE
			}
		}		
	}
	
	public class ExitCommand extends Command
	{
		// CLOSE THE PROGRAM
		public ExitCommand() 
		{
			super("This is an exit command");
			setDescription();
		}
		
		public void setDescription()
		{
			this.description = "exit";
			return;
		}

		@Override
		public void execute()
		{
			//System.out.println("Exit"); // TODO DELETE
		}		
	}
	
}
