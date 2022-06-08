package test;

public class StatLib {

	// simple average
	public static float avg(float[] x){
		
		float avg = 0f;
		
		for (int i = 0; i < x.length; i++)
		{
			avg += x[i];
		}

		avg /= x.length;

		return avg;
	}

	// returns the variance of X and Y
	public static float var(float[] x){

		float var = 0f;
		int size = x.length;
		float u = 0f;

		for (int i = 0; i < size; i++)
		{
			u += x[i];
		}

		u *= (1f/size);

		for (int j = 0; j < size; j++)
		{
			var += Math.pow((x[j]), 2);
		}

		var *= (1f/size);
		var -= Math.pow(u, 2);

		return var;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){

		float avgX = avg(x);
		float avgY = avg(y);

		float cov = 0;

		for (int i = 0; i < x.length; i++)
		{
			cov += ((x[i] - avgX) * (y[i] - avgY));
		}

		return cov / (x.length);
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		
		float pear = 0f;
		double devX;
		double devY;

		devX = Math.pow((double)var(x), (double)0.5);
		devY = Math.pow((double)var(y), (double)0.5);

		pear = cov(x,y) / ((float)devX * (float)devY);

		return pear;
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){

		float[] x = new float[points.length]; // Create an array of X's from the given Points array
		float[] y = new float[points.length]; // Create an array of Y's from the given Points array

		for (int i = 0; i < points.length; i++)
		{
			x[i] = points[i].x;
			y[i] = points[i].y;
		}

		float a = (cov(x,y) / var(x));
		float b = avg(y) - a * avg(x);

		Line line = new Line(a,b);

		return line;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){

		Line line = linear_reg(points);
		float dev =  Math.abs(line.f(p.y) - p.y);

		return dev;
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){

		float dev = Math.abs(l.f(p.x) - p.y);

		return dev;
	}
}
