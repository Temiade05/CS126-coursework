package structures;


public class PriorityQueue<K extends Comparable<K>, V> implements IPriorityQueue<K, V> {
    protected ListElement<KeyValuePair<K,V>> head;
    protected int size;

    /**
     * Constructor for PriorityQueue object
     */
    public PriorityQueue() {
        head = null;
        size = 0;
    }

    @Override
    /**
     * Returns the number of elements in the queue
     * 
     * @return number of elements in the queue
     */
    public int size() {
        return size;
    }

    @Override
    /**
     * Returns boolean to say if the queue is empty
     * @return if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    /**
     * Inserts a value into the priority queue in the correct location
     */
    public void insert(K key, V value) throws IllegalArgumentException {
        // Creating a ListElement object for the new pair to be added
        ListElement<KeyValuePair<K, V>> newPair = new ListElement<>(new KeyValuePair<K, V>(key, value));

        // If the queue is empty we can set the head to be the element
        if (head == null) {
            head = newPair;
            head.setNext(null);
        } else {
            ListElement<KeyValuePair<K, V>> current = head;
            ListElement<KeyValuePair<K, V>> previous = null;
            while (current != null && newPair.getValue().getKey().compareTo(current.getValue().getKey()) > 0) {
                previous = current;
                current = current.getNext();
            }

            newPair.setNext(current);
            
            if (current == head) head = newPair;

            if (previous != null) previous.setNext(newPair);
        }
        
        size++;
    }

    /**
     * Returns ListElement in queue with the key K
     * @param key key being searched for in the queue
     * @return ListElement containing key value pair or null if pair couldn't be found
     */
    public ListElement<KeyValuePair<K, V>> get(K key) {
        ListElement<KeyValuePair<K, V>> current = head;
        // Iterates through queue until the key is found
        while (current != null) {
            if (current.getValue().getKey() == key) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }

    @Override
    /**
     * Returns the element in the queue with the highest key
     * @return Maximum element in the queue
     */
    public KeyValuePair<K, V> max() {
        return head.getValue();
    }

    /**
     * Checks if the queue contains a pair with key K
     * @param key key of element being looked for
     * @return true if the key exists in queue, false otherwise
     */
    public boolean contains(K key) {
        ListElement<KeyValuePair<K, V>> current = head;
        while (current != null) {
            if (current.getValue().getKey() == key) {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    @Override
    /**
     * Removes and returns the max element in the queue
     * @return Max value in the queue
     */
    public KeyValuePair<K, V> removeMax() {
        ListElement<KeyValuePair<K, V>> temp = head;
        head = head.getNext();

        size--;

        return temp.getValue();
    }

    @Override
    /**
     * Removes and returns the min element in the queue
     * @return Min value in the queue
     */
    public KeyValuePair<K, V> removeMin() {
        ListElement<KeyValuePair<K, V>> ptr = head;
        KeyValuePair<K, V> max;

        if (head == null) return null;
        
        // If there is only 1 element in the queue we can remove and return the head
        if (size() == 1) {
            head = null;
            max = ptr.getValue();
        } else {
            // Iterate through the priority queue until the next element is the final element
            while (ptr.getNext().getNext() != null)
                ptr = ptr.getNext();
                max = ptr.getNext().getValue();
                ptr.setNext(null);
            }

        size--;

        return max;
    }
}