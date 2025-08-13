package structures;

public class KeyValuePairLinkedList<K extends Comparable<K>,V> {

    protected ListElement<KeyValuePair<K,V>> head;
    protected int size;
    
    public KeyValuePairLinkedList() {
        head = null;
        size = 0;
    }
    
    public void add(K key, V value) {
        this.add(new KeyValuePair<K,V>(key,value));
    }

    public void add(KeyValuePair<K,V> kvp) {
        ListElement<KeyValuePair<K,V>> new_element = 
                new ListElement<>(kvp);
        new_element.setNext(head);
        head = new_element;
        size++;
    }
    
    public int size() {
        return size;
    }
    
    public ListElement<KeyValuePair<K,V>> getHead() {
        return head;
    }
    
    public KeyValuePair<K,V> get(K key) {
        ListElement<KeyValuePair<K,V>> temp = head;
        
        while(temp != null) {
            if(temp.getValue().getKey().equals(key)) {
                return temp.getValue();
            }
            
            temp = temp.getNext();
        }
        
        return null;
    }

    public boolean remove(K key) {
        if (head == null) {
            return false; 
        }
    
        // If the head contains the key, remove it
        if (head.getValue().getKey().equals(key)) {
            head = head.getNext();
            return true;
        }
    
        // Traverse the list to find and remove the key
        ListElement<KeyValuePair<K, V>> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getValue().getKey().equals(key)) {
                current.setNext(current.getNext().getNext()); 
                return true;
            }
            current = current.getNext();
        }
    
        return false; 
    }
}
