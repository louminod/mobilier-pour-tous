package tools;

import java.util.ArrayList;
import java.util.List;

import entitiees.Archive;
import entitiees.Association;
import entitiees.DepotVente;
import entitiees.Destination;

public class Activites {
    private List<Destination> lstDestinations;
    private Archive archive;

    public Activites() {
        this.lstDestinations = new ArrayList<>();
        this.archive = new Archive();
    }

    @Override
    public boolean equals(Object arg0) {
        return super.equals(arg0);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public List<Destination> getLstDestinations() {
        return lstDestinations;
    }

    public List<Association> getLstAssociations() {
        List<Association> lstResult = new ArrayList<>();

        for(Destination destination : this.lstDestinations){
            if (destination.getClass() == Association.class && !lstResult.contains(destination)) {
                lstResult.add((Association) destination);
            }
        }

        return lstResult;
    }

    public void addDestination(Destination destination) {
        this.lstDestinations.add(destination);
    }

    public Archive getArchive() {
        return this.archive;
    }
}
