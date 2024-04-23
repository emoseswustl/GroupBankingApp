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
		user.addAsset(new PersonalCapital(true, 1000, user, 1));
		user.addLiability(new PersonalCapital(false, 500, user, 1));
	}

	@Test
	public void testListCreation() {
		assertNotNull(user.getLiabilityList());
		assertNotNull(user.getAssetList());
	}

	@Test
	public void testGetTotalLiquidValue() {
		assertEquals(1000, user.getAssets().getTotalLiquidValue());
		assertEquals(-500, user.getLiabilities().getTotalLiquidValue(user));
	}

	@Test
	public void testGetTotalNumberofAssets() {
		assertEquals(1, user.getAssetList().size());
		assertEquals(1, user.getLiabilityList().size());
	}

}