package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import bankapp.PersonalCapital;
import bankapp.BalanceSheet;
import bankapp.Assets;
import bankapp.Liabilities;
import bankapp.User;

/**
 * @author ethanmoses
 *
 */
class BalanceSheetAssetsLiabilities {
	private User hello;
	private LinkedList<PersonalCapital> accounts;
	private double expectedBalance = 50;

	@BeforeEach
	void setUp() {
		User hello = new User("hi", "helloworld");
	}
	
	@AfterEach
	void clear() {
		accounts.clear();
	}
	
	LinkedList<PersonalCapital> createPC(boolean isAsset, User owner) {
		LinkedList<PersonalCapital> accounts = new LinkedList<PersonalCapital>();
		for (int i = 0; i < 500; i++) {
			accounts.add(new PersonalCapital(isAsset, 50.0, owner, i));
		}
		return accounts;
	}
	
	void addAcctsToBalanceSheet(BalanceSheet writeSheet) {
		for (PersonalCapital account: accounts) {
			writeSheet.addAccount(account);
		}
	}
	
	Assets createAssets() {
		accounts = createPC(true, hello);
		Assets assetList = new Assets(hello);
		addAcctsToBalanceSheet(assetList);
		return assetList;
	}

	@Test
	void addGetAssetsList() {
		Assets assetList = createAssets();
		LinkedList<PersonalCapital> retrieveAccts = assetList.getAccounts();
		for (int i = 0; i < retrieveAccts.size(); i++) {
			assertEquals(retrieveAccts.get(i), accounts.get(i));
		}
	}
	
	@Test
	void getAssetsWorth() {
		Assets assetList = createAssets();
		assertEquals(assetList.getTotalLiquidValue(), expectedBalance*accounts.size(), 0.001);
	}
	
	@Test
	void getAssetsNumber() {
		Assets assetList = createAssets();
		assertEquals(assetList.getTotalNumberofAssets(), accounts.size(), 0.001);
	}
	
	@Test
	void getAssetsStatus() {
		Assets assetList = createAssets();
		assertTrue(assetList.hasAssets());
	}
	
	@Test
	void removeAsset() {
		Assets assetList = createAssets();
		PersonalCapital toRemove = assetList.get(0);
		assertTrue(assetList.removeAccount(toRemove));
		assertNotEquals(assetList.get(0), toRemove);
		assertEquals(assetList.getTotalNumberofAssets(), accounts.size()-1);
	}
	
	Liabilities createLiabilities() {
		accounts = createPC(false, hello);
		Liabilities liabilityList = new Liabilities(hello);
		addAcctsToBalanceSheet(liabilityList);
		return liabilityList;
	}
	
	@Test
	void addGetLiabilitiesList() {
		Liabilities liabilityList = createLiabilities();
		LinkedList<PersonalCapital> retrieveAccts = liabilityList.getAccounts();
		for (int i = 0; i < retrieveAccts.size(); i++) {
			assertEquals(retrieveAccts.get(i), accounts.get(i));
		}
	}
	
	@Test
	void getLiabilitiesWorth() {
		Liabilities liabilityList = createLiabilities();
		assertEquals(liabilityList.getTotalLiquidValue(), -expectedBalance*accounts.size(), 0.001);
	}
	
	@Test
	void getLiabilitiesNumber() {
		Liabilities liabilityList = createLiabilities();
		assertEquals(liabilityList.getTotalNumberofLiabilities(), accounts.size(), 0.001);
	}
	
	@Test
	void getLiabilitiesStatus() {
		Liabilities liabilityList = createLiabilities();
		assertFalse(liabilityList.hasAssets());
	}
	
	@Test
	void removeLiability() {
		Liabilities liabilityList = createLiabilities();
		PersonalCapital toRemove = liabilityList.get(0);
		assertTrue(liabilityList.removeAccount(toRemove));
		assertNotEquals(liabilityList.get(0), toRemove);
		assertEquals(liabilityList.getTotalNumberofLiabilities(), accounts.size()-1);
	}

}
