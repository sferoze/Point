import java.io.File;
import java.util.Arrays;


public class Brute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File(args[0]);
		In input;
		
		// First step take in point from file put into an array
	
				input = new In(f);
				int N = input.readInt();
				Point[] pointsArray = new Point[N];
		    	for (int i = 0; i < N; i++) 
		    	{
		    		Point aPoint = new Point(input.readInt(), input.readInt());
		    		pointsArray[i] = aPoint;
		    	}
		    	double slope1;
		    	double slope2;
		    	double slope3;
		    	Arrays.sort(pointsArray);
		    	
		    	for (int i = 0; i < pointsArray.length; i++) {
		    		StdDraw.setXscale(0, 32768);
					StdDraw.setYscale(0, 32768);
		    		pointsArray[i].draw();
		    	}

		    	for (int i = 0; i < pointsArray.length; i++) 
		    	{
		    		for (int j = i+1; j < pointsArray.length; j++)
		    		{
		    			
		    			for (int k = j+1; k < pointsArray.length; k++)
		    			{
		    				
		    				for (int l = k+1; l < pointsArray.length; l++)
			    			{

		    					slope1 = pointsArray[i].slopeTo(pointsArray[j]);
		    					slope2 = pointsArray[i].slopeTo(pointsArray[k]);
		    					slope3 = pointsArray[i].slopeTo(pointsArray[l]);
		    					if (slope1 == slope2 && slope1 == slope3) 
		    					{

		    							StdOut.print(pointsArray[i].toString() + " -> " + pointsArray[j].toString() + " -> " + pointsArray[k].toString() + " -> " + pointsArray[l].toString());
			    						StdOut.println();
			    						StdDraw.setXscale(0, 32768);
			    						StdDraw.setYscale(0, 32768);
			    						
			    						pointsArray[i].drawTo(pointsArray[l]);

		    					}
			    			}
		    			}
		    			
		    		}
		    	}
	}

}
