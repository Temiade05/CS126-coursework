package structures;

// This line allows us to cast our object to type (E) without any warnings.
// For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
@SuppressWarnings("unchecked") 
public class HashMap<K extends Comparable<K>,V> implements IMap<K,V> {

    protected KeyValuePairLinkedList[] table;

    public KeyValuePairLinkedList[] getTable() {
        return table;
    }
    
    public HashMap() {
        this(9973);
    }
    
    public HashMap(int size) {
        table = new KeyValuePairLinkedList[size];
        initTable();
    }

    /**
    * Finds the number of comparisons required to locate an element using Linear Search
    * in the hash table.
    * @param key The key to search for in the hash table.
    * @return The number of comparisons required to find the key, or 0 if the key isn't found
    */
    public int find(K key) {
        int hashKey = hash(key);
        int count = 1;
        //Determines index in hash table
        int location = Math.abs(hashKey % table.length);

        // If bucket at index is empty, return 0;
        if (table[location].size() == 0) {
            return 0;
        }
        //Gets the head of linked list at the index
        ListElement<KeyValuePair<K, V>> ptr = table[location].getHead();
        //Traverses linked list until element is found
        while (ptr != null && ptr.getValue().getKey().compareTo(key) != 0) {
            count ++;
            ptr = ptr.getNext();
        }
        return count;
    }
    
    protected void initTable() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }
    
    protected int hash(K key) {
        int code = key.hashCode();
        return code;    
    }
    
    public void add(K key, V value) {
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);
        
        System.out.println("Adding " + value + " under key " + key + " at location " + location);
        
        table[location].add(key,value);
    }

    public V get(K key) {
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);
        
        KeyValuePair<K,V> ptr = table[location].get(key);
        
        if (ptr == null) {
            return null;
        }

        return (V) ptr.getValue();
    }

    public boolean containsKey(K key) {
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);
        
        return table[location].get(key) != null;
    }

    public boolean remove(K key) {
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);
        return table[location].remove(key);
    }

    public DynamicArrayList<K> getAllKeys() {
        DynamicArrayList<K> keys = new DynamicArrayList<>();
        for (KeyValuePairLinkedList<K, V> bucket : table) {
            if (bucket != null) {
                ListElement<KeyValuePair<K, V>> current = bucket.getHead();
                while (current != null) {
                    keys.add(current.getValue().getKey());
                    current = current.getNext();
                }
            }
        }
        return keys;
    }

    public DynamicArrayList<V> values() {
        DynamicArrayList<V> valuesList = new DynamicArrayList<>();
        for (KeyValuePairLinkedList<K, V> bucket : table) {
            if (bucket != null) {
                ListElement<KeyValuePair<K, V>> current = bucket.getHead();
                while (current != null) {
                    valuesList.add(current.getValue().getValue()); 
                    current = current.getNext();
                }
            }
        }
        return valuesList;
    }

    public boolean findRating(K key, int movieid) {
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);

        //temporary value to store current element of the linked list we are comparing with, as it iterates through
        ListElement<KeyValuePair<K,MoviesObject>> element = table[location].getHead();
        
        // iterates through linked list to find position where desired rating is 
        while(element != null) {
            if((element.getValue().getValue().getID() == movieid) & (element.getValue().getKey().equals(key))) {
                return true;
            }
            element = element.getNext();
        }
        return false;
    }

    public boolean removeRating(K key, int movieid){
        int hash_code = hash(key);
        int location = Math.abs(hash_code % table.length);
        
        //temporary value to store current element of the linked list we are comparing with, as it iterates through
        ListElement<KeyValuePair<K,MoviesObject>> element = table[location].getHead();
        while(element != null) {
            // checking if current element is storing a rating object with matching userid and movieid
            if((element.getValue().getValue().getID() == movieid) & (element.getValue().getKey().equals(key))) {
                if (element.getPrev() == null) {
                        table[location].head = element.getNext();
                    } else {
                        element.getPrev().setNext(element.getNext());
                    }
                return true;
            }
            element = element.getNext();
        }
        return false;
    }
}