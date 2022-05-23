import java.util.*;

public class FilterByLocation implements FilterStrategy{
    private Location locationFilter;

    public FilterByLocation(Location aLocation){
        this.locationFilter = aLocation;
    }

    public void setLocationFilter(Location aLocation){
        this.locationFilter = aLocation;
    }

    @Override
    public List<Event> filter(List<Event> allEvents) {
        List<Event> filteredEvents = new ArrayList<>();
        for(Event event: allEvents){
            if (event.getLocation() == this.locationFilter){
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}
