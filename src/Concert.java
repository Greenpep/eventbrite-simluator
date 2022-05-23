import java.time.LocalDate;
import java.util.*;

public class Concert extends EventWithVIP{
    private final String artist;

    // Default constructor
    private Concert(String aArtist, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        super(aName, aLocation, aDate, aPrice, aTicketNumber);
        this.artist = aArtist;
    }

    // Constructor for Coming Soon
    private Concert(String aArtist, String aName, LocalDate aDate){
        super(aName, aDate);
        this.artist = aArtist;
    }

    /*
    Constructor of the subclasses of Event are private. We implemented a Flyweight design that utilises some
    callback methods between the subclass and Event. The getConcert method gets the Location and the Date to see
    if there are any duplicates. If not, create new Concert.
    */
    public static Event getConcert(String aArtist, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Event.getEvent(aLocation, aDate);
        if (e == null){
            Concert c = new Concert(aArtist, aName, aLocation, aDate, aPrice, aTicketNumber);
            c.addEvent();
            return c;
        }
        return e;
    }

    public static Event getConcert(String aArtist, String aName, LocalDate aDate){
        return new Concert(aArtist, aName, aDate);
    }

    public String getArtist(){
        return this.artist;
    }

    @Override
    public double calculatePrice() {
        return this.getPrice() * (double) this.getNumTickets() * Profits.instance().getConcertPercentage();
    }

    @Override
    public Event copyEvent() {
        Concert copyConcert1 = (Concert) Concert.getConcert(this.getArtist(), this.getName(), this.getLocation(), this.getDate(), this.getPrice(), this.getNumTickets());
        Concert copyConcert2 = (Concert) Concert.getConcert(this.getArtist(), this.getName(), this.getDate());

        for(VIP v: super.getVipList()){
            copyConcert1.addVIP(v);
            copyConcert2.addVIP(v);
        }

        if (this.isComingSoon()){
            return copyConcert2;
        }
        return copyConcert1;
    }
}
