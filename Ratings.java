package stores;

import java.time.LocalDateTime;


import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public HashMap<JointKey, RatingsObject> ratingHashMap;
    public HashMap<Integer, MoviesObject> movieHashMap;
    public SortedRatingArrayList ratingSort;
    int count;
    public Ratings(Stores stores) {
        this.stores = stores;
        this.ratingHashMap = new HashMap<>();
        this.movieHashMap = new HashMap<>();
        this.count = 0;
        this.ratingSort = new SortedRatingArrayList();
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {
        JointKey jKey = new JointKey(userid, movieid);
        RatingsObject existingRating = ratingHashMap.get(jKey);
        
        if (existingRating != null) {
            return false;
        }
        
        RatingsObject ratingObject = new RatingsObject(userid, movieid, rating, timestamp);
        ratingHashMap.add(jKey, ratingObject);
        ratingSort.add(ratingObject);
        count++;
        
        return true;
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        JointKey jKey = new JointKey(userid, movieid);
        if (!ratingHashMap.containsKey(jKey)) {
            return false; 
        }

        boolean removed = ratingHashMap.remove(jKey);
        
        if (removed) {
            ratingSort = new SortedRatingArrayList();
            DynamicArrayList<RatingsObject> allRatings = ratingHashMap.values();
            for (int i = 0; i < allRatings.size(); i++) {
                ratingSort.add(allRatings.get(i));
            }
            
            count--;
            
            return true;
        }
        
        return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        JointKey jKey = new JointKey(userid, movieid);
        RatingsObject newRating = new RatingsObject(userid, movieid, rating, timestamp);
        
        if (ratingHashMap.containsKey(jKey)) {
            ratingHashMap.add(jKey, newRating);
            ratingSort.add(newRating);
        } else {
            ratingHashMap.add(jKey, newRating);
            ratingSort.add(newRating);
            count++;
        }
        
        return true;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        DynamicArrayList<Float> ratingsList = new DynamicArrayList<>();
        DynamicArrayList<RatingsObject> values = ratingHashMap.values();

        for (int i = 0; i < values.size(); i++) {
            RatingsObject rating = (RatingsObject) values.get(i);
            if (rating.getMovieID() == movieid) {
                ratingsList.add(rating.getRating());
            }
        }

        float[] ratingsArray = new float[ratingsList.size()];
        for (int i = 0; i < ratingsList.size(); i++) {
            ratingsArray[i] = ratingsList.get(i);
        }

        return ratingsArray;
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        DynamicArrayList<Float> ratingsList = new DynamicArrayList<>();
        DynamicArrayList<RatingsObject> values = ratingHashMap.values();

        for (int i = 0; i < values.size(); i++) {
            RatingsObject rating = (RatingsObject) values.get(i);
            if (rating.getUserID() == userid) {
                ratingsList.add(rating.getRating());
            }
        }

        float[] ratingsArray = new float[ratingsList.size()];
        for (int i = 0; i < ratingsList.size(); i++) {
            ratingsArray[i] = ratingsList.get(i);
        }

        return ratingsArray;
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in Ratings, but does exist in the Movies store, return 0.0f. 
     *         If the film cannot be found in Ratings or Movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        boolean movieExists = stores.getMovies().getTitle(movieid) != null;
        float[] ratings = getMovieRatings(movieid);
        
        if (ratings.length == 0) {
            if (movieExists) {
                return 0.0f; 
            } else {
                return -1.0f; 
            }
        }
        
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }
        
        return sum / ratings.length;
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found in Ratings, or there are no rating, return -1.0f
     */
    @Override
    public float getUserAverageRating(int userid) {
        float[] ratings = getUserRatings(userid);
    
        if (ratings.length == 0) {
            return -1.0f; 
        }
        
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }
        
        return sum / ratings.length;
    
    }
    

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        if (num <= 0) {
            return new int[0];
        }
        
        HashMap<Integer, Integer> movieRatingCounts = new HashMap<>();
        DynamicArrayList<Integer> uniqueMovieIDs = new DynamicArrayList<>();
        DynamicArrayList<RatingsObject> allRatings = ratingHashMap.values();
        for (int i = 0; i < allRatings.size(); i++) {
            RatingsObject rating = allRatings.get(i);
            int movieID = rating.getMovieID();
            boolean found = false;
            for (int j = 0; j < uniqueMovieIDs.size(); j++) {
                if (uniqueMovieIDs.get(j) == movieID) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                uniqueMovieIDs.add(movieID);
                movieRatingCounts.add(movieID, 1); 
            } else {
                int currentCount = movieRatingCounts.get(movieID);
                movieRatingCounts.add(movieID, currentCount + 1);
            }
        }
    
        if (uniqueMovieIDs.size() == 0) {
            return new int[0];
        }
        
        int[] movieIDsArray = new int[uniqueMovieIDs.size()];
        for (int i = 0; i < uniqueMovieIDs.size(); i++) {
            movieIDsArray[i] = uniqueMovieIDs.get(i);
        }
        
        for (int i = 0; i < movieIDsArray.length - 1; i++) {
            int maxIndex = i;
            
            for (int j = i + 1; j < movieIDsArray.length; j++) {
                int countMax = movieRatingCounts.get(movieIDsArray[maxIndex]);
                int countJ = movieRatingCounts.get(movieIDsArray[j]);
                
                if (countJ > countMax) {
                    maxIndex = j;
                }
            }
            
            if (maxIndex != i) {
                int temp = movieIDsArray[i];
                movieIDsArray[i] = movieIDsArray[maxIndex];
                movieIDsArray[maxIndex] = temp;
            }
        }
        
        int resultSize = Math.min(num, movieIDsArray.length);
        int[] result = new int[resultSize];
        
        for (int i = 0; i < resultSize; i++) {
            result[i] = movieIDsArray[i];
        }
        
        return result;
    }
    

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users in Ratings
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        if (num <= 0) {
            return new int[0];
        }
        
        HashMap<Integer, Integer> userRatingCounts = new HashMap<>();
        DynamicArrayList<Integer> uniqueUserIDs = new DynamicArrayList<>();
        DynamicArrayList<RatingsObject> allRatings = ratingHashMap.values();
        for (int i = 0; i < allRatings.size(); i++) {
            RatingsObject rating = allRatings.get(i);
            int userID = rating.getUserID();
            boolean found = false;
            for (int j = 0; j < uniqueUserIDs.size(); j++) {
                if (uniqueUserIDs.get(j) == userID) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                uniqueUserIDs.add(userID);
                userRatingCounts.add(userID, 1); 
            } else {
                int currentCount = userRatingCounts.get(userID);
                userRatingCounts.add(userID, currentCount + 1);
            }
        }
        
        if (uniqueUserIDs.size() == 0) {
            return new int[0];
        }
        
        int[] userIDsArray = new int[uniqueUserIDs.size()];
        for (int i = 0; i < uniqueUserIDs.size(); i++) {
            userIDsArray[i] = uniqueUserIDs.get(i);
        }
        
        for (int i = 0; i < userIDsArray.length - 1; i++) {
            int maxIndex = i;
            
            for (int j = i + 1; j < userIDsArray.length; j++) {
                int countMax = userRatingCounts.get(userIDsArray[maxIndex]);
                int countJ = userRatingCounts.get(userIDsArray[j]);
                
                if (countJ > countMax) {
                    maxIndex = j;
                }
            }
            
            if (maxIndex != i) {
                int temp = userIDsArray[i];
                userIDsArray[i] = userIDsArray[maxIndex];
                userIDsArray[maxIndex] = temp;
            }
        }
        
        int resultSize = Math.min(num, userIDsArray.length);
        int[] result = new int[resultSize];
        
        for (int i = 0; i < resultSize; i++) {
            result[i] = userIDsArray[i];
        }
        
        return result;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the Movies store, but there are no ratings for it, then return 0. 
     *         If the movie does not exist in the Ratings or Movies store, then return -1.
     */
    @Override
    public int getNumRatings(int movieid) {
        boolean movieExists = stores.getMovies().getTitle(movieid) != null;
        float[] ratings = getMovieRatings(movieid);
        
        if (ratings.length == 0 && !movieExists) {
            return -1;
        }
        
        return ratings.length;
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        if (numResults <= 0) {
            return new int[0];
        }
        
        HashMap<Integer, Float> movieAvgRatings = new HashMap<>();
        DynamicArrayList<RatingsObject> allRatings = ratingHashMap.values();
        DynamicArrayList<Integer> uniqueMovieIDs = new DynamicArrayList<>();
        
        for (int i = 0; i < allRatings.size(); i++) {
            int movieID = allRatings.get(i).getMovieID();
            boolean found = false;
            
            for (int j = 0; j < uniqueMovieIDs.size(); j++) {
                if (uniqueMovieIDs.get(j) == movieID) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                uniqueMovieIDs.add(movieID);
            }
        }
        
        for (int i = 0; i < uniqueMovieIDs.size(); i++) {
            int movieID = uniqueMovieIDs.get(i);
            float[] ratings = getMovieRatings(movieID);
            
            if (ratings.length > 0) {
                float sum = 0.0f;
                for (int j = 0; j < ratings.length; j++) {
                    sum += ratings[j];
                }
                float avgRating = sum / ratings.length;
                movieAvgRatings.add(movieID, avgRating);
            }
        }
        
        int[] movieIDs = new int[movieAvgRatings.getAllKeys().size()];
        for (int i = 0; i < movieAvgRatings.getAllKeys().size(); i++) {
            movieIDs[i] = movieAvgRatings.getAllKeys().get(i);
        }
        
        for (int i = 0; i < movieIDs.length - 1; i++) {
            for (int j = 0; j < movieIDs.length - i - 1; j++) {
                float ratingA = movieAvgRatings.get(movieIDs[j]);
                float ratingB = movieAvgRatings.get(movieIDs[j + 1]);
                
                if (ratingA < ratingB) {
                    int temp = movieIDs[j];
                    movieIDs[j] = movieIDs[j + 1];
                    movieIDs[j + 1] = temp;
                }
            }
        }
        
        int resultSize = Math.min(numResults, movieIDs.length);
        int[] result = new int[resultSize];
        
        for (int i = 0; i < resultSize; i++) {
            result[i] = movieIDs[i];
        }
        
        return result;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return count;
    }
}
