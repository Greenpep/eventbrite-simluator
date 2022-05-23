import java.time.LocalDate;

public class Screening extends Event{
    private final Rating rating;

    // Default constructor
    private Screening(Rating inputRating, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        super(aName, aLocation, aDate, aPrice, aTicketNumber);
        this.rating = inputRating;
    }

    // Constructor for Coming Soon
    private Screening(Rating inputRating, String aName, LocalDate aDate){
        super(aName, aDate);
        this.rating = inputRating;
    }

    /*
    Constructor of the subclasses of Event are private. We implemented a Flyweight design that utilises some
    callback methods between the subclass and Event. The getScreening method gets the Location and the Date to see
    if there are any duplicates. If not, create new Screening.
    */
    public static Event getScreening(Rating inputRating, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Event.getEvent(aLocation, aDate);
        if (e == null){
            Screening s = new Screening(inputRating, aName, aLocation, aDate, aPrice, aTicketNumber);
            s.addEvent();
            return s;
        }
        return e;
    }

    public static Event getScreening(Rating inputRating, String aName, LocalDate aDate){
        return new Screening(inputRating, aName, aDate);
    }

    public Rating getRating(){
        return this.rating;
    }

    @Override
    public double calculatePrice() {
        return this.getPrice() * (double) this.getNumTickets() * Profits.instance().getScreeningPercentage();
    }

    @Override
    public Event copyEvent(){
        if (this.isComingSoon()){
            return Screening.getScreening(this.rating, this.getName(), this.getDate());
        }
        return Screening.getScreening(this.rating, this.getName(), this.getLocation(), this.getDate(), this.getPrice(), this.getNumTickets());
    }
}
