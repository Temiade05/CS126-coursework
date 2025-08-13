package stores;

import structures.*;

import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;
    public HashMap<Integer, CreditsObject> CreditHashMap;
    int size;


    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        this.stores = stores;
        CreditHashMap = new HashMap<>();
        size = 0;
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        if (CreditHashMap.containsKey(id)) {
            return false;
        }
    
        CreditsObject newCredits = new CreditsObject(cast, crew, id);
        CreditHashMap.add(id, newCredits);
        size++;
    
        return true; 
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        if (!CreditHashMap.containsKey(id)) {
            return false;
        }
        CreditHashMap.remove(id);
        size --;
        return true;
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film cannot be 
     *         found in Credits, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        CreditsObject credits = CreditHashMap.get(filmID);
        if (credits == null) {
            return new CastCredit[0]; 
        }

        // Get the list of crew members
        CastCredit[] castArray = credits.getCast();

        // Performs a Bubble Sort  on the array based on the order
        for (int i = 0; i < castArray.length - 1; i++) {
            for (int j = 0; j < castArray.length - i - 1; j++) {
                if (castArray[j].getOrder() > castArray[j + 1].getOrder()) {
                    CastCredit temp = castArray[j];
                    castArray[j] = castArray[j + 1];
                    castArray[j + 1] = temp;
                }
            }
        }
        return castArray;
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in "id" order (not "elementID"). If there 
     *         is no crew members attached to a film, or the film cannot be found in Credits, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        CreditsObject credits = CreditHashMap.get(filmID);
        if (credits == null) {
            return new CrewCredit[0]; 
        }
    
        CrewCredit[] crewArray = credits.getCrew();
    
        // Bubble Sort, sorting by ID
        int n = crewArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (crewArray[j].getID() > crewArray[j + 1].getID()) {
                    CrewCredit temp = crewArray[j];
                    crewArray[j] = crewArray[j + 1];
                    crewArray[j + 1] = temp;
                }
            }
        }   
        return crewArray;
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        CreditsObject credits = CreditHashMap.get(filmID);
        if (credits == null) {
            return -1;
        }
        return credits.getCast().length;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCrew(int filmID) {
        CreditsObject credits = CreditHashMap.get(filmID);
        if (credits == null) {
            return -1;
        }
        return credits.getCrew().length;
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        DynamicArrayList<Person> uniqueCastList = new DynamicArrayList<>();
        HashMap<Integer, Person> uniquePersons = new HashMap<>();
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();  
        
        for (int i = 0; i < creditArray.size(); i++) {
            CreditsObject credits = CreditHashMap.get(creditArray.get(i)); 

            if (credits == null) {
                continue; 
            }

            CastCredit[] cast = credits.getCast();

            for (int j = 0; j < cast.length; j++) {
                CastCredit castMember = cast[j];
                int personID = castMember.getID();
                String personName = castMember.getName();
                String profilePath = castMember.getProfilePath();

                if (!uniquePersons.containsKey(personID)) {
                    Person newPerson = new Person(personID, personName, profilePath);
                    uniquePersons.add(personID, newPerson); 
                    uniqueCastList.add(newPerson); 
                }
            }
        }

        
        if (uniqueCastList.size() == 0) {
            return new Person[0];
        } else {
            Person[] uniqueCastArray = new Person[uniqueCastList.size()];
        
            for (int i = 0; i < uniqueCastList.size(); i++) {
                uniqueCastArray[i] = uniqueCastList.get(i);
            }
            
            return uniqueCastArray;
        }
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        DynamicArrayList<Person> uniqueCrewList = new DynamicArrayList<>();
        HashMap<Integer, Person> uniquePersons = new HashMap<>();
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();  
        
        for (int i = 0; i < creditArray.size(); i++) {
            CreditsObject credits = CreditHashMap.get(creditArray.get(i)); 

            if (credits == null) {
                continue; 
            }

            CrewCredit[] crew = credits.getCrew();

            
            for (int j = 0; j < crew.length; j++) {
                CrewCredit crewMember = crew[j];
                int personID = crewMember.getID();
                String personName = crewMember.getName();
                String profilePath = crewMember.getProfilePath();

                if (!uniquePersons.containsKey(personID)) {
                    Person newPerson = new Person(personID, personName, profilePath);
                    uniquePersons.add(personID, newPerson); 
                    uniqueCrewList.add(newPerson); 
                }
            }
        }

        if (uniqueCrewList.size() == 0) {
            return new Person[0];
        } else {
            Person[] uniqueCastArray = new Person[uniqueCrewList.size()];

            for (int i = 0; i < uniqueCrewList.size(); i++) {
                uniqueCastArray[i] = uniqueCrewList.get(i);
            }
            
            return uniqueCastArray;
        }
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCast(String cast) {
        DynamicArrayList<Person> castList = new DynamicArrayList<>();
        Person[] uniqueCast = getUniqueCast();

        for (Person person: uniqueCast){
            if (person.getName().contains(cast)){
                castList.add(person);
            }
        }

        Person[] finalPersonList = new Person[castList.size()];
        for (int i = 0; i < castList.size(); i++){
            finalPersonList[i] = castList.get(i);
        }
        return finalPersonList;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCrew(String crew) {
        DynamicArrayList<Person> crewList = new DynamicArrayList<>();
        Person[] uniqueCrew = getUniqueCrew();

        for (Person person: uniqueCrew){
            if (person.getName().contains(crew)){
                crewList.add(person);
            }
        }

        Person[] finalPersonList = new Person[crewList.size()];
        for (int i = 0; i < crewList.size(); i++){
            finalPersonList[i] = crewList.get(i);
        }
        return finalPersonList;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();
    
        for (int i = 0; i < creditArray.size(); i++) {
            CreditsObject credits = CreditHashMap.get(creditArray.get(i)); 

            if (credits == null) {
                continue;
            }

            CastCredit[] cast = credits.getCast(); 

            for (int j = 0; j < cast.length; j++) {
                if (cast[j].getID() == castID) { 
                    return new Person(cast[j].getID(), cast[j].getName(), cast[j].getProfilePath());
                }
            }
        }
    
        return null; 
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID){
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();
        
        for (int i = 0; i < creditArray.size(); i++) {
            CreditsObject credits = CreditHashMap.get(creditArray.get(i)); 

            if (credits == null) {
                continue;
            }

            CrewCredit[] crew = credits.getCrew(); 

            for (int j = 0; j < crew.length; j++) {
                if (crew[j].getID() == crewID) { 
                    return new Person(crew[j].getID(), crew[j].getName(), crew[j].getProfilePath());
                }
            }
        }
        
        return null; 
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        DynamicArrayList<Integer> filmList = new DynamicArrayList<>();
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();
        
        for (int i = 0; i < creditArray.size(); i++) {
            int movieID = creditArray.get(i);
            CreditsObject credits = CreditHashMap.get(movieID); 

            if (credits == null) {
                continue;
            }

            CastCredit[] cast = credits.getCast(); 

            for (int j = 0; j < cast.length; j++) {
                if (cast[j].getID() == castID) { 
                    filmList.add(movieID);
                    break; 
                }
            }
        }

        if (filmList.size() == 0) {
            return new int[0]; 
        } else {
            int[] filmArray = new int[filmList.size()];
            for (int i = 0; i < filmList.size(); i++) {
                filmArray[i] = filmList.get(i);
            }
            return filmArray;
        }
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        DynamicArrayList<Integer> filmList = new DynamicArrayList<>();
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();
        
        for (int i = 0; i < creditArray.size(); i++) {
            int movieID = creditArray.get(i);
            CreditsObject credits = CreditHashMap.get(movieID); 

            if (credits == null) {
                continue;
            }

            CrewCredit[] crew = credits.getCrew(); 

            for (int j = 0; j < crew.length; j++) {
                if (crew[j].getID() == crewID) { 
                    filmList.add(movieID);
                    break; 
                }
            }
        }

        if (filmList.size() == 0) {
            return new int[0]; 
        } else {
            int[] filmArray = new int[filmList.size()];
            for (int i = 0; i < filmList.size(); i++) {
                filmArray[i] = filmList.get(i);
            }
            return filmArray;
        }
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID){
        DynamicArrayList<Integer> filmList = new DynamicArrayList<>();
        DynamicArrayList<Integer> creditArray = CreditHashMap.getAllKeys();
        
        for (int i = 0; i < creditArray.size(); i++) {
            int movieID = creditArray.get(i);
            CreditsObject credits = CreditHashMap.get(movieID);

            if (credits == null) {
                continue;
            }

            CastCredit[] cast = credits.getCast();

            for (int j = 0; j < cast.length; j++) {
                if (cast[j].getID() == castID && cast[j].getOrder() <= 3) { 
                    filmList.add(movieID);
                    break; 
                }
            }
        }
    
        if (filmList.size() == 0) {
            return new int[0]; 
        } else {
            int[] filmArray = new int[filmList.size()];
            for (int i = 0; i < filmList.size(); i++) {
                filmArray[i] = filmList.get(i);
            }
            return filmArray;
        }
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest to lowest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        DynamicArrayList<Integer> castIDs = new DynamicArrayList<>();
        DynamicArrayList<Integer> creditCounts = new DynamicArrayList<>();
        DynamicArrayList<Integer> movieIDs = CreditHashMap.getAllKeys();

        for (int i = 0; i < movieIDs.size(); i++) {
            int movieID = movieIDs.get(i);
            CreditsObject credits = CreditHashMap.get(movieID);

            if (credits == null) {
                continue;
            }

            CastCredit[] cast = credits.getCast(); 

            for (int j = 0; j < cast.length; j++) {
                int castID = cast[j].getID();
                int index = castIDs.indexOf(castID);
                if (index == -1) {
                    castIDs.add(castID);
                    creditCounts.add(1);
                } else {
                    creditCounts.set(index, creditCounts.get(index) + 1);
                }
            }
        }

        for (int i = 0; i < creditCounts.size() - 1; i++) {
            for (int j = i + 1; j < creditCounts.size(); j++) {
                if (creditCounts.get(i) < creditCounts.get(j)) {
                    int tempCredit = creditCounts.get(i);
                    creditCounts.set(i, creditCounts.get(j));
                    creditCounts.set(j, tempCredit);

                    int tempID = castIDs.get(i);
                    castIDs.set(i, castIDs.get(j));
                    castIDs.set(j, tempID);
                }
            }
        }

        int resultSize = Math.min(numResults, castIDs.size());
        Person[] result = new Person[resultSize];

        for (int i = 0; i < resultSize; i++) {
            int castID = castIDs.get(i);
            result[i] = getCast(castID); 
        }

        return result;
    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        int creditCount = 0;
        DynamicArrayList<Integer> movieIDs = CreditHashMap.getAllKeys();

        for (int i = 0; i < movieIDs.size(); i++) {
            int movieID = movieIDs.get(i);
            CreditsObject credits = CreditHashMap.get(movieID);
            if (credits == null) {
                continue;
            }

            CastCredit[] cast = credits.getCast(); 

            for (int j = 0; j < cast.length; j++) {
                if (cast[j].getID() == castID) {
                    creditCount++; 
                }
            }
        }

        if (creditCount > 0) {
            return creditCount;
        } else {
            return -1;
        }
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return size;
    }
}
