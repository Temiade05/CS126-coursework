package structures;
import stores.CrewCredit;
import stores.CastCredit;

public class CreditsObject implements ComparableID {
    CastCredit[] cast;
    CrewCredit[] crew;
    int id;

    public CreditsObject( CastCredit[] cast,CrewCredit[] crew, int id) {
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public CastCredit[] getCast() {
        return cast;
    }

    public CrewCredit[] getCrew() {
        return crew;
    }

    public int getID() {
        return id;
    }


    public int compareTo(ComparableID o) {
        return ((Integer)this.id).compareTo((Integer)o.getID());
    }
}
