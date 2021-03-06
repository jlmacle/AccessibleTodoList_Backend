package jl.forthem.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ItemTest {
	private static Item item;
	private static Item item_equal_Compare_0;
	private static Item item_nonEqual_Compare_0;
	private static Item item_nonEqual_Compare_non_0;
	private static Item item_nonEqual_Compare_0_2;
	private static Item item_nonEqual_Compare_non_0_2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		item =  new Item(1,"An item",1 );
		item_equal_Compare_0 =  new Item(1,"An item",1 );
		item_nonEqual_Compare_0 =  new Item(2,"An item",1 );
		item_nonEqual_Compare_non_0 =  new Item(1,"Bn An other item",1 );
		item_nonEqual_Compare_0_2 =  new Item(1,"An item",2 );
		item_nonEqual_Compare_non_0_2 = new Item(1,"Cn An other item",1 );
	}

	
	@Test
	void equalsTest_Identical_Items() {
		assertTrue(item.equals(item_equal_Compare_0));
	}
	
	@Test
	void equalsTest_Different_Items_1()
	{
		assertFalse(item.equals(item_nonEqual_Compare_0));
	}
	
	@Test
	void equalsTest_Different_Items_2()
	{
		assertFalse(item.equals(item_nonEqual_Compare_non_0));
	}
	
	@Test
	void equalsTest_Different_Items_3()
	{
		assertFalse(item.equals(item_nonEqual_Compare_0_2));
	}
	
	// https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html
	// Requirement 1 for compareTo 
	// The implementor must ensure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.
	// The overriden method is based on the alphabetical order. 
	// This requirement known to be fulfilled for the alphabetical order is assumed.
	
	@Test
	void compareToTest_Requirement1()
	{	
		//Comparing items with different names
		int comparison1 = item.compareTo(item_nonEqual_Compare_non_0);
		int comparison2 = item_nonEqual_Compare_non_0.compareTo(item);
		boolean test = ((comparison1*comparison2) <0);
		assertTrue(test);
	}
	
	//  Requirement 2 for compareTo: transistive relationship
	//  (x.compareTo(y)>0 && y.compareTo(z)>0) implies x.compareTo(z)>0.
	@Test
	void compareTest_Requirement2() 
	{
		int x_to_y = item.compareTo(item_nonEqual_Compare_non_0);
		int y_to_z = item_nonEqual_Compare_non_0.compareTo(item_nonEqual_Compare_non_0_2);
		int x_to_z = item.compareTo(item_nonEqual_Compare_non_0_2);
		
		if ((x_to_y * y_to_z) >0 )
		{
			assertTrue((x_to_y * x_to_z) >0);
		}
		else 
		{fail("The test of transitivity failed. x_to_y and y_to_z should have been of the same sign ");}
		
	}
	// Requirement 3 for compareTo	
	// x.compareTo(y)==0 implies that sgn(x.compareTo(z)) == sgn(y.compareTo(z)), for all z.
	@Test
	void compareTest_Requirement3()
	{
		assertEquals(0, item.compareTo(item_equal_Compare_0));
		int x_to_z = item.compareTo(item_nonEqual_Compare_non_0_2);
		int y_to_z = item_equal_Compare_0.compareTo(item_nonEqual_Compare_non_0_2);
		assertTrue((x_to_z * y_to_z) > 0);
	}
	
	@Test
	void compareToTest_Same()
	{
		assertEquals(0,item.compareTo(item_equal_Compare_0));
	}
	
	@Test
	void compareToTest_Same2()
	{	// same name. should return true.
		assertEquals(0,item.compareTo(item_nonEqual_Compare_0));
	}
	
	/*
	 * The general contract of hashCode is:
	 * 
	 * 1. Whenever it is invoked on the same object more than once 
	 * during an execution of a Java application, 
	 * the hashCode method must consistently return the same
	 * integer, provided no information used in equals comparisons on the object is
	 * modified. This integer need not remain consistent from one execution of an
	 * application to another execution of the same application. 
	 * 
	 * 2. If two objects are equal according to the equals(Object) method, then calling the hashCode
	 * method on each of the two objects must produce the same integer result. 
	 * 
	 * 3. It is not required that if two objects are unequal according to the
	 * equals(java.lang.Object) method, then calling the hashCode method on each of
	 * the two objects must produce distinct integer results. However, the
	 * programmer should be aware that producing distinct integer results for
	 * unequal objects may improve the performance of hash tables.
	 */
	
	@Test
	void hashTest_contract_1()
	{
		int hash1 = item.hashCode();
		int hash2 = item.hashCode();
		assertEquals(hash1, hash2);
	}
	
	@Test
	void hashTest_contract_2()
	{
		if(item.equals(item_equal_Compare_0))
		{
			assertEquals(item.hashCode(), item_equal_Compare_0.hashCode());
		}
		else fail("The two items were not identical.");
	}
	
	
	
}
