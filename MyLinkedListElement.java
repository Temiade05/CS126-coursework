package structures;

public class MyLinkedListElement<E> {

    private E value;
    private MyLinkedListElement<E> next_elem;

    public MyLinkedListElement(E val) {
        value = val;
        next_elem = null;
    }

    public MyLinkedListElement<E> getNext() {
        return next_elem;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E val) {
        value = val;
    }

    public void setNext(MyLinkedListElement<E> next) {
        this.next_elem = next;
    }
    
    public String toString() {
        return ""+value;
    }
}
