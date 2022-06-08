package test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	
	List<CorrelatedFeatures> corrFeatures = new ArrayList<CorrelatedFeatures>(); // List containing all the CorrelatedFeatures

	@Override
	public void learnNormal(TimeSeries ts) // Perform an upper triangle matrix scan to correlate between each column with the others and find the strongest correlation
	{
		float corrThreshold; // a correlation must be above this value
		int size = ts.GetColumnsNum();
		int rowsNum = ts.GetRowsNum()-1; // The number of rows in each column without the column name row
		
		for(int i = 0; i < size - 1; i++) // The current column to compare with the others (does not include the last column because it does not have anyone to compare to)
		{			
			for (int j = i+1; j < size; j++) // The current column which is compared to the 'i' column (does not include the i column itself so it wont compare a column with itself)
			{
				float pears = test.StatLib.pearson(ts.GetColumnArrFloat(i), ts.GetColumnArrFloat(j)); // Perform a Pearson correlation
				pears = Math.abs(pears); // If the result is negative, make it positive
				if (pears > 1) // Prevent pears from being larger than 1 (caused by float values being rounded up)
				{
					pears = 1; // Prevent pears from being larger than 1 (caused by float values being rounded up)
				}

				//////////////////////////////////////////////////
				// Create parameters to make the CorrealtedFeature for the current correlation and add it to the list CorrealtedFeatures
				corrThreshold = 0f; // A default value for threshold
				String firstColName = ts.GetAttributeColumn(i).get(0); // Save the first correlated column's name (the one on the left) into a variable
				String secondColName = ts.GetAttributeColumn(j).get(0); // Save the second correlated column's name (the one on the right) into a variable
				test.Point[] points = new test.Point[rowsNum]; // Create an array of Points of which will provide the Linear Regression. each point in the array is a point made by the values of each line of the correlated columns (left column = x, right column = y)
				
				for(int row = 0; row < rowsNum; row++) // For each row in the columns (each point)
				{
					test.Point point = new test.Point(ts.GetColumnArrFloat(i)[row], ts.GetColumnArrFloat(j)[row]); // Create a point from each value in the correlated column (left column is the point's x value, right column is the point's y value)
					points[row] = point; // Add the created Point to the Points array
				}
				
				test.Line regLine = test.StatLib.linear_reg(points); // Create a Line by Linear Regression from the Points array of this correlation
				
				for(int row = 0; row < rowsNum; row++) // For each row in the columns (each point)
				{
					if (Math.abs(points[row].y - regLine.f(points[row].x)) > corrThreshold) // Check if the iterated point's y value minus the regLine's y value for the same x is bigger than the threshold
					{
						corrThreshold = Math.abs(points[row].y - regLine.f(points[row].x)); // Make the threshold that same value
						corrThreshold *= 1.1f; // Add 10% to allow close to threshold values
					}
				}
				
				test.CorrelatedFeatures corrFeature = new test.CorrelatedFeatures(firstColName, secondColName, pears, regLine, corrThreshold); // Create a Correlated Feature from the given parameters
				
				int corrIndex = 0; // To know which correlation is being iterated in case of correlation removal
				
				for(test.CorrelatedFeatures correlation : corrFeatures)
				{
					if (corrFeature.feature1 == correlation.feature1 || corrFeature.feature1 ==  correlation.feature2 || corrFeature.feature2 ==  correlation.feature2) // if the current iterated column has been added to the correlation list as a left column or a right column
					{
						if (corrFeature.corrlation > correlation.corrlation) // If the current correlation is bigger than the one in the list
						{
							corrFeatures.remove(corrIndex); // Remove the smaller correlation from the list 
							corrFeatures.add(corrFeature); // Add the new CorrelatedFeature to the CorrelatedFeatures List
						}
						break;
					}
					
					if (corrIndex == corrFeatures.size() - 1) // If the current correlation's columns are not in the list, add the correlation to the list
					{
						corrFeatures.add(corrFeature); // Add the new CorrelatedFeature to the CorrelatedFeatures List
						break;
					}
					
					corrIndex++;
				}
				
				if (corrFeatures.size() == 0) // If the sit is empty, add the first correlation anyway
				{
					corrFeatures.add(corrFeature); // Add the new CorrelatedFeature to the CorrelatedFeatures List
				}
				//////////////////////////////////////////////////
			}
		}		
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts)
	{
		List<AnomalyReport> anomalyReports = new ArrayList<AnomalyReport>();
		
		int rowsNum = ts.GetRowsNum() - 1;
		
		for(int i = 0; i < rowsNum; i++)
		{
			for(test.CorrelatedFeatures correlation : corrFeatures)
			{
				float feature1 = ts.GetColumnTimeValue(correlation.feature1, i);
				float feature2 = ts.GetColumnTimeValue(correlation.feature2, i);
				
				if (Math.abs(feature2 - correlation.lin_reg.f(feature1)) > correlation.threshold)
				{
					String description = correlation.feature1 + "-" + correlation.feature2;
					long timeStep = (long)ts.GetColumnTimeValue(correlation.feature1, i);
					AnomalyReport anomalyReport = new AnomalyReport(description, i);
					anomalyReports.add(anomalyReport);
				}
			}
		}
		
		return anomalyReports;
	}
	
	public List<CorrelatedFeatures> getNormalModel()
	{
		
		return corrFeatures;
	}
}
