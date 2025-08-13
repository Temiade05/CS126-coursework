package structures;

public class SortedRatingArrayList {

    private RatingsObject[] array;
    private int size;
    private int capacity;
    
    public SortedRatingArrayList() {
        this.capacity = 100;
        this.array = new RatingsObject[capacity];
        this.size = 0;
    }
    
    public boolean add(RatingsObject element) {
        if (size == this.array.length) {
            resize(capacity*2);
        }
        this.array[size++] = element;
        for (int i = this.size()-2; i >= 0; i--) {
            if (this.get(i).compareTo(this.get(i+1)) < 0) {
                break;
            }
            RatingsObject temp = this.get(i);
            this.set(i, this.get(i+1));
            this.set(i+1, temp);
        }
        return true;
    }


    public boolean contains(RatingsObject element) {
        return binarySearch(element, size, capacity) != -1;
    }

    public int binarySearch(RatingsObject element, int low, int high) {
    
        if (low <= high) {
            int mid = low + (high - low) / 2;
            if (element.compareTo(this.array[mid]) == 0) {
                do {
                    if (element.getUserID() == (this.array[mid]).getUserID() && element.getMovieID() == (this.array[mid]).getMovieID()) {
                        return mid;
                    }
                    mid++;
                } while (element.compareTo(this.array[mid]) == 0);
                mid --;
                do {
                    if (element.getUserID() == (this.array[mid]).getUserID() && element.getMovieID() == (this.array[mid]).getMovieID()) {
                        return mid;
                    }
                    mid++;
                    mid--;
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
        this.array = new RatingsObject[capacity];
        this.size = 0;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return size;
    }

    public RatingsObject get(int index) {
        return this.array[index];
    }

    public RatingsObject get(JointKey jKey) {
        for (int i = 0; i < size(); i++) {
            if (array[i].getUserID() == jKey.key1 && array[i].getMovieID() == jKey.key2) {
                return array[i];
            }
        }
        return null;
    }
    
    public int indexOf(RatingsObject element) {
        return binarySearch(element, size, capacity);
    }

    public boolean remove(RatingsObject element) {
        int index = this.indexOf(element);
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

    public boolean remove(JointKey jKey) {
        int index = -1;
        for (int i = 0; i < size(); i++) {
            if (array[i].getUserID() == jKey.key1 && array[i].getMovieID() == jKey.key2) {
                index = i;
                break;

            }
        }

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

    public RatingsObject set(int index, RatingsObject element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        RatingsObject replaced = this.get(index);
        this.array[index] = element;
        return replaced;
    }

    public RatingsObject set(JointKey jKey, RatingsObject element) {
        RatingsObject replaced = this.get(jKey);
        int index = indexOf(replaced);
        this.array[index] = element;
        return replaced;
    }

    private void resize(int newCapacity) {
        newCapacity = this.array.length*2;
        RatingsObject[] newArray = new RatingsObject[newCapacity];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = this.array[i];
        }
        array = newArray;
    }
    
}
