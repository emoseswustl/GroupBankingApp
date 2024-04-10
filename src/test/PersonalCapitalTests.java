package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import bankapp.PersonalCapital;
import bankapp.Assets;
import bankapp.Liabilities;

public class PersonalCapitalTests {

	@Test 
	public void testGetLiquidValueAssets() {
		PersonalCapital pc = new PersonalCapital(true, 100.0); 
		double expected = 100.0; 
		double actual = pc.getLiquidValue(pc); 
		assertEquals(expected, actual); 
	}
	
	@Test
	public void testGetLiquidValueLiabilties() {
		PersonalCapital pc = new PersonalCapital(false, 100.0); 
		double expected = -100.0; 
		double actual = pc.getLiquidValue(pc); 
		assertEquals(expected, actual); 
	}

	@Test
	public void testGetToalLiquidValueAssets() {
		LinkedList<PersonalCapital>list = new LinkedList<>(); 
		list.add(new PersonalCapital(true, 509.0));
		list.add(new PersonalCapital(true, 350.0));
		list.add(new PersonalCapital(true, 2.56));
		PersonalCapital pc = new PersonalCapital(true, 0);
		double actual = pc.getTotalLiquidValue(list);
		double expected = 509.0 + 350.0 + 2.56; 
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTotalLiquidValueLiabilites() {
		LinkedList<PersonalCapital>list = new LinkedList<>(); 
		list.add(new PersonalCapital(false, 509.0));
		list.add(new PersonalCapital(false, 350.0));
		list.add(new PersonalCapital(false, 2.56));
		PersonalCapital pc = new PersonalCapital(false, 0);
		double actual = pc.getTotalLiquidValue(list);
		double expected = (509.0 + 350.0 + 2.56) * -1; 
		//System.out.println("Expected: " + expected);
		//System.out.println("Actual: " + actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAddItem() {
		LinkedList<PersonalCapital>list = new LinkedList<>(); 
		PersonalCapital a = new PersonalCapital(true, 100.0);
		PersonalCapital l = new PersonalCapital(false, 200.0);
		
		PersonalCapital pc = new PersonalCapital(true, 0.0);
		assertTrue(pc.addItem(a, list));
		assertTrue(pc.addItem(l, list));
		assertEquals(2, list.size());
		
	}
	@Test
	public void testRemoveItem() {
		LinkedList<PersonalCapital>list = new LinkedList<>(); 
		PersonalCapital a = new PersonalCapital(true, 100.0);
		PersonalCapital l = new PersonalCapital(false, 200.0);
		PersonalCapital rand = new PersonalCapital(false, 200.0);
		PersonalCapital pc = new PersonalCapital(true, 0.0);
		pc.addItem(a, list);
		pc.addItem(l, list);
		pc.addItem(rand, list);
		
		assertTrue(pc.removeItem(a, list));
		assertTrue(pc.removeItem(l, list));
		assertEquals(1, list.size());
		
	}
	

	
	
}
