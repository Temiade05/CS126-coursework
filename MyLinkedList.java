package structures;

/**
* A generic implementation of the IList iterface, that uses LinkElements.
*/



public class MyLinkedList<E> implements IList<E> {
    
    MyLinkedListElement<E> head;
    private int count;
    
    public MyLinkedList() {
        this.head = null;
        this.count = 0;

    }
    
    /**
    * Checks if the list is empty by checking that the head is null.
    * @return true if the list has no elements, and false otherwise.
    */
    public boolean isEmpty() {
        // Returns whether the list is empty.
        return head==null;
    }
    
    /**
    * Adds an element to the of the linked list.
    * @param element the element to be added
    * @return true after the element is successfully added
    */
    public boolean add(E element) {
        // Adds an element to the head of the list.
        MyLinkedListElement<E> temp = new MyLinkedListElement<>(element);
        
        // if the list is not empty, point the new link to head
        if (head != null) {
            temp.setNext(head);
        }
        // update the head and increment the size
        head = temp;
        this.count++;
        
        return true;
    }
    
    /**
    * @return the number of elements in the list
    */
    public int size() {
        // Returns the number of elements in stored in this list.
        return count;
    }
    
    /**
    * Returns a string representation of the linked list.
    * The string is represented as a list of elements enclosed in square brackets.
    * @return a string representation of the list
    */
    public String toString() {
        // Returns a string representation of this list.
        MyLinkedListElement<E> ptr = head;
        // Way for the string to be formulated.
        String str = "[";
        while (ptr != null){
            str += ptr.getValue();
            if (ptr.getNext() != null){
                str += ", ";
            }
            ptr = ptr.getNext();
        }
        str += "]";
        return str;

    }
    
    /**
    * Adds an element to the tail of the linked list.
    * @param element the element to be added
    * @return true after the element is successfully added
    */
    public boolean addToTail(E element) {
        // Adds element to tail of the list
        MyLinkedListElement<E> ptr = head;
        MyLinkedListElement<E> temp = new MyLinkedListElement<E>(element);

        if (isEmpty()){
            head = temp;
        } else {
            while (ptr.getNext() != null) {
                ptr = ptr.getNext();
            }
            ptr.setNext(temp);
        }
        //increment count
        count ++;
        return true;
    }
    
    /**
    * Removes and returns the head element of the linked list.
    * @return the value of the removed head element, or null if the list is empty
    */
    public E removeFromHead() {
        // Removes and returns the head element
        MyLinkedListElement<E> temp = head;
        if (isEmpty()){
            return null;
        }
        head = head.getNext();
        //decrement count
        count--;
        return temp.getValue();
    }
    
    
    
    public E get(int index) {
        if (isEmpty() || index >= size()) {
            return null;
        }
        // Gets the element at index in the list
        MyLinkedListElement<E> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.getNext();
        }
        return ptr.getValue();
    }

    public int indexOf(E element) {
        // Gets the index of element in the list
        MyLinkedListElement<E> ptr = head;
        int i=0;
        while (ptr != null) {
            if (element.equals(ptr.getValue())) {
                return i;
            }
            i++;
            ptr = ptr.getNext();
        }
        return -1;
    }

    public E set(int index, E element) {
        if (isEmpty()) {
            return null;
        }

        // Sets element at index in the list
        MyLinkedListElement<E> ptr = head;
        MyLinkedListElement<E> prev = null;

        for (int i = 0; i < index; i++) {
            prev = ptr;
            ptr = ptr.getNext();
        }

        E ret = ptr.getValue();

        MyLinkedListElement<E> newLink = new MyLinkedListElement<>(element);
        newLink.setNext(ptr.getNext());
        if (prev != null) {
            prev.setNext(newLink);
        } else {
            head = newLink;
        }

        return ret;
    }
    
    public void clear() {
        // Clears the list
        head = null;
        this.count = 0;

    }
    
    public boolean contains(E element) {
        // Returns whether the element exists in the list
        return indexOf(element) != -1;
    }

    public boolean remove(E element) {
        MyLinkedListElement<E> ptr = head;
        MyLinkedListElement<E> prev = null;

        while (ptr != null) {
            if (ptr.getValue().equals(element)) {
                if (prev == null) {
                    head = ptr.getNext();
                } else {
                    prev.setNext(ptr.getNext());
                }
                this.count--;
                return true;
            }

            prev = ptr;
            ptr = ptr.getNext();
        }

        return false;
    }
}
