package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSeries
{
	public String[] values;
	public int collumnsNum;
	public List<ArrayList<String>> columnsList = new ArrayList<ArrayList<String>>(); // a list of each column in an array of lists
	
	public TimeSeries(String csvFileName) // Ctor
	{
		BufferedReader reader = null;
		String line = "";
		
		try
		{
			reader = new BufferedReader(new FileReader(csvFileName));
			
			/* Get the number of columns into a variable */
			line = reader.readLine();
	    	values = line.split(",");
	    	collumnsNum = values.length;
		    /* ^Get the number of columns into a variable^ */
			
		    
	    	/* Get each columns into a list */
		    for (int i = 0; i < collumnsNum; i++)
		    {
		    	ArrayList<String> column = new ArrayList<String>(); // Create a new list which represents a column
		    	reader.close();
		    	reader = new BufferedReader(new FileReader(csvFileName));
		    	
		    	while((line = reader.readLine()) != null) // Iterate through the collumn's values
		    	{
		    		values = line.split(",");
			    	column.add(values[i]); // Add the current iterated value to the column's list
		    	}

		    	columnsList.add(column); // Add the new column to the list of columns
		    }
		    /* Get each columns into a list */
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<String> GetAttributeColumn(String columnName) // Given a column name as a parameter, return the desired column as a list of String
	{
		for (ArrayList<String> column : this.columnsList) // Iterate through each column in the columns list
		{
			if (column.get(0).equals(columnName)) // Find the desired column
			{
				return column; // Return the desired column
			}
		}
		
		return null; // If the column does not exist, return null
	}
	
	public ArrayList<String> GetAttributeColumn(int index) // Given a column index as a parameter, return the desired column as a list of String
	{
		if (index > this.collumnsNum - 1 || index < 0) // If the given column index is more than the there are column indexes or if it's a negative number
		{
			return null;
		}
		
		return  this.columnsList.get(index); // Return the desired column by index
	}
	
	public List<ArrayList<String>> GetColumnsList() // Return the list of columns of the TimeSeries
	{
		return columnsList;
	}
	
	public int GetColumnsNum() // Return the number of columns in the TimeSeries
	{
		return this.collumnsNum;
	}
	
	public int GetRowsNum() // Return the number of rows in each column of the TimeSeries (Including attribute name row)
	{
		return this.columnsList.get(0).size();
	}
	
	public float GetColumnTimeValue(String columnName, int timeStep) // Given a column Name and a time step (row index) Return the value in that same cell of the TimeSeries as a float
	{	
		if (timeStep < 1) // If given illegal time step (time step must be >= 1)
		{
			return Float.NaN;
		}
		
		if (this.GetAttributeColumn(columnName) == null) // Check if the given column exists in the TimeSeries
		{
			return Float.NaN;
		}
		
		if (this.GetAttributeColumn(columnName).size() <= timeStep) // Check if the given time step is in column's boundary
		{
			return Float.NaN;
		}
		
		if (this.GetAttributeColumn(columnName).get(timeStep) != null) // If the desired cell was found
		{
			return Float.parseFloat(this.GetAttributeColumn(columnName).get(timeStep));
		}
		
		return Float.NaN;
	}
	
	public float[] GetColumnArrFloat(String columnName) // Given a column name, return an array of type float with the values of the given column (without the column name row) 
	{
		float[] arr = new float[this.GetRowsNum() - 1]; // Create the end float array which will contain the column's numeric values
		int i = -1; // Index of the iteration of the column's values. It is -1 to prevent the column's attribute name from being included
		
		if (this.GetAttributeColumn(columnName) == null) // If given column name does not exist, return null
		{
			return null;
		}
		
		List<String> column = this.GetAttributeColumn(columnName);
		for(String value : column) // Iterate through each numeric value in the given column
		{
			if (i != -1) // If it is not the first iteration which has the attribute name
			{
				arr[i] = Float.parseFloat(value); // Add current iterated value to the float array		
			}
			
			i++;
		}	
		
		return arr;
	}
	
	public float[] GetColumnArrFloat(int columnIdx) // Given a column index, return an array of type float with the values of the given column (without the column name row) 
	{
		float[] arr = new float[this.GetRowsNum() - 1]; // Create the end float array which will contain the column's numeric values
		int i = -1; // Index of the iteration of the column's values. It is -1 to prevent the column's attribute name from being included
		
		List<String> column = this.GetAttributeColumn(columnIdx);
		for(String value : column) // Iterate through each numeric value in the given column
		{
			if (i != -1) // If it is not the first iteration which has the attribute name
			{
				arr[i] = Float.parseFloat(value); // Add current iterated value to the float array		
			}
			
			i++;
		}	
		
		return arr;
	}
}