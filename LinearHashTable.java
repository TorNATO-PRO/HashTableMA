/*
 *  Microassignment: Probing Hash Table addElement and removeElement
 *
 *  LinearHashTable: Yet another Hash Table Implementation
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


class LinearHashTable<K, V> extends HashTableBase<K, V> {
    // Linear and Quadratic probing should rehash at a load factor of 0.5 or higher
    private static final double REHASH_LOAD_FACTOR = 0.5;

    // Constructors
    public LinearHashTable() {
        super();
    }

    public LinearHashTable(HasherBase<K> hasher) {
        super(hasher);
    }

    public LinearHashTable(HasherBase<K> hasher, int number_of_elements) {
        super(hasher, number_of_elements);
    }

    // Copy constructor
    public LinearHashTable(LinearHashTable<K, V> other) {
        super(other);
    }


    // ***** MA Section Start ************************************************ //

    /**
     * @param key - A client provided key
     * @param value - A client provided value corresponding to the key
     *
     * Concrete implementation for parent's addElement method
     */
    public void addElement(K key, V value) {
        // Check for size restrictions
        resizeCheck();

        // Calculate hash based on key
        int hash = super.getHash(key);
        HashItem<K, V> slot = _items.elementAt(hash);

        // iteration marker
        int step = 0;
        // iterating until a viable index is found
        while (!slot.isEmpty() && slot.getKey() != key) {
            if (step == size()) {
                return; // element doesn't exist? returns
            }

            // increments intelligently
            // gets HashItem
            // increments step
            hash = (hash + 1) % _items.size();
            slot = _items.elementAt(hash);
            step++;
        }

        // Sets the value of the HashItem and addresses it as non-empty
        slot.setValue(value);
        slot.setKey(key);
        slot.setIsEmpty(false);
        _number_of_elements++;
    }

    /**
     * @param key - A client provided key
     *
     * Removes the supplied key from hash table using lazy deletion
     * (setting it as empty but not actually deleting it)
     */
    public void removeElement(K key) {

        // Calculate hash from key
        int hash = super.getHash(key);
        HashItem<K, V> slot = _items.elementAt(hash);

        // iteration marker
        int step = 0;
        while (slot.getKey() != key) {
            if (step == size() || slot.isTrueEmpty()) {
                // interesting thing is that it dead stops
                // if it comes on a "Truly" empty index
                return; // element does not exist, returns
            }

            // increments intelligently, gets HashItem, increments step
            hash = (hash + 1) % _items.size();
            slot = _items.elementAt(hash);
            step++;
        }

        // performs remove operation, avoids doing so if it is already empty
        // to preserve HashItem vector size
        if (!slot.isEmpty()) {
            slot.setIsEmpty(true);
            _number_of_elements--;
        }
    }

    // ***** MA Section End ************************************************ //


    // Public API to get current number of elements in Hash Table
    public int size() {
        return this._number_of_elements;
    }

    // Public API to test whether the Hash Table is empty (N == 0)
    public boolean isEmpty() {
        return this._number_of_elements == 0;
    }

    /**
     * @param key - A client provided key
     *
     * @return - true if the key is contained in the hashtable
     */
    public boolean containsElement(K key) {
        int hash = super.getHash(key);
        HashItem<K, V> slot = _items.elementAt(hash);

        int step = 0;
        while (!slot.isTrueEmpty() && step != size()) {
            if (slot.getKey() == key) {
                return true;
            }

            // increments hash intelligently,
            // gets the HashItem at that index
            // increments step
            hash = (hash + 1) % _items.size();
            slot = _items.elementAt(hash);
            step++;
        }
        // Left incomplete to avoid hints in the MA :)
        return false;
    }

    /**
     * @param key - A client provided key
     *
     * @return - item pointed to by the key
     */
    public V getElement(K key) {
        int hash = super.getHash(key);
        HashItem<K, V> slot = _items.elementAt(hash);

        // Left incomplete to avoid hints in the MA :)
        return slot.getValue();
    }

    // Determines whether or not we need to resize
    //  to turn off resize, just always return false
    protected boolean needsResize() {
        // Linear probing seems to get worse after a load factor of about 50%
        return _number_of_elements > (REHASH_LOAD_FACTOR * _primes[_local_prime_index]);
    }

    // Called to do a resize as needed
    protected void resizeCheck() {
        // Right now, resize when load factor > 0.5; it might be worth it to experiment with 
        //  this value for different kinds of hashtables
        if (needsResize()) {
            _local_prime_index++;

            HasherBase<K> hasher = _hasher;
            LinearHashTable<K, V> new_hash = new LinearHashTable<K, V>(hasher, _primes[_local_prime_index]);

            for (HashItem<K, V> item : _items) {
                if (!item.isEmpty()) {
                    // Add element to new hash table
                    new_hash.addElement(item.getKey(), item.getValue());
                }
            }

            // Steal temp hash object's internal vector for ourselves
            _items = new_hash._items;
        }
    }

    // Debugging tool to print out the entire contents of the hash table
    public void printOut() {
        System.out.println(" Dumping hash with " + _number_of_elements + " items in " + _items.size() + " buckets");
        System.out.println("[X] Key	| Value	| Deleted");
        for (int i = 0; i < _items.size(); i++) {
            HashItem<K, V> curr_slot = _items.get(i);
            System.out.print("[" + i + "] ");
            System.out.println(curr_slot.getKey() + " | " + curr_slot.getValue() + " | " + curr_slot.isEmpty());
        }
    }
}