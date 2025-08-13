package structures;

public class JointKey implements Comparable<JointKey> {
    Integer key1;
    Integer key2;
    public JointKey(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
    public int compareTo(JointKey o) {
        if (key1.compareTo(o.key1) == 0) {
            return key2.compareTo(o.key2);
        }
        return key1.compareTo(o.key1);
    }

    public int getKey1() {
        return key1;
    }

    public int getKey2() {
        return key2;
    }

    public JointKey reverse() {
        JointKey reversedKey = new JointKey(key2, key1);
        return reversedKey;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof JointKey)) return false;
        
        JointKey other = (JointKey) o;
        
        // Compare key1 values
        if (key1 == null) {
            if (other.key1 != null) return false;
        } else if (!key1.equals(other.key1)) {
            return false;
        }
        
        // Compare key2 values
        if (key2 == null) {
            if (other.key2 != null) return false;
        } else if (!key2.equals(other.key2)) {
            return false;
        }
        
        return true;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
       
        if (key1 == null) {
            result = prime * result + 0;
        } else {
            result = prime * result + key1.hashCode();
        }

        if (key2 == null) {
            result = prime * result + 0;
        } else {
            result = prime * result + key2.hashCode();
        }
        
        return result;
    }

}
