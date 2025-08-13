package structures;


public interface IPriorityQueue<K extends Comparable<K>, V>{
    int size();
    boolean isEmpty();
    KeyValuePair<K, V> max();
    KeyValuePair<K, V> removeMax();
    KeyValuePair<K, V> removeMin();

    void insert(K key, V value)
    throws IllegalArgumentException;
}