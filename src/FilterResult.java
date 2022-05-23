import java.util.*;

public class FilterResult {
    private final List<Event> allEvents = new ArrayList<>();
    private List<Event> aFilteredEvents = new ArrayList<>();

    public FilterResult(List<Event> eventList) {
        for(Event event: eventList){
            this.allEvents.add(event.copyEvent());
        }
    }

    public List<Event> getAllEvents(){
        List<Event> allEventsCopy = new ArrayList<>();
        for(Event event: this.allEvents){
            allEventsCopy.add(event.copyEvent());
        }
        return allEventsCopy;
    }

    public List<Event> getaFilteredEvents(){
        List<Event> filteredEventsCopy = new ArrayList<>();
        for(Event event: this.aFilteredEvents){
            filteredEventsCopy.add(event.copyEvent());
        }
        return filteredEventsCopy;
    }

    /*
    By utilizing the Strategy design pattern, we can input any kind of filter strategy that will filter the events.
     */
    public void filterEvents(FilterStrategy strategy){
        this.aFilteredEvents = strategy.filter(this.allEvents);
    }
}
