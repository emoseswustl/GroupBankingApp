package bankapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.HashMap;


public class FileStorage {
	String fileName;
	
	/**
	 * Generates a new FileStorage object.
	 * @param fileName Name of the file that objects should be stored in
	 */
	public FileStorage(String fileName) {
		this.fileName = fileName;	
	}
	
	/**
	 * Gets or creates a new file from the fileName instance variable; does not overwrite existing one.
	 * @return Resulting file with correct file name and .ser extension.
	 */
	public File getFile() {
		File dataStore = new File(fileName + ".ser");
		try {
			dataStore.createNewFile();
			return dataStore;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Writes a map to the file generated from the instance variable.
	 * @param toSave The map that should be saved.
	 * @return Whether the map was written to storage successfully.
	 */
	public boolean writeMap(Map<?,?> toSave) {
		try {
			FileOutputStream output = new FileOutputStream(getFile(), false);
			ObjectOutputStream writer = new ObjectOutputStream(output);
			writer.writeObject(toSave);
			writer.close();
			output.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Read a map from the file determined by the instance variable.
	 * @return Null if a map doesn't exist in the file, and a Map otherwise.
	 */
	public Map<?,?> readMap() {
		try {
			FileInputStream input = new FileInputStream(getFile());
			ObjectInputStream reader = new ObjectInputStream(input);
			Map<?,?> result = (Map<?, ?>) reader.readObject();
			reader.close();
			input.close();
			return result;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException c) {
			return null;
		}
	}
	
	/**
	 * Casts the result of readMap() to the format for User.
	 * @return A map linking strings to users.
	 */
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>) readMap();
	}

	/**
	 * Casts the result of readMap() to the format for Bank Account.
	 * @return A map linking integers to bank accounts.
	 */
	public HashMap<Integer, BankAccount> readBankAcctMap() {
		return (HashMap<Integer, BankAccount>) readMap();
	}
}