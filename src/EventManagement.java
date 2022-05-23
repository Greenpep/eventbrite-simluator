import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
Controller to manage events hosted on EventBrite.
 */
public class EventManagement {
    private final List<Event> aHostedEvents = new ArrayList<Event>();

    /*
    Method to host a new Concert event
     */
    public void addConcertEvent(String aArtist, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber, List<VIP> allVIP){
        Event e = Concert.getConcert(aArtist, aName, aLocation, aDate, aPrice, aTicketNumber);
        if (!this.aHostedEvents.contains(e)){
            Concert c = (Concert) e;
            for (VIP v: allVIP){
                c.addVIP(v);
            }
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new coming soon Concert event
     */
    public void addComingSoonConcert(String aArtist, String aName, LocalDate aDate, List<VIP> allVIP){
        Event e = Concert.getConcert(aArtist, aName, aDate);
        if (!this.aHostedEvents.contains(e)){
            Concert c = (Concert) e;
            for (VIP v: allVIP){
                c.addVIP(v);
            }
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new Gala event
     */
    public void addGalaEvent(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber, List<VIP> allVIP){
        Event e = Gala.getGala(aName, aLocation, aDate, aPrice, aTicketNumber);
        if (!this.aHostedEvents.contains(e)){
            Gala g = (Gala) e;
            for (VIP v: allVIP){
                g.addVIP(v);
            }
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new coming soon Gala event
     */
    public void addComingSoonGala(String aName, LocalDate aDate, List<VIP> allVIP){
        Event e = Gala.getGala(aName, aDate);
        if (!this.aHostedEvents.contains(e)){
            Gala g = (Gala) e;
            for (VIP v: allVIP){
                g.addVIP(v);
            }
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new Screening event
     */
    public void addScreeningEvent(Rating aRating, String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Screening.getScreening(aRating, aName, aLocation, aDate, aPrice, aTicketNumber);
        if (!this.aHostedEvents.contains(e)){
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new coming soon Screening event
     */
    public void addComingSoonScreening(Rating rating, String aName, LocalDate aDate){
        Event e = Screening.getScreening(rating, aName, aDate);
        if (!this.aHostedEvents.contains(e)){
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new Workshop event
     */
    public void addWorkshopEvent(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        Event e = Workshop.getWorkshop(aName, aLocation, aDate, aPrice, aTicketNumber);
        if (!this.aHostedEvents.contains(e)){
            this.aHostedEvents.add(e);
        }
    }

    /*
    Method to host a new coming soon Workshop event
     */
    public void addComingSoonWorkshop(String aName, LocalDate aDate){
        Event e = Workshop.getWorkshop(aName, aDate);
        if (!this.aHostedEvents.contains(e)){
            this.aHostedEvents.add(e);
        }
    }

    public void addFestival(String aName, List<Event> eventList){
        Event e = Festival.getFestival(aName, eventList);
        if (!this.aHostedEvents.contains(e)){
            this.aHostedEvents.add(e);
        }
    }

    /*
    Return the list of hosted events on EventBrite.
    This method assumes that Events are immutable.
     */
    public List<Event> getHostedEvents(){
        return new ArrayList<Event>(aHostedEvents);
    }

    public FilterResult filterEvents(FilterStrategy strategy){
        FilterResult filterResult = new FilterResult(aHostedEvents);
        filterResult.filterEvents(strategy);
        return filterResult;
    }

    public double calculateProfit(FilterResult filterResult, double concertPer, double workshopPer, double galaPer, double screeningPer){
        double sum = 0;
        Profits.instance().setPercentages(concertPer, galaPer, screeningPer, workshopPer);
        for(Event event: filterResult.getaFilteredEvents()){
            sum += event.calculatePrice();
        }
        return sum;
    }

    public int calculateVIPs(Event event){
        return event.getNumberOfVIP();
    }

    public int calculateAllEventsVIP(){
        int numberOfVIP = 0;
        for(Event event: aHostedEvents){
            numberOfVIP += event.getNumberOfVIP();
        }
        return numberOfVIP;
    }
}
