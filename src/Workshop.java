import java.time.LocalDate;
import java.util.*;

public class Workshop extends Event{
    private final List<Workshop> prerequisites = new ArrayList<>();

    // Default constructor
    private Workshop(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        super(aName, aLocation, aDate, aPrice, aTicketNumber);
    }

    // Constructor for Coming Soon
    private Workshop(String aName, LocalDate aDate){
        super(aName, aDate);
    }

    /*
    Constructor of the subclasses of Event are private. We implemented a Flyweight design that utilises some
    callback methods between the subclass and Event. The getWorkshop method gets the Location and the Date to see
    if there are any duplicates. If not, create new Workshop.
    */
    public static Event getWorkshop(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Event.getEvent(aLocation, aDate);
        if (e == null){
            Workshop ws = new Workshop(aName, aLocation, aDate, aPrice, aTicketNumber);
            ws.addEvent();
            return ws;
        }
        return e;
    }

    public static Event getWorkshop(String aName, LocalDate aDate){
        return new Workshop(aName, aDate);
    }

    public void addPrerequisite(Workshop ws){
        Workshop workshopCopy = (Workshop) ws.copyEvent();
        this.prerequisites.add(workshopCopy);
    }

    public List<Workshop> getPrerequisites(){
        List<Workshop> copyPrerequisites = new ArrayList<>();
        for (Workshop workshop: this.prerequisites){
            Workshop workshopCopy = (Workshop) workshop.copyEvent();
            copyPrerequisites.add(workshopCopy);
        }
        return copyPrerequisites;
    }

    @Override
    public double calculatePrice() {
        return this.getPrice() * (double) this.getNumTickets() * Profits.instance().getWorkshopPercentage();
    }

    @Override
    public Event copyEvent(){
        Workshop workshopCopy1 = (Workshop) Workshop.getWorkshop(this.getName(), this.getLocation(), this.getDate(), this.getPrice(), this.getNumTickets());
        Workshop workshopCopy2 = (Workshop) Workshop.getWorkshop(this.getName(), this.getDate());
        for(Workshop ws: this.prerequisites){
            workshopCopy1.addPrerequisite(ws);
            workshopCopy2.addPrerequisite(ws);
        }
        if (this.isComingSoon()){
            return workshopCopy2;
        }
        return workshopCopy1;
    }
}
