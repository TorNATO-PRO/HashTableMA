/*
 *  Microassignment: Probing Hash Table addElement and removeElement
 *
 *  MA4_main: Main testing class for the hash table
 *   Tests inserts and deletes to evaluate implementation
 * 
 *  Contributors:
 *    Bolong Zeng <bzeng@wsu.edu>, 2018
 *    Aaron S. Crandall <acrandal@wsu.edu>, 2019
 * 
 *  Copyright:
 *   For academic use only under the Creative Commons
 *   Attribution-NonCommercial-NoDerivatives 4.0 International License
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 */

import java.util.Vector;

public class MA_HashTable_Add_Remove_main
{

	public static void main(String[] args) {
		small_demo();
		int testing_result = run_tests();

		if(testing_result > 0 ) {
			System.out.println("Some tests didn't pass.");
		}else{
			System.out.println("All tests passed - SUCCESS!.");
		}
		System.exit(testing_result);                    // 0 means all tests pass
	}

	// Suite of tests on our Hash Table implementation
	public static int run_tests() {
		System.out.println(" ----- Beginning Hash Table Tests ----- ");
		int return_code = 0;

		return_code |= test_isEmpty();
		return_code |= test_size_whenEmpty();
		return_code |= test_addElement();
		return_code |= test_removeElement();
		return_code |= test_add_rem_add();
		return_code |= test_remove_nonexistent();

		return return_code;
	}

	public static int test_isEmpty() {
		int return_code = 0;
		System.out.print("Test: isEmpty() interface -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		if( ht.isEmpty() == false ) {
			System.out.println(" FAIL");
			return_code = 1;	// Error, why isn't it empty?
		} else {
			System.out.println(" PASS");
		}
		return return_code;
	}

	public static int test_size_whenEmpty() {
		int return_code = 0;
		System.out.print("Test: size() when empty interface -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		if( ht.size() != 0 ) {
			System.out.println(" FAIL");
			return_code = 1;	// Error, why isn't it empty?
		} else {
			System.out.println(" PASS");
		}
		return return_code;
	}

	public static int test_addElement() {
		int return_code = 0;
		System.out.println("Test: addElement() interface -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		ht.addElement("KeyOne", "Element");
		ht.addElement("KeyTwo", "More Data");
		ht.addElement("AnotherKey", "Yet More Data");
		ht.printOut();

		System.out.print("  Should not be an empty table  -- ");
		if( ht.isEmpty() == true ) {
			System.out.println(" FAIL");
			return_code++;	// Error, why isn't it empty?
		} else {
			System.out.println(" PASS");
		}
		System.out.print("  Should have 3 in table  -- ");
		if( ht.size() != 3 ) {
			System.out.println(" FAIL");
			return_code++;	// Error, why isn't it empty?
		} else {
			System.out.println(" PASS");
		}

		System.out.println(" Doing deep data structure tests after add ");
		Vector<HashItem<String, String>> items = ht.getItems();
		return_code |= test_items_status(items, 4, "KeyOne", "Element", false);
		return_code |= test_items_status(items, 6, "KeyTwo", "More Data", false);
		return_code |= test_items_status(items, 7, "AnotherKey", "Yet More Data", false);

		return return_code;
	}

	public static int test_removeElement() {
		int return_code = 0;
		System.out.println("Test: removeElement() interface -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		ht.addElement("KeyOne", "Element");
		ht.addElement("KeyTwo", "More Data 1");
		ht.addElement("KeyTwoM", "More Data 2");
		ht.addElement("KeyTwoMM", "More Data 3");
		ht.addElement("KeyTwoMMM", "More Data 4");
		ht.addElement("AnotherKey", "Yet More Data");
		ht.removeElement("KeyOne");			// Finds at hash key location
		ht.removeElement("KeyTwoM");        // Takes one hop to find
		ht.removeElement("AnotherKey");		// Needs a few hops to locate properly - over KeyTwoM's delete
		ht.printOut();
		
		System.out.println(" Doing deep data structure tests after remove");
		Vector<HashItem<String, String>> items = ht.getItems();
		return_code |= test_items_status(items, 4, "KeyOne", "Element", true);
		return_code |= test_items_status(items, 6, "KeyTwo", "More Data 1", false);
		return_code |= test_items_status(items, 7, "KeyTwoM", "More Data 2", true);
		return_code |= test_items_status(items, 10, "AnotherKey", "Yet More Data", true);
		return_code |= test_items_status(items, 0, null, null, true);

		return return_code;
	}

	public static int test_add_rem_add() {
		int return_code = 0;
		System.out.println("Test: Add, Remove, Add same key with value update -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		ht.addElement("KeyTwo", "More Data 1");
		ht.addElement("AnotherKey", "Yet More Data");
		ht.removeElement("AnotherKey");
		ht.addElement("AnotherKey", "Updated Data");
		Vector<HashItem<String, String>> items = ht.getItems();
		return_code |= test_items_status(items, 7, "AnotherKey", "Updated Data", false);
		ht.printOut();

		return return_code;
	}

	public static int test_remove_nonexistent() {
		int return_code = 0;
		System.out.println("Test: Add, remove non-existent key -- ");
		LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
		ht.addElement("KeyTwo", "More Data 1");
		ht.addElement("AnotherKey", "Yet More Data");
		ht.removeElement("NonExistentKey");
		Vector<HashItem<String, String>> items = ht.getItems();
		return_code |= test_items_status(items, 6, "KeyTwo", "More Data 1", false);
		return_code |= test_items_status(items, 7, "AnotherKey", "Yet More Data", false);
		ht.printOut();

		return return_code;
	}

	public static int test_items_status(Vector<HashItem<String, String>> items, int bucket, String key, String value, boolean status) {
		int return_code = 0;
		System.out.print("  Testing items[" + bucket + "] for key: " + key + " | val: " + value + " | deleted? " + status + " -- ");
		HashItem<String, String> slot = items.elementAt(bucket);
		if( slot.getKey() != key || slot.getValue() != value || slot.isEmpty() != status ) {
			return_code++;
			System.out.println("FAIL");
		} else {
			System.out.println("PASS");
		}

		return return_code;
	}


	// Small demo of hash table operating
    public static void small_demo()
    {
		System.out.println(" ----- Small Demo of Table Operating ----- ");
        LinearHashTable<String, String> ht = new LinearHashTable<>(new SimpleStringHasher());
        ht.addElement("I", "Love");
    	ht.addElement("CptS", "233");
    	ht.addElement("And", "I");
    	ht.addElement("especially", "love");
		ht.addElement("Hashtables", "!");

		ht.printOut();				// Dump the table out
    }
}