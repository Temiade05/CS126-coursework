package structures;


public class SortedArrayList<E extends Comparable<E>> implements IList<E> {

    private Object[] array;
    private int size;
    private int capacity;
    
    public SortedArrayList() {
        this.capacity = 100;
        this.array = new Object[capacity];
        this.size = 0;
    }

    public boolean add(E element) {
        if (this.size == this.capacity) {
            resize();
        }
        this.array[size++] = element;
        for (int i = this.size()-2; i >= 0; i--) {
            // ascending order
            if (this.get(i).compareTo(this.get(i+1)) < 0) {
                break;
            }
            // swap elements at index i and i+1
            E temp = this.get(i);
            this.set(i, this.get(i+1));
            this.set(i+1, temp);
        }
        return true;
    }

    public boolean contains(E element) {
        return binarySearch(element, size, capacity) != -1;
    }

    

    public int binarySearch(E element, int low, int high) {
        
        if (low <= high) {
            int mid = low + (high - low) / 2;
            if (element.compareTo(this.get(mid)) == 0) {
                return mid;
            }

            if (element.compareTo(this.get(mid)) > 0) {
                return binarySearch(element, mid+1, high);
            }
            else {
                return binarySearch(element, low, mid-1);
            }
        }
        return -1;
    }

    public void clear() {
        this.capacity = 100;
        this.array = new Object[capacity];
        this.size = 0;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    // This line allows us to cast our object to type (E) without any warnings.
    // For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
    @SuppressWarnings("unchecked") 
    public E get(int index) {
        return (E) this.array[index];
    }
    
    public int indexOf(E element) {
        return binarySearch(element, size, capacity);
    }

    public boolean remove(E element) {
        int index = this.indexOf(element);
        if (index >= 0) {
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            this.array[size-1] = null;
            size--;
            return true;
        }
        return false;
    }

    public E set(int index, E element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        E replaced = this.get(index);
        this.array[index] = element;
        return replaced;
    }
    
    private void resize() {
        int newCapacity = array.length * 2; // Double the size
        Object[] newArray = new Object[newCapacity];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }


    public Object[] reverse() {
        Object[] tmp = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            tmp[this.size-i-1] = this.array[i];
        }
        return tmp;
    }
    
    
    
}
