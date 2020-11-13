/*
 *  Microassignment: Probing Hash Table addElement and removeElement
 *
 *  HashTableBase: Base class for different kinds of Hash Tables
 *   Defines common features and API
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

/*
Considerations when implementing a Hash Table:
	* How do we convert the key into an integer value (the hash)?
	* What happens when we try to store a KVP in an already full slot (collision)?
	* What are appropriate sizes of the underlying vector?
	* How does performance change as our underlying vector fills up?
		* When is it necessary to resize?
			* How do we resize?
	
*/

import java.util.Vector;
abstract class HashTableBase<K, V> 
{
	// Note: Java uses 11 as the starting hashtable size.
	protected Vector<HashItem<K, V>> _items;
	protected static int[] _primes = { 11, 47, 97, 197, 379, 691, 1259, 2557, 5051, 7919, 14149, 28607, 52817, 102149, 209939, 461017, 855683, 1299827 };
	protected static int _primes_count = _primes.length;
	protected HasherBase<K> _hasher;
	
	// Keeps track of our position in our prime index counter
	//  Used when we rehash
	protected int _local_prime_index;
	protected int _number_of_elements;
	
	// Checks to see whether or not it's time to resize and rehashes
	protected abstract void resizeCheck();
	
	// Shortcut method for calling full hasher's getHash function
	//  This is a form of a wrapper or facade software design pattern
	//  See "Gang of Four" book on design patterns
	protected int getHash(K item)
	{
		return _hasher.getHash(item, _items.size());
	}

	public HashTableBase()
	{
		
	}

	public HashTableBase(HasherBase<K> hasher)
	{
		_hasher = hasher;
		_items = initItems(11);		
		_local_prime_index = 0;
		_number_of_elements = 0;
		
		while (_primes[_local_prime_index] < 11)
		{
			_local_prime_index ++;
		}
	}

	public HashTableBase(HasherBase<K> hasher, int number_of_elements)
	{
		_hasher = hasher;
		_items = initItems(number_of_elements); //new Vector<HashItem<K, V>>(number_of_elements);
		_local_prime_index = 0;
		_number_of_elements = 0;
		
		while (_primes[_local_prime_index] < number_of_elements)
		{
			_local_prime_index ++;
		}
	}

	// Init an _items vector
	protected Vector<HashItem<K, V>> initItems(int number_of_elements)
	{
		Vector<HashItem<K,V>> v_hash = new Vector<HashItem<K,V>>(number_of_elements);
		for (int i = 0; i < v_hash.capacity(); i ++)
		{
			v_hash.add(new HashItem<K, V>());
		}
		return v_hash;
	}

	// Copy constructor
	public HashTableBase(HashTableBase<K, V> other)
	{
		_hasher = other._hasher;
		_items = other._items;			// This is *BADNESS* can you see why?
		_local_prime_index = other._local_prime_index;
		_number_of_elements = other._number_of_elements;
	}

	// Determines whether or not a given key exists in the hash table
	public boolean hasKey(K key)
	{
		try
		{
			V result = getElement(key);
			return true;
		}
		catch (Exception e)
		{
			//e.printStackTrace();	// Uncomment for deep debugging issues
			return false;
		}
	}

	// Returns all keys present in the hashtable
	//  Class Question: How can we make this more efficient?
	public Vector<K> getKeys()
	{
		Vector<K> keys = new Vector<>();
		
		// Iteration through _items vector
		for (HashItem<K, V> item: _items)
		{
			if (item.isEmpty() == false)
			{
				keys.add(item.getKey());
			}
		}
		return keys;
	}
	
	public Vector<HashItem<K, V>> getItems()
	{
		return _items;
	}
	
	// TODO: Implement remove function based on desired collision mechanism
	public abstract void removeElement(K key);

	// TODO: Implement contains check based on desired collision mechanism
	public abstract boolean containsElement(K key);

	// TODO: Implement getElement method based on desired collision mechanism
	public abstract V getElement(K key);

	// TODO: Implement add function based on desired collision mechanism
	public abstract void addElement(K key, V value); 
}
