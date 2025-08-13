package structures;
import stores.*;
import java.time.LocalDate;

public class MoviesObject implements ComparableID{
    private int id;
    private String title;
    private String originalTitle;
    private String overview;
    private String tagline;
    private String status;
    private Genre[] genres;
    private LocalDate release;
    private long budget;
    private long revenue;
    private String[] languages;
    private String originalLanguage;
    private double runtime;
    private String homepage;
    private boolean adult;
    private boolean video;
    private String poster;
    
    private double voteAverage;
    private int voteCount;

    private double popularity;

    private String IMDB;

    private CollectionsObject collection;

    private DynamicArrayList<Company> productionCompanies;
    private DynamicArrayList<String> productionCountries;


    public MoviesObject(int id, String title, String originalTitle, String overview, String tagline, String status,
    Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage,
    double runtime, String homepage, boolean adult, boolean video, String poster) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline;
        this.status = status;
        this.genres = genres;
        this.release = release;
        this.budget = budget;
        this.revenue = revenue;
        this.languages = languages;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.homepage = homepage;
        this.adult = adult;
        this.video = video;
        this.poster = poster;

        this.productionCompanies = new DynamicArrayList<>();
        this.productionCountries = new DynamicArrayList<>();
    }

    public int getID() {
        return id;
    }

    public LocalDate getRelease() {
        return release;
    }

    public long getBudget() {
        return budget;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagline() {
        return tagline;
    }
    
    public String getStatus() {
        return status;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public long getRevenue() {
        return revenue;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public double getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public boolean getAdult() {
        return adult;
    }

    public boolean getVideo() {
        return video;
    }

    public String getPoster() {
        return poster;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    } 


    public String getIMDB() {
        return IMDB;
    }

    public void setIMDB(String IMDB) {
        this.IMDB = IMDB;
    }

    public void setVote(double voteAverage, int voteCount) {
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
    
    public int getVoteCount() {
        return voteCount;
    }
    

    public void addCollection(CollectionsObject collection) {
        this.collection = collection;
    }

    public int getCollectionID() {
        if (this.collection == null) {
            return -1;
        }
        return collection.getID();
    }

    public String getCollectionPoster() {
        return collection.getPoster();
    }


    public boolean addProductionCompany(Company company) {
        if (this.productionCompanies.contains(company)) {
            return false;
        }
        return this.productionCompanies.add(company);
    }

    public DynamicArrayList<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public boolean addProductionCountry(String country) {
        return this.productionCountries.add(country);
    }

    public DynamicArrayList<String> getProductionCountries() {
        return productionCountries;
    }

    public int compareTo(ComparableID o) {
        if (this.release == null) {
            return 1;
        }
        if (((MoviesObject)o).getRelease() == null) {
            return -1;
        }
        return release.compareTo(((MoviesObject)o).getRelease());
    }

}
