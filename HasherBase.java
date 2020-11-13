/*
 *  Microassignment: Probing Hash Table addElement and removeElement
 *
 *  HasherBase: Base class for Hash Functions
 *   Provides a class that can be overriden to do different hash algorithms
 *   Structured to do a form of passed in function handling
 *   Designed to make the implementation of hash algorithms to be more dynamic
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


// A base class to provide a general interface for all hash functions
// To use this, you build your own Hashing Class that inherits from HasherBase
// Then, you implement your own getHash and then pass your object to the HashTable class
// This is a form of treating the Hasher as a first class function:
//  https://en.wikipedia.org/wiki/First-class_function
// I would consider this the "Strategy" design pattern from the Gang of Four book:
//  https://springframework.guru/gang-of-four-design-patterns/strategy-pattern/
abstract class HasherBase<T>
{
	public abstract int getHash(T item, int mod_by);
}

// Notes:
//
//HasherBase<String> hb = new HasherBase<>();
//All the subclasses of the abstract class, has to implement its defined abstract functions/operations
//
//Define many other Hashers based upon this base: 
// IntegerHasher
// StringHasher
// DoubleHasher
// MyOwnClassHasher