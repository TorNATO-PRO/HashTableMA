/*
 *  Microassignment: Probing Hash Table addElement and removeElement
 *
 *  SimpleStringHasher: Provides an object able to hash strings
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


class SimpleStringHasher extends HasherBase<String>
{
    public int getHash(String key, int tableSize)
    {
        int hash = 0;
        for (char ch: key.toCharArray())
        {
            hash += ch;
        }
        return hash % tableSize;
    }
}