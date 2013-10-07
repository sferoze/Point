/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;


public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;							  // y coordinate
    
    private class SlopeOrder implements Comparator<Point>
    {
    	public int compare(Point q1, Point q2)
    	{
    		
    		double slope1, slope2;
    		try {
    		slope1 = (double)(q1.y - Point.this.y) / (q1.x - Point.this.x);
    		} catch (ArithmeticException e) {
    			if ((q2.x - Point.this.x) == 0) {
    				return (q2.x > q1.x)? 0 : -0;
    			} else
    				return 1;
    		}
    		try {
    			slope2 = (double)(q2.y - Point.this.y) / (q2.x - Point.this.x);
        		} catch (ArithmeticException e) {
        			if ((q1.x - Point.this.x) == 0) {
        				return (q2.x > q1.x)? 0 : -0;
        			} else
        				return 1;
        		}
    		
    		if (slope1 == slope2)
    			return (q2.x > q1.x) ? 0 : -0;
    		else if (slope1 > slope2)
    			return -1;
    		else
    			return 1;

    	}
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	double slope = Double.POSITIVE_INFINITY;
    	try {
    		slope = (double)(that.y - this.y)/ (that.x - this.x);
    	} catch (ArithmeticException e) {

    	}
    	
    	if (slope == (Double.NEGATIVE_INFINITY))
    		slope = Double.POSITIVE_INFINITY;
    	return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if (that.y == this.y)
    		return (that.x > this.x) ? 0 : 1;
    	else if (that.y > this.y)
    		return -1;
    	else
    		return 1;	
    }
    

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    /*
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
	    			if (trigger == false | (i == (sPA.length -1))) { // or last segment
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
    	
    	File f = new File(args[0]);
    	Scanner input;
    	
    	// First step take in point from file put into an array
    	try {
				input = new Scanner(f);
				int N = input.nextInt();
				Point[] pointsArray = new Point[N];
		    	for (int i = 0; i < N; i++) 
		    	{
		    		Point aPoint = new Point(input.nextInt(), input.nextInt());
		    		pointsArray[i] = aPoint;
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
		    	

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    }
    */
}