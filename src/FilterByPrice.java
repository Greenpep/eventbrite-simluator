import java.util.*;

public class FilterByPrice implements FilterStrategy{
    private double lowestPrice;
    private double highestPrice;

    public FilterByPrice(double lowest, double highest){
        this.lowestPrice = lowest;
        this.highestPrice = highest;
    }

    public void setPriceRange(double lowest, double highest){
        this.lowestPrice = lowest;
        this.highestPrice = highest;
    }

    @Override
    public List<Event> filter(List<Event> allEvents) {
        List<Event> filteredEvents = new ArrayList<>();
        for(Event event: allEvents){
            if (event.getPrice() >= lowestPrice && event.getPrice() <= highestPrice){
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}
