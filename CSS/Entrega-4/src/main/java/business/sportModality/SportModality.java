package business.sportModality;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = SportModality.FIND_BY_NAME, query = "SELECT sm FROM SportModality sm WHERE sm.name = :" +
                SportModality.SPORT_MODALITY_NAME),
        @NamedQuery(name = SportModality.GET_ALL, query = "SELECT sm.name FROM SportModality sm")
})
public class SportModality {

    public static final String FIND_BY_NAME = "SportModality.findByName";
    public static final String GET_ALL = "SportModality.getAllNames";

    public static final String SPORT_MODALITY_NAME = "name";

    @Id
    @GeneratedValue
    private int id;
    
    @Column(nullable=false)
    private String name;
    
    @Column(nullable=false)
    private int minDuration;
    
    @Column(nullable=false)
    private double costPerHour;

    SportModality() {
    }

    public SportModality(String name, int minDuration, double costPerHour) {
        this.name = name;
        this.minDuration = minDuration;
        this.costPerHour = costPerHour;
    }

    public String getName() {
        return name;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public int getMinDuration() {
        return minDuration;
    }

}
