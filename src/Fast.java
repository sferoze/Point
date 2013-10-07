import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class Fast {

	private static void printSegment(ArrayList<Point> segment, int same) {

		for (int i = 0; i <= same; i++) {
			StdOut.print(segment.get(i).toString());
			if (i != same)
				StdOut.print(" -> ");
			else
				StdOut.println();
		}
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		segment.get(0).drawTo(segment.get(same));

	}

	private static void findSegments(Point p, Point[] sPA, Point[] pA, int index)
	{
	
		
		double[] slopes = new double[sPA.length];
		int same = 0;
		double previousSlope = -1;
		boolean trigger = false;
		ArrayList <Point> segment = new ArrayList<Point>();
		for (int i = 0; i < sPA.length; i++) {
			slopes[i] = p.slopeTo(sPA[i]);
	
			//System.out.println("Slope: " + slopes[i] + " for points " + p.toString() + " and " + sPA[i].toString());
			if (i > 0) // Process first point before you can check for duplicate slopes
			{
	    		if(slopes[i] == previousSlope) {
	    			trigger = true;
	    			if (same == 0) {
	    				segment.add(p);
	    				segment.add(sPA[i-1]);
	    				same++;
	    			}
	    			segment.add(sPA[i]);
	    			same++;
	    			//System.out.println("same is: " + same);
	    		} else {
	    			trigger = false;
	    			if (same < 3) {
	    				segment.clear();
	    				same = 0;
	    			}
	    		}
	    		if (same >= 3) {
	    			//System.out.println("Slope is: " + p.slopeTo(segment.get(1)));
	    			if (trigger == false | (i == (sPA.length -1)) /* or on last element */) {
	    				boolean onForward = true;
	    				// check backwards point for same slope meaning a subsegment
	    				for (int k = (index -1); k >= 0; k--) {
	    					double slope = p.slopeTo(pA[k]);
	    					//System.out.println("Checking backwards points from: " + p.toString() + " 
	    					//to " + pA[k].toString() + " and slope: " + slope);
	    					if (slope == slopes[i-1]) {
	    						onForward = false;
	    						break;
	    					}
	    				}
	    				//System.out.println("!!!!!same is: " + same);
	    				// If no subsegment then proceed to print the segment
	    				if (onForward)
	    					printSegment(segment, same);
	    				segment.clear();
	    				same = 0;
	    			}
	    		}
			}
			previousSlope = slopes[i];
		}
	}
	
	
	
	// unit test
	public static void main(String[] args) {
	    /* YOUR CODE HERE */
		
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
		    		StdDraw.setXscale(0, 32768);
		    		StdDraw.setYscale(0, 32768);
		    		aPoint.draw();
		    	}
		    	
		    	// Second step is to sort the array
		    	Arrays.sort(pointsArray);
		    	
		    	// Third step is to copy all the points after the origin to 2nd array
		    	for (int i = 1; i < (pointsArray.length); i++)
		    	{
		    		Point p = pointsArray[i-1];
		    		Point[] sPointsArray = new Point[pointsArray.length - i];
		    		int counter = i;
		    		for (int j = 0; j < sPointsArray.length; j++) {
		    			sPointsArray[j] = pointsArray[counter];
		    			counter++;
		    		}
		    		
		    		//System.out.println("The indexes: " + i);
		    		
		    		// Fourth step: sort the 2nd array by slope order to origin point
		    		Arrays.sort(sPointsArray, p.SLOPE_ORDER);
		    		
		    		// Send to findSegments
		    		findSegments(p, sPointsArray, pointsArray, i);
		    		
		    	}

	}

}
