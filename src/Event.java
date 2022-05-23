import java.time.LocalDate;
import java.util.*;

/*
Representation of a type of Event that can exist
 */
public abstract class Event {
    private String name;
    private Optional<Location> location;
    private LocalDate date;
    private Optional<Double> price;
    private Optional<Integer> ticketNumber;
    private boolean comingSoon;
    private static final List<Event> eventList = new ArrayList<>();

    // Default constructor
    public Event(){}

    // Constructor with parameters
    public Event(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        assert aLocation != null;
        this.name = aName;
        this.location = Optional.of(aLocation);
        this.date = aDate;
        this.price = Optional.of(aPrice);
        this.ticketNumber = Optional.of(aTicketNumber);
        this.comingSoon = false;
    }
    /*
    Constructor for Coming Soon events. We allow duplicates for Coming Soon events.
     */
    public Event(String aName, LocalDate aDate){
        this.name = aName;
        this.location = Optional.empty();
        this.date = aDate;
        this.price = Optional.empty();
        this.ticketNumber = Optional.empty();
        this.comingSoon = true;
    }

    // Constructor for Festival
    // We assume that there cannot be a Festival with ONLY Coming Soon events
    public Event(String aName, List<Event> aEventList){
        double aPrice = 0;
        int aTicketNumber = Integer.MAX_VALUE;
        LocalDate aDate = LocalDate.MAX;
        List<Location> allLocations = new ArrayList<>();
        assert !aEventList.isEmpty();

        for(Event event: aEventList){
            if (!event.comingSoon){
                if (event.getDate().isBefore(aDate)){
                    aDate = event.getDate();
                }
                if (event.getNumTickets() < aTicketNumber){
                    aTicketNumber = event.getNumTickets();
                }
                if (!allLocations.contains(event.getLocation())){
                    allLocations.add(event.getLocation());
                }
                aPrice += event.getPrice();
            }
        }

        if (allLocations.size() > 1){
            this.location = Optional.of(Location.Multiple);
            this.price = Optional.of(aPrice * 0.2);
            this.ticketNumber = Optional.of(aTicketNumber);
        }
        else if (allLocations.size() == 0){
            this.location = Optional.empty();
            this.price = Optional.empty();
            this.ticketNumber = Optional.empty();
        }
        else{
            this.location = Optional.of(allLocations.get(0));
            this.price = Optional.of(aPrice * 0.2);
            this.ticketNumber = Optional.of(aTicketNumber);
        }

        this.name = aName;
        this.date = aDate;
        this.comingSoon = false;
    }

    // Check if an event with the same date and location has already been created
    public static Event getEvent(Location aLocation, LocalDate aDate){
        assert aLocation != null;
        for (Event e: eventList){
            if (!e.isComingSoon() && e.getLocation() == aLocation && e.getDate() == aDate){
                System.out.println("There is already an event with this location and date.");
                return e;
            }
        }
        return null;
    }

    public void addEvent(){
        eventList.add(this);
    }

    // Getter methods to obtain field values
    public String getName(){
        return this.name;
    }

    public Location getLocation(){
        assert this.location.isPresent();
        return this.location.get();
    }

    public LocalDate getDate(){
        return this.date;
    }

    public double getPrice(){
        assert this.price.isPresent();
        return this.price.get();
    }
    public int getNumTickets() {
        assert this.ticketNumber.isPresent();
        return this.ticketNumber.get();
    }

    public boolean isComingSoon(){
        return this.comingSoon;
    }

    public int getNumberOfVIP(){
        return 0;
    }

    // Abstract methods
    public abstract Event copyEvent();
    public abstract double calculatePrice();
}
