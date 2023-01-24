package business.catalogs;

import business.SportModality;

import java.util.LinkedList;
import java.util.List;

public class SportModalityCatalog {

    private List<SportModality> sportModalities;

    public SportModalityCatalog() {
        this.sportModalities = new LinkedList<>();
        loadSportModalities();
    }

    public SportModality getSportModalityByName(String sportModalityName) {
        for (SportModality sportModality : sportModalities) {
            if (sportModality.getName().equals(sportModalityName))
                return sportModality;
        }
        return null;
    }

    public List<SportModality> getAllSportModalities() {
        return sportModalities;
    }
    
	private void loadSportModalities() {
		sportModalities.add(new SportModality("Tenis", 90, 7));
		sportModalities.add(new SportModality("Ginasio", 45, 2));
		sportModalities.add(new SportModality("Natacao", 40, 4));
	}

}
