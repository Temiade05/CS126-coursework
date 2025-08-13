package structures;

public class CollectionsObject implements ComparableID{
    private int id;
    private String name;
    private String posterPath;
    private String backdropPath;
    private DynamicArrayList<Integer> movieIDs;
    
    public CollectionsObject(int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath){
        this.id = collectionID;
        this.name = collectionName;
        this.posterPath = collectionPosterPath;
        this.backdropPath = collectionBackdropPath;
        this.movieIDs = new DynamicArrayList<>();
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster() {
        return posterPath;
    }

    public String getBackdrop() {
        return backdropPath;
    }

    public int compareTo(ComparableID o) {
        return ((Integer)this.id).compareTo(((CollectionsObject)o).getID());
    }

    public void addMovie(int movieID) {
        if (!movieIDs.contains(movieID)) {
            movieIDs.add(movieID);
        }
    }

    public int[] getMovieIDs() {
        return movieIDs.toIntArray(); 
    }

}
