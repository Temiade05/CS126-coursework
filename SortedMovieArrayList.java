package structures;

public class SortedMovieArrayList {

    private MoviesObject[] array;
    private int size;
    private int capacity;
    
    public SortedMovieArrayList() {
        this.capacity = 100;
        this.array = new MoviesObject[capacity];
        this.size = 0;
    }
    
    public boolean add(MoviesObject element) {
        if (size == this.array.length) {
            resize(capacity*2);
        }
        this.array[size++] = element;
        for (int i = this.size()-2; i >= 0; i--) {
            if (this.get(i).compareTo(this.get(i+1)) < 0) {
                break;
            }
            MoviesObject temp = this.get(i);
            this.set(i, this.get(i+1));
            this.set(i+1, temp);
        }
        return true;
    }


    public boolean contains(MoviesObject element) {
        return binarySearch(element, size, capacity) != -1;
    }

    public boolean contains(int key) {
        return this.getByID(key) == null;
    }

    public int binarySearch(MoviesObject element, int low, int high) {
    
        if (low <= high) {
            int mid = low + (high - low) / 2;
            if (element.compareTo(this.array[mid]) == 0) {
                do {
                    if (element.getID() == (this.array[mid]).getID()) {
                        return mid;
                    }
                    mid++;
                } while (element.compareTo(this.array[mid]) == 0);
                return mid;
            }

            if (element.compareTo(this.array[mid]) > 0) {
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
        this.array = new MoviesObject[capacity];
        this.size = 0;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    public MoviesObject get(int index) {
        return this.array[index];
    }

    public MoviesObject getByID(int key) {
        for (int i = 0; i < size(); i++) {
            if (array[i].getID() == key) {
                return array[i];
            }
        }
        return null;
    }
    
    public int indexOf(MoviesObject element) {
        return binarySearch(element, size, capacity);
        
    }

    public int indexByID(int key) {
        for (int i = 0; i < size; i++) {
            if (array[i].getID() == key) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeID(int key) {
        int index = indexByID(key);

        if (index >= 0) {
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            this.array[size-1] = null;
            this.size--;
            return true;
        }
        return false;
    
    }

    public MoviesObject set(int index, MoviesObject element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        MoviesObject replaced = this.get(index);
        this.array[index] = element;
        return replaced;
    }

    public boolean setByID(int key, MoviesObject element) {
        int index = indexByID(key);
        if (index >= 0) {
            this.array[index] = element;
            return true;
        }
        
        return false;
    }

    private void resize(int newCapacity) {
        newCapacity = array.length*2;
        MoviesObject[] newArray = new MoviesObject[newCapacity];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    
}