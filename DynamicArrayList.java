package structures;

public class DynamicArrayList<T> {
    private Object[] array; 
    private int size; 

    // Constructor - initialize with default capacity
    public DynamicArrayList() {
        array = new Object[10]; 
        size = 0;
    }

    // Resize the array when it's full
    private void resize() {
        int newCapacity = array.length * 2; 
        Object[] newArray = new Object[newCapacity];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public boolean add(T element) {
        if (size == array.length) {
            resize();
        }
        array[size++] = element;
        return true;
    }

    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    public void clear() {
        this.size = 100;
        this.array = new Object[size];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

   
    public int size() {
        return size;
    }

    
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1; 
    }
    
    public boolean remove(T element) {
        int index = indexOf(element);
        if (index == -1) {
            return false; 
        }
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null; 
        return true;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array[index] = element;
    }

   
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    
    public void printList() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(array[i]);
            if (i < size - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size]; 
        for (int i = 0; i < size; i++) {
            result[i] = (T) array[i]; 
        }
        return result;
    }

    public int[] toIntArray() {
        int[] result = new int[size]; 
        for (int i = 0; i < size; i++) {
            result[i] = (Integer) array[i]; 
        }
        return result;
    }
 
}

