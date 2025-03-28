package org.jfree.data;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest {
    
    private Range testRangeInt;
    private Range testRangeDec;
    private Range rangeOne;
    private Range rangeTwo;
    private Range rangeThree;
    private Range testRange;
    private Range nanRange;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        testRangeInt = new Range(3, 5);
        testRangeDec = new Range(-5.5, -2.5);
        rangeOne = new Range(-1, 1);
        rangeTwo = new Range(0, 0);
        rangeThree = new Range(0, 2.3);
        testRange = new Range(-2.5, 2.5);
        nanRange = new Range(Double.NaN, Double.NaN);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBoundGetters() {
        assertEquals("Lower bound of (-5.5, -2.5) should be -5.5", -5.5, testRangeDec.getLowerBound(), .000000001d);
        assertEquals("Upper bound of (-5.5, -2.5) should be -2.5", -2.5, testRangeDec.getUpperBound(), .000000001d);
        assertEquals("Lower bound of (3, 5) should be 3", 3, testRangeInt.getLowerBound(), .000000001d);
        assertEquals("Upper bound of (3, 5) should be 5", 5, testRangeInt.getUpperBound(), .000000001d);
    }

    @Test
    public void testRangeConstrain() {
        assertEquals("-1 should be the closest (in-range) value to -3", -1, rangeOne.constrain(-3), .000000001d);
        assertEquals("1 should be the closest (in-range) value to 4", 1, rangeOne.constrain(4), .000000001d);
        assertEquals("0.1 should be returned as it is in range of -1 and 1", 0.1, rangeOne.constrain(0.1), .000000001d);
    }
    
    @Test
    public void testRangeContains() {
    	assertTrue("-1 should be in the range of -1, 1", rangeOne.contains(-1));
		assertTrue("1 should be in the range of -1, 1", rangeOne.contains(1));
		assertTrue("0.7 should be in the range of -1, 1", rangeOne.contains(0.7));
		assertFalse("2 shouldn't be in the range of -1, 1", rangeOne.contains(2));
		assertFalse("-2 shouldnt be in the range of -1, 1", rangeOne.contains(-2));
		assertFalse("1 shouldnt be in the range of NaN", nanRange.contains(1));
    }

    @Test
    public void testExpandToInclude_Null() {
        Range nil = Range.expandToInclude(null, 1);
        assertEquals(1, nil.getLowerBound(), .000000001d);
        assertEquals(1, nil.getUpperBound(), .000000001d);
    }

    @Test
    public void testExpandToInclude_LessThan() {
        Range range = new Range(1, 4);
        Range expanded = Range.expandToInclude(range, 0);
        assertEquals(0, expanded.getLowerBound(), .000000001d);
        assertEquals(4, expanded.getUpperBound(), .000000001d);
    }

    @Test
    public void testExpandToInclude_MoreThan() {
        Range range = new Range(1, 4);
        Range expanded = Range.expandToInclude(range, 8);
        assertEquals(1, expanded.getLowerBound(), .000000001d);
        assertEquals(8, expanded.getUpperBound(), .000000001d);
    }

    @Test
    public void testExpandToInclude_InRange() {
        Range range = new Range(1, 4);
        Range expanded = Range.expandToInclude(range, 2);
        assertEquals(1, expanded.getLowerBound(), .000000001d);
        assertEquals(4, expanded.getUpperBound(), .000000001d);
    }

    @Test
    public void testGetLength() {
        assertEquals("The length of -1 and 1 should be 2", 2, rangeOne.getLength(), .000000001d);
        assertEquals("The length of 0 and 0 should be 0", 0, rangeTwo.getLength(), .000000001d);
        assertEquals("The length of 0 and 2.3 should be 2.3", 2.3, rangeThree.getLength(), .000000001d);
    }


    @Test
    public void testIntersects() {
        assertTrue("Ranges (-2.5, 2.5) and (2, 4) should overlap", testRange.intersects(2, 4));
        assertFalse("Ranges (-2.5, 2.5) and (5, 8) should not overlap", testRange.intersects(5, 8));
        assertTrue("Ranges (-2.5, 2.5) and (-2.5, 2.5) should overlap", testRange.intersects(-2.5, 2.5));
        assertTrue("Ranges (-2.5, 2.5) and (-5.4, -2.4) should overlap", testRange.intersects(-5.4, -2.4));
        assertFalse("Ranges (-2.5, 2.5) and (-5.6, -2.6) should not overlap", testRange.intersects(-5.6, -2.6));
        assertTrue("Ranges (-2.5, 2.5) and (-3, 3) should overlap", testRange.intersects(-3, 3));
        assertTrue("Ranges (-2.5, 2.5) and (-2, 2) should overlap", testRange.intersects(-2, 2));
    }
    
    @Test
    public void testCombine_rangeOneNull() {
    	Range result = Range.combine(null, new Range(2,5));
    	assertEquals("Combining null with (2, 5) should return (2,5)", 2, result.getLowerBound(), .000000001d);
    	assertEquals("Combining null with (2, 5) should return (2,5)", 5, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombine_rangeTwoNull() {
    	Range result = Range.combine(new Range(2, 5), null);
    	assertEquals("Combining null with (2, 5) should return (2,5)", 2, result.getLowerBound(), .000000001d);
    	assertEquals("Combining null with (2, 5) should return (2,5)", 5, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombine_ranges() {
    	Range result = Range.combine(new Range(2, 5), new Range(6, 8));
    	assertEquals("Combining (6, 8) with (2, 5) should return (2, 8)", 2, result.getLowerBound(), .000000001d);
    	assertEquals("Combining (6, 8) with (2, 5) should return (2, 8)", 8, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombineNan_bothNull() {
    	assertNull("Combining two null ranges should return null", Range.combineIgnoringNaN(null, null));
    	}
    	
    @Test
    public void testCombineNan_firstNan() {
    	assertNull("combining nan range with null should return null", Range.combineIgnoringNaN(nanRange, null));
    }
    
    @Test
    public void testCombineNan_secondNan() {
    	assertNull("combining nan range with null should return null", Range.combineIgnoringNaN(null, nanRange));
    }
    
    @Test
    public void testCombineNan_firstRangeNullSecondValid() {
    	Range result = Range.combineIgnoringNaN(null, new Range(1, 4));
    	assertEquals("Should return (1, 4)", 1, result.getLowerBound(), .000000001d);
    	assertEquals("Should return (1, 4)", 4, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombineNan_firstRangeValidSecondNull() {
    	Range result = Range.combineIgnoringNaN(new Range(1, 4), null);
    	assertEquals("Should return (1, 4)", 1, result.getLowerBound(), .000000001d);
    	assertEquals("Should return (1, 4)", 4, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombineNan_normal() {
    	Range result = Range.combineIgnoringNaN(new Range(1, 4), new Range(-3, 2));
    	assertEquals("Should return (-3, 4)", -3, result.getLowerBound(), .000000001d);
    	assertEquals("Should return (-3, 4)", 4, result.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombineNan_bothNanRanges() {
    	assertNull("Should return null", Range.combineIgnoringNaN(nanRange, nanRange));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRange_lowerGreaterthanUpper() {
    	new Range(670, 123);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testScale_nullRange() {
    	Range.scale(null, 4);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testScale_negativeScale() {
    	Range.scale(new Range(1, 2), -2);
    }
    
    @Test
    public void testScale_normal() {
    	Range result = Range.scale(new Range(3, 7), 3);
    	assertEquals("Range should now be (9, 21)", 9, result.getLowerBound(), .000000001d );
    }
    
    @Test
    public void testToString() {
    	assertEquals("Range[-1.0,1.0]", rangeOne.toString());
    }
    
    @Test
    public void testHashCode1() {
    	int h = rangeOne.hashCode();
    	assertNotNull(h);
    }
    
    @Test
    public void testEquals_equalsNull() {
    	assertFalse("Range can't be equal to null", (new Range(0, 1).equals(null)));
    }
    
    @Test
    public void testEquals_lowerEqual() {
    	assertFalse("Range [-1, 1] shouldn't be equal to [-1, 2]", rangeOne.equals(new Range(-1, 2)));
    }
    
    @Test
    public void testEquals_upperEqual() {
    	assertFalse("Range [-1, 1] shouldn't be equal to [-2, 1]", rangeOne.equals(new Range(-2, 1)));
    }
    
    @Test
    public void testEqual_isEqual() {
    	assertTrue("Range [-1, 1] should be equal to itself", rangeOne.equals(rangeOne));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testShift_nullRange() {
    	Range.shift(null, 2, false);
    }
    
    @Test
    public void testShift_NoZeroCrossing() {
    	Range shifted = Range.shift(rangeOne, 2, false);
    	assertEquals("-1 should be 0", 0, shifted.getLowerBound(), 0.00000001d);
    	assertEquals("1 should be 3", 3, shifted.getUpperBound(), 0.00000001d);
    }
    
    @Test
    public void testShift_ZeroCrossingSameRange() {
    	Range shifted = Range.shift(new Range(2, 2), -3, true);
    	assertEquals("2 should be -1", -1, shifted.getLowerBound(), 0.00000001d);
    	assertEquals("2 should be -1", -1, shifted.getUpperBound(), 0.00000001d);
    }
    
    @Test
    public void testShift_NoZeroCrossingSameRange() {
    	Range shifted = Range.shift(new Range(0, 0), 3, false);
    	assertEquals("0 should be 3", 3, shifted.getLowerBound(), 0.00000001d);
    	assertEquals("0 should be 3", 3, shifted.getUpperBound(), 0.00000001d);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExpand_nullRange() {
    	Range.expand(null, 2, 2);
    }
    
    @Test
    public void testExpand_normalRange() {
    	Range xxpand = Range.expand(new Range(3, 5), 0.5, 0.5);
    	assertEquals("3 should be 2", 2, xxpand.getLowerBound(), 0.00000001d);
    	assertEquals("5 should be 6", 6, xxpand.getUpperBound(), 0.00000001d);
    }
    
    @Test
    public void testShift() {
    	Range shift = Range.shift(new Range(1, 4), 3);
    	assertEquals("1 should be 4", 4, shift.getLowerBound(), 0.00000001d);
    	assertEquals("4 should be 7", 7, shift.getUpperBound(), 0.00000001d);
    }
   
    @Test
    public void testCentralValue() {
    	assertEquals("Central value of (1, 3) should be 2", 2, new Range(1, 3).getCentralValue(), 0.00000001d);
    }
    
   
    @Test
    public void testIntersectsRange() {
    	assertTrue(new Range(1, 5).intersects(new Range(2, 7)));
    	assertFalse(new Range(4, 6).intersects(new Range(1, 3))); //added for mutation
    }
    
    @Test
    public void testIsNaNRange() {
    	assertTrue(nanRange.isNaNRange());
    	assertFalse(new Range(1, Double.NaN).isNaNRange());
    	assertFalse(new Range(Double.NaN, 1).isNaNRange());
    	assertFalse(new Range(1, 2).isNaNRange());
    }
   
    @Test
    public void testIntersects_Mutations() { //added for mutation testing
        Range range1 = new Range(2, 6);
        Range range2 = new Range(3, 8);
        Range range3 = new Range(1, 5);
        Range range4 = new Range(2, 7);
        
        assertTrue(range1.intersects(2, 4));  
        assertFalse(range2.intersects(2, 3));
        assertFalse(range3.intersects(5, 6));
        assertTrue(range4.intersects(3, 3));
    }
}
