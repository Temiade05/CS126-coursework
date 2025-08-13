package structures;

import java.time.LocalDateTime;

public class RatingsObject implements Comparable<RatingsObject> {
    private int userID;
    private int movieID;
    private float rating;
    private LocalDateTime timestamp;

    public RatingsObject(int userID, int movieID, float rating, LocalDateTime timestamp) {
        this.userID = userID;
        this.movieID = movieID;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getUserID() {
        return userID;
    }

    public int getMovieID() {
        return movieID;
    }
    public float getRating() {
        return rating;
    }

    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    public int compareTo(RatingsObject o) {
        if (this.timestamp == null) {
            return -1;
        }

        if (o.getTimeStamp() == null) {
            return 1;
        }
        return timestamp.compareTo(o.getTimeStamp());
    }
    
}
