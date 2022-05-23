import java.time.LocalDate;
import java.util.*;

public class Gala extends EventWithVIP{
    // Default constructor
    private Gala(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        super(aName, aLocation, aDate, aPrice, aTicketNumber);
    }

    // Constructor for Coming Soon
    private Gala(String aName, LocalDate aDate){
        super(aName, aDate);
    }

    /*
    Constructor of the subclasses of Event are private. We implemented a Flyweight design that utilises some
    callback methods between the subclass and Event. The getGala method gets the Location and the Date to see
    if there are any duplicates. If not, create new Gala.
    */
    public static Event getGala(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Event.getEvent(aLocation, aDate);
        if (e == null){
            Gala g = new Gala(aName, aLocation, aDate, aPrice, aTicketNumber);
            g.addEvent();
            return g;
        }
        return e;
    }

    public static Event getGala(String aName, LocalDate aDate){
        return new Gala(aName, aDate);
    }

    @Override
    public double calculatePrice() {
        return this.getPrice() * (double) this.getNumTickets() * Profits.instance().getGalaPercentage();
    }

    @Override
    public Event copyEvent(){
        Gala copyGala1 = (Gala) Gala.getGala(this.getName(), this.getLocation(), this.getDate(), this.getPrice(), this.getNumTickets());
        Gala copyGala2 = (Gala) Gala.getGala(this.getName(), this.getDate());

        for(VIP v: super.getVipList()){
            copyGala1.addVIP(v);
            copyGala2.addVIP(v);
        }

        if (this.isComingSoon()){
            return copyGala2;
        }
        return copyGala1;
    }
}
