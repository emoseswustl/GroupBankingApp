package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import bankapp.PersonalCapital;
import bankapp.User;
import bankapp.Assets;
import bankapp.Liabilities;

public class PersonalCapitalTests {
	
	    private User user; 
	    
	    @BeforeEach
	    public void setUp() {
	       user = new User("testuser", "password");
	        // Add some assets and liabilities for testing
	        user.a.assets.add(new PersonalCapital(true, 1000));
	        user.l.liabilities.add(new PersonalCapital(false, 500));
	    }

	    @Test
	    public void testListCreation() {
	        assertNotNull(user.l.liabilities);
	        assertNotNull(user.a.assets);
	    }
	    
	    @Test 
	    public void testGetTotalLiquidValue() {
	    	assertEquals(1000, user.a.getTotalLiquidValue(user));
	    	assertEquals(-500, user.l.getTotalLiquidValue(user));
	    }
	    @Test
	    public void testGetTotalNumberofAssets() {
	        assertEquals(1, user.a.getTotalNumberofAssets());
	        assertEquals(1, user.l.getTotalNumberofLiabilities());
	    }

}