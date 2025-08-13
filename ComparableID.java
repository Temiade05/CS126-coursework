package structures;

public interface ComparableID extends Comparable<ComparableID> {
    public int getID();
    public int compareTo(ComparableID o);
}
