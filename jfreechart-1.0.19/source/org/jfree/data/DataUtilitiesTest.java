package org.jfree.data;

import static org.junit.Assert.*; 
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.*;
import org.junit.*;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class DataUtilitiesTest extends DataUtilities {
	// Initialization for Equal and Clone tests
	private double a[][];
	private double b[][];
	private double c[][];
	private double d[][];
	private double e[][];
	private double f[][] = {{1.0}, null, {2}, {2}};
	
	// Initialization for Row tests
	private Values2D values;
	private KeyedValues keyedvalues;
	private Mockery mockingContext;
	int columnArray[] = {0, 1, 2, 3};
	
	// Initialization for Array tests
	Number[] expected = {-1.3, 5.6, -9.9, 10.5, 65.2};
	double[] input = {-1.3, 5.6, -9.9, 10.5, 65.2};
	Number[] expectedEmpty = {};
	double[] empty = {};
	
	@Before
	public void setUp() {
		
	/*---------------Test Setup for Row methods ---------------*/
		mockingContext = new Mockery();
	    values = mockingContext.mock(Values2D.class);
	    keyedvalues = mockingContext.mock(KeyedValues.class);
	    mockingContext.checking(new Expectations() {
			 {
				 one(values).getColumnCount();
				 will(returnValue(4));
				 one(values).getRowCount();
				 will(returnValue(3));
				 
				 one(values).getValue(0, 0);
				 will(returnValue(3));
				 one(values).getValue(0, 1);
				 will(returnValue(6));
				 one(values).getValue(0, 2);
				 will(returnValue(null));
				 one(values).getValue(0, 3);
				 will(returnValue(5.7));
				 
				 one(values).getValue(1, 0);
				 will(returnValue(92));
				 one(values).getValue(1, 1);
				 will(returnValue(77.9));
				 one(values).getValue(1, 2);
				 will(returnValue(12));
				 one(values).getValue(1, 3);
				 will(returnValue(105));
				 
				 one(values).getValue(2, 0);
				 will(returnValue(-23.4));
				 one(values).getValue(2, 1);
				 will(returnValue(15.6));
				 one(values).getValue(2, 2);
				 will(returnValue(-56.7));
				 one(values).getValue(2, 3);
				 will(returnValue(-100));
				 
				 
				// invalid indexes

				
	            one(values).getValue(-5, 0);
	            will(returnValue(1));
	            one(values).getValue(-5, 1);
	            will(returnValue(2));
	            one(values).getValue(-5, 2);
	            will(returnValue(3));
				one(values).getValue(-5, 3);
	            will(returnValue(4));
	            
	            
	            one(values).getValue(0, -5);
	            will(returnValue(1));
	            one(values).getValue(1, -5);
	            will(returnValue(null));
	            one(values).getValue(2, -5);
	            will(returnValue(3));
				one(values).getValue(3, -5);
	            will(returnValue(4));
	
	            
	            one(values).getValue(3, 0);
	            will(returnValue(1));
	            one(values).getValue(3, 1);
	            will(returnValue(2));
	            one(values).getValue(3, 2);
	            will(returnValue(3));
				one(values).getValue(3, 3);
	            will(returnValue(4));
	            
	  
	            one(values).getValue(0, 3);
	            will(returnValue(1));
	            one(values).getValue(1, 3);
	            will(returnValue(null));
	            one(values).getValue(2, 3);
	            will(returnValue(3));
				one(values).getValue(3, 3);
	            will(returnValue(4));
			 }
		 });
		
	/*---------------Test Setup for Equal and Clone methods---------------*/
	    
		a = new double [2][2];
		b = new double [2][2];
		c = new double [2][2];
		d = new double [3][2];
		e = new double [2][3];
		
		a[0][0] = 4;
		a[1][0] = 3;
		a[0][1] = 2;
		a[1][1] = 1;
		
		b[0][0] = 4;
		b[1][0] = 3;
		b[0][1] = 2;
		b[1][1] = 1;
		
		c[0][0] = 7;
		c[1][0] = 3;
		c[0][1] = 9;
		c[1][1] = 2;
		
		d[0][0] = 4;
		d[1][0] = 3;
		d[0][1] = 2;
		d[1][1] = 1;
		d[2][0] = 0;
		d[2][1] = 5;
		
		e[0][0] = 4;
		e[1][0] = 3;
		e[0][1] = 2;
		e[1][1] = 1;
		e[0][2] = 0;
		e[0][2] = 1;
		
	}
	
/*---------------Tests for public static double calculateRowTotal(Values2D data, int row)---------------*/
	
	@Test
	public void testCalculateRowTotal_ValidFirstRow() {
		double results = DataUtilities.calculateRowTotal(values, 0);
		assertEquals("The sum of row 1 is", 14.7, results, .000000001d);
	}

	@Test 
   	public void testCalculateRowTotal_ValidMiddleIndex() {
    	double result = DataUtilities.calculateRowTotal(values, 1);
    	assertEquals("Sum of the middle row is", 286.9, result, .000000001d);
    }

	@Test 
   	public void testCalculateRowTotal_ValidLastIndex() {
    	double result = DataUtilities.calculateRowTotal(values, 2);
    	assertEquals("Sum of the last row is", -164.5, result, .000000001d);
    }
    
    /*---------------Tests for public static double calculateRowTotal(Values2D data, int row, int[] validCols)---------------*/
    
    @Test
    public void testCalculateRowTotal_ThreeInputs() {
    	double result = DataUtilities.calculateRowTotal(values, 1, columnArray);
    	assertEquals("Sum o the row is", 286.9, result, .000000001d);
    }
    
    /*---------------Tests for public static Number[] createNumberArray(double[] data)---------------*/
    
    @Test
    public void testCreateNumberArray_ValidArray() {
    	assertArrayEquals("Valid double array.", expected, DataUtilities.createNumberArray(input));
    }
    
    @Test
    public void testCreateNumberArray_EmptyArray() {
    	assertArrayEquals("Empty array.", expectedEmpty, DataUtilities.createNumberArray(empty));
    }
	
	/*---------------Tests for public static boolean equal(double[][] a, double[][] b)---------------*/
	
	@Test
	public void testEqual_SameArray() {
		assertTrue("Output should be true.", DataUtilities.equal(a, b));
	}
	
	@Test
	public void testEqual_DifferentArray() {
		assertFalse("Output should be false.", DataUtilities.equal(a, c));
	}
	
	@Test
	public void testEqual_DifferentColSize() {
		assertFalse("Output should be false.", DataUtilities.equal(a, d));
	}
	
	@Test
	public void testEqual_DifferentRowSize() {
		assertFalse("Output should be false.", DataUtilities.equal(b, e));
	}
	
	@Test
	public void testEqual_FirstNullValue() {
		assertFalse("Output should be false.", DataUtilities.equal(null, b));
	}
	
	@Test
	public void testEqual_SecondNullValue() {
		assertFalse("Output should be false.", DataUtilities.equal(a, null));
	}
	
	@Test
	public void testEqual_BothNull() {
		assertTrue("Output should be false.", DataUtilities.equal(null, null));
	}
	
	/*---------------Tests for public static double[][] clone(double[][] source)---------------*/
	
	@Test
	public void testClone_ShallowCopy() {
		assertArrayEquals("Array should be equal to source.", a, DataUtilities.clone(a));
	}
	
	@Test
	public void testClone_NullValue() {
		assertArrayEquals("Array should be equal.", f, DataUtilities.clone(f));
	}
	
	/*---------------Tests for public static double calculateColumnTotal(Values2D data, int column)---------------*/
	
	@Test
	 public void calculateColumnTotalForTwoValues() {	    
	     double result = DataUtilities.calculateColumnTotal(values, 0);
	     assertEquals( 71.6, result, .000000001d);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void calculateColumnTotalwithnulldata() throws Exception{
		 Values2D empty = null;
		 try {
			double result = DataUtilities.calculateColumnTotal(empty, 0); 
		 } catch (IllegalStateException e) {
			 
		 }
		 
	 }
	 
	 /*---------------Tests for public static Number[][] createNumberArray2D(double[][] data)---------------*/

	 @Test
	 public void createNumberArray2Dwithvalues() {
		double[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		Number[][] result = DataUtilities.createNumberArray2D(array);
		for (int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[i].length; j++) {
				assertEquals("Values should be equal", array[i][j], result[i][j]);
			}
		}
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void createNumberArray2Dwithnullvalues() {
		double[][] array = null;
		Number[][] result = DataUtilities.createNumberArray2D(array);
	 }
	 
	 @Test
	 public void createNumberArray2Dwithnovalues() {
		double[][] array = {{}, {}, {}};
		Number[][] result = DataUtilities.createNumberArray2D(array);
		for (int i = 0; i < array.length; i++) {
			assertEquals("Array should be empty", 0, result[i].length);
		}
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void getCumulativePercentages_NullInput() {
		 
		 KeyedValues invalid = null;

		 getCumulativePercentages(invalid);
		 
	 }
	 
	 /*---------------Tests for public static KeyedValues getCumulativePercentages(KeyedValues data)---------------*/
	 
	 @Test
	 public void getCumulativePercentages_ValidValues() {
		 mockingContext.checking(new Expectations() {
			 {
				 allowing(keyedvalues).getItemCount();
				 will(returnValue(3));
				 allowing(keyedvalues).getValue(0);
				 will(returnValue(2));
				 allowing(keyedvalues).getValue(1);
				 will(returnValue(8));
				 allowing(keyedvalues).getValue(2);
				 will(returnValue(15));
				 allowing(keyedvalues).getKey(0);
				 will(returnValue(0));
				 allowing(keyedvalues).getKey(1);
				 will(returnValue(1));
				 allowing(keyedvalues).getKey(2);
				 will(returnValue(2));
			 }
		 });
		 
		 float percent = 0;
		 float totalnumerator = 0;
		 float totaldivisor = 0;
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totaldivisor = totaldivisor + keyedvalues.getValue(i).floatValue();
		 }
		 
		 
		 KeyedValues result = getCumulativePercentages(keyedvalues);
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totalnumerator = totalnumerator + keyedvalues.getValue(i).floatValue();
			 percent = (totalnumerator/totaldivisor); 
			 System.out.println(percent);
			 System.out.println(result.getValue(i).floatValue());
			 assertEquals("Values should be equal", percent, result.getValue(i).floatValue(), .01d);
		 }
	 }
	 
	 @Test
	 public void getCumulativePercentages_NegativeValues() {
		 mockingContext.checking(new Expectations() {
			 {
				 allowing(keyedvalues).getItemCount();
				 will(returnValue(3));
				 allowing(keyedvalues).getValue(0);
				 will(returnValue(-2));
				 allowing(keyedvalues).getValue(1);
				 will(returnValue(-8));
				 allowing(keyedvalues).getValue(2);
				 will(returnValue(-15));
				 allowing(keyedvalues).getKey(0);
				 will(returnValue(0));
				 allowing(keyedvalues).getKey(1);
				 will(returnValue(1));
				 allowing(keyedvalues).getKey(2);
				 will(returnValue(2));
			 }
		 });
		 
		 float percent = 0;
		 float totalnumerator = 0;
		 float totaldivisor = 0;
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totaldivisor = totaldivisor + keyedvalues.getValue(i).floatValue();
		 }
		 
		 
		 KeyedValues result = getCumulativePercentages(keyedvalues);
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totalnumerator = totalnumerator + keyedvalues.getValue(i).floatValue();
			 percent = (totalnumerator/totaldivisor); 
			 System.out.println(percent);
			 System.out.println(result.getValue(i).floatValue());
			 assertEquals("Values should be equal", percent, result.getValue(i).floatValue(), .01d);
		 }
	 }
	 
	 @Test(expected = IllegalStateException.class)
	 public void getCumulativePercentages_StringValues() {
		 mockingContext.checking(new Expectations() {
			 {
				 allowing(keyedvalues).getItemCount();
				 will(returnValue(3));
				 allowing(keyedvalues).getValue(0);
				 will(returnValue("2"));
				 allowing(keyedvalues).getValue(1);
				 will(returnValue("8"));
				 allowing(keyedvalues).getValue(2);
				 will(returnValue("15"));
				 allowing(keyedvalues).getKey(0);
				 will(returnValue(0));
				 allowing(keyedvalues).getKey(1);
				 will(returnValue(1));
				 allowing(keyedvalues).getKey(2);
				 will(returnValue(2));
			 }
		 });
		 
		 float percent = 0;
		 float totalnumerator = 0;
		 float totaldivisor = 0;
		 KeyedValues result = getCumulativePercentages(keyedvalues);
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totaldivisor = totaldivisor + keyedvalues.getValue(i).floatValue();
		 }
		 
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totalnumerator = totalnumerator + keyedvalues.getValue(i).floatValue();
			 percent = (totalnumerator/totaldivisor); 
			 System.out.println(percent);
			 System.out.println(result.getValue(i).floatValue());
			 assertEquals("Values should be equal", percent, result.getValue(i).floatValue(), .01d);
		 }
	 }
	 
	 
	 //This new test adds condition coverage to the two v = null if statements by making it true
	 @Test(expected = NullPointerException.class)
	 public void getCumulativePercentages_nullValues() {
	 	 mockingContext.checking(new Expectations() {
	 		 {
	 			 allowing(keyedvalues).getItemCount();
	 			 will(returnValue(3));
	 			 allowing(keyedvalues).getValue(0);
	 			 will(returnValue(null));
	 			 allowing(keyedvalues).getValue(1);
	 			 will(returnValue(null));
	 			 allowing(keyedvalues).getValue(2);
	 			 will(returnValue(null));
	 			 allowing(keyedvalues).getKey(0);
	 			 will(returnValue(0));
	 			 allowing(keyedvalues).getKey(1);
	 			 will(returnValue(1));
	 			 allowing(keyedvalues).getKey(2);
	 			 will(returnValue(2));
	 		 }
	 	 });
		 float percent = 0;
		 float totalnumerator = 0;
		 float totaldivisor = 0;
		 KeyedValues result = getCumulativePercentages(keyedvalues);
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totaldivisor = totaldivisor + keyedvalues.getValue(i).floatValue();
		 }
		 
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totalnumerator = totalnumerator + keyedvalues.getValue(i).floatValue();
			 percent = (totalnumerator/totaldivisor); 
			 System.out.println(percent);
			 System.out.println(result.getValue(i).floatValue());
			 assertEquals("Values should be equal", percent, result.getValue(i).floatValue(), .01d);
		 }
	 }
	 
	 
	 //This new test adds both condition and statement coverage to a broken piece of code that will create an infinite loop
	 @Test
	 public void getCumulativePercentages_negativeitemcount() {
	 	 mockingContext.checking(new Expectations() {
	 		 {
	 			 allowing(keyedvalues).getItemCount();
	 			 will(returnValue(-3));
	 			 allowing(keyedvalues).getValue(0);
	 			 will(returnValue(5));
	 			 allowing(keyedvalues).getValue(1);
	 			 will(returnValue(7));
	 			 allowing(keyedvalues).getValue(2);
	 			 will(returnValue(9));
	 			 allowing(keyedvalues).getKey(0);
	 			 will(returnValue(0));
	 			 allowing(keyedvalues).getKey(1);
	 			 will(returnValue(1));
	 			 allowing(keyedvalues).getKey(2);
	 			 will(returnValue(2));
	 		 }
	 	 });
		 float percent = 0;
		 float totalnumerator = 0;
		 float totaldivisor = 0;
		 KeyedValues result = getCumulativePercentages(keyedvalues);
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totaldivisor = totaldivisor + keyedvalues.getValue(i).floatValue();
		 }
		 
		 
		 for (int i = 0; i < keyedvalues.getItemCount(); i++) {
			 totalnumerator = totalnumerator + keyedvalues.getValue(i).floatValue();
			 percent = (totalnumerator/totaldivisor); 
			 System.out.println(percent);
			 System.out.println(result.getValue(i).floatValue());
			 assertEquals("Values should be equal", percent, result.getValue(i).floatValue(), .01d);
		 }
	 }
	 
	 /*---------------Tests for public static double calculateColumnTotal(Values2D data, int column, int[] validRows)---------------*/
	 
	 @Test
	 public void calculateColumnTotalotherparameters() {
		 int [] array = {0,1};
			 double result = DataUtilities.calculateColumnTotal(values, 0, array);
		     assertEquals(95.0, result, .000000001d);
	 }
	 

}