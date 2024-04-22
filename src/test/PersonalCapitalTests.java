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
	        user.assetList.assets.add(new PersonalCapital(true, 1000));
	        user.liabilityList.liabilities.add(new PersonalCapital(false, 500));
	    }

	    @Test
	    public void testListCreation() {
	        assertNotNull(user.liabilityList.liabilities);
	        assertNotNull(user.assetList.assets);
	    }
	    
	    @Test 
	    public void testGetTotalLiquidValue() {
	    	assertEquals(1000, user.assetList.getTotalLiquidValue(user));
	    	assertEquals(-500, user.liabilityList.getTotalLiquidValue(user));
	    }
	    @Test
	    public void testGetTotalNumberofAssets() {
	        assertEquals(1, user.assetList.getTotalNumberofAssets());
	        assertEquals(1, user.liabilityList.getTotalNumberofLiabilities());
	    }

}