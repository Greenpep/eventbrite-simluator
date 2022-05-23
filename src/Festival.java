import java.time.LocalDate;
import java.util.*;

public class Festival extends Event {

    private final List<Event> eventList = new ArrayList<>();

    private Festival(String aName, List<Event> aEventList){
        super(aName, aEventList);
        this.eventList.addAll(aEventList);
    }

    /*
    Constructor of the subclasses of Event are private. We implemented a Flyweight design that utilises some
    callback methods between the subclass and Event. The getFestival method gets the Location and the Date to see
    if there are any duplicates. If not, create new Festival.
    */
    public static Event getFestival(String aName, List<Event> aEventList){
        LocalDate aDate = LocalDate.MAX;
        Location aLocation;
        List<Location> allLocations = new ArrayList<>();

        for(Event event: aEventList){
            if (!event.isComingSoon()){
                if (event.getDate().isBefore(aDate)){
                    aDate = event.getDate();
                }
                if (!allLocations.contains(event.getLocation())){
                    allLocations.add(event.getLocation());
                }
            }
        }
        if (allLocations.size() > 1){
            aLocation = Location.Multiple;
        }
        else if (allLocations.size() == 0){
            System.out.println("Error: No event entered");
            return null;
        }
        else{
            aLocation = allLocations.get(0);
        }

        Event e = Event.getEvent(aLocation, aDate);
        if (e == null){
            Festival f = new Festival(aName, aEventList);
            f.addEvent();
            return f;
        }
        return e;
    }

    /*
    Since Festival utilizes a Composite design pattern, it delegates the job of calculating the prices to
    the Events in the list. We sum the profits together and return it.
     */
    @Override
    public double calculatePrice() {
        if(this.eventList.isEmpty()){
            return 0.0;
        }
        double sum = 0;
        for(Event event: this.eventList){
            sum += event.calculatePrice();
        }
        return sum;
    }

    /*
    Since Festival utilizes a Composite design pattern, it delegates the job of calculating the number of VIPs to
    the Events in the list. We sum the number of VIPs together and return it.
     */
    @Override
    public int getNumberOfVIP(){
        int numberOfVIP = 0;
        for(Event event: this.eventList){
            numberOfVIP += event.getNumberOfVIP();
        }
        return numberOfVIP;
    }

    @Override
    public Event copyEvent(){
        List<Event> copyList = new ArrayList<>();
        for(Event event: this.eventList){
            copyList.add(event.copyEvent());
        }
        return Festival.getFestival(this.getName(), copyList);
    }
}
