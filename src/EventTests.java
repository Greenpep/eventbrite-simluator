import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.util.*;

public class EventTests {
    EventManagement manager= new EventManagement();
    LocalDate sawanoDate = LocalDate.of(2022, 7, 7);
    LocalDate awardDate = LocalDate.of(2022, 5, 10);
    LocalDate noWayHomeDate = LocalDate.of(2022, 4, 30);
    LocalDate javaWorkshopDate = LocalDate.of(2022, 4, 30);
    List<Event> osheagaEvents = new ArrayList<>();
    List<VIP> emptyVIP = new ArrayList<>();

    @BeforeEach
    void setUpEvents(){
        manager.addConcertEvent("Sawano", "Attack On Titan Big Hits", Location.BellCentre, sawanoDate, 50.0, 400, emptyVIP);
        manager.addGalaEvent("Best Course Awards", Location.PlaceDesArts, awardDate, 30, 150, emptyVIP);
        manager.addScreeningEvent(Rating.PG13, "No Way Home", Location.ParcJeanDrapeau, noWayHomeDate, 20, 80);
        manager.addWorkshopEvent("Java Workshop", Location.PlaceDesArts, javaWorkshopDate, 10.4, 30);

        osheagaEvents.add(manager.getHostedEvents().get(0));
        osheagaEvents.add(manager.getHostedEvents().get(1));
        osheagaEvents.add(manager.getHostedEvents().get(2));
        osheagaEvents.add(manager.getHostedEvents().get(3));

        manager.addFestival("Osheaga", osheagaEvents);
    }

    @Test
    void artistName(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals("Sawano",  sawanoConcert.getArtist());
    }

    @Test
    void concertName(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals("Attack On Titan Big Hits", sawanoConcert.getName());
    }

    @Test
    void concertLocation(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals(Location.BellCentre, sawanoConcert.getLocation());
    }

    @Test
    void concertDate(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals(sawanoDate, sawanoConcert.getDate());
    }

    @Test
    void concertPrice(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals(50.0, sawanoConcert.getPrice());
    }

    @Test
    void concertTickets(){
        Concert sawanoConcert = (Concert) manager.getHostedEvents().get(0);
        assertEquals(400, sawanoConcert.getNumTickets());
    }

    @Test
    void createGala(){
        Gala awardGala1 = (Gala) manager.getHostedEvents().get(1);
        Gala awardGala2 = (Gala) Gala.getGala("Best Course Awards", Location.PlaceDesArts, awardDate, 30, 150);
        assertEquals(awardGala2, awardGala1);
    }

    @Test
    void createScreening(){
        Screening noWayHome1 = (Screening) manager.getHostedEvents().get(2);
        Screening noWayHome2 = (Screening) Screening.getScreening(Rating.PG13, "No Way Home", Location.ParcJeanDrapeau, noWayHomeDate, 20, 80);
        assertEquals(noWayHome2, noWayHome1);
    }

    @Test
    void createWorkshop(){
        Workshop javaWorkshop1 = (Workshop) manager.getHostedEvents().get(3);
        Workshop javaWorkshop2 = (Workshop) Workshop.getWorkshop("Java Workshop", Location.PlaceDesArts, javaWorkshopDate, 10.4, 30);
        assertEquals(javaWorkshop2, javaWorkshop1);
    }

    @Test
    void createFestival(){
        Festival osheaga1 = (Festival) manager.getHostedEvents().get(4);
        Festival osheaga2 = (Festival) Festival.getFestival("Osheaga", osheagaEvents);
        assertEquals(osheaga2, osheaga1);
    }

    @Test
    void festivalPrice(){
        Festival osheaga = (Festival) manager.getHostedEvents().get(4);
        assertEquals(0.2 * (50+30+20+10.4), osheaga.getPrice());
    }

    @Test
    void festivalTickets(){
        Festival osheaga = (Festival) manager.getHostedEvents().get(4);
        assertEquals(30, osheaga.getNumTickets());
    }

    @Test
    void festivalLocation(){
        Festival osheaga = (Festival) manager.getHostedEvents().get(4);
        assertEquals(Location.Multiple, osheaga.getLocation());
    }

    @Test
    void festivalDate(){
        Festival osheaga = (Festival) manager.getHostedEvents().get(4);
        assertEquals(javaWorkshopDate, osheaga.getDate());
    }

    @Test
    void filterEvents(){
        FilterStrategy byPrice = new FilterByPrice(20, 40);
        FilterResult result = manager.filterEvents(byPrice);
        List<Event> test = new ArrayList<>();
        test.add(manager.getHostedEvents().get(1));
        test.add(manager.getHostedEvents().get(2));
        test.add(manager.getHostedEvents().get(4));
        assertEquals(test, result.getaFilteredEvents());
    }

    @Test
    void calculateVIPs(){
        Concert c = (Concert) manager.getHostedEvents().get(0);
        VIP vip1 = new VIP("Andrew");
        VIP vip2 = new VIP("Eduard");
        c.addVIP(vip1);
        c.addVIP(vip2);
        int numberOfVIP = manager.calculateVIPs(c);
        assertEquals(2, numberOfVIP);
    }

    @Test
    void calculateAllVIPs(){
        Concert c = (Concert) manager.getHostedEvents().get(0);
        Gala g = (Gala) manager.getHostedEvents().get(1);
        VIP vip1 = new VIP("Andrew");
        VIP vip2 = new VIP("Eduard");
        VIP vip3 = new VIP("Pascal");
        VIP vip4 = new VIP("Eren");

        c.addVIP(vip1);
        c.addVIP(vip2);
        g.addVIP(vip3);
        g.addVIP(vip4);
        int numberOfVIP = manager.calculateAllEventsVIP();
        assertEquals(8, numberOfVIP);
    }

    @Test
    void calculateProfit(){
        FilterStrategy byPrice = new FilterByPrice(20, 40);
        FilterResult result = manager.filterEvents(byPrice);
        double profits = manager.calculateProfit(result, 0.6, 0.3, 0.4, 0.7);
        assertEquals(17933.6, profits);
    }

    static class FilterByDate implements FilterStrategy{
        private LocalDate dateFilter;

        FilterByDate(LocalDate aDate){
            this.dateFilter = aDate;
        }

        public void setDateFilter(LocalDate aDate){
            this.dateFilter = aDate;
        }

        @Override
        public List<Event> filter(List<Event> allEvents) {
            List<Event> filteredEvents = new ArrayList<>();
            for(Event event: allEvents){
                if (event.getDate().isEqual(this.dateFilter)){
                    filteredEvents.add(event);
                }
            }
            return filteredEvents;
        }
    }

    // Stub test
    @Test
    void testNewDateFilter(){
        LocalDate aDate = LocalDate.of(2022, 4, 30);
        FilterByDate filterStub = new FilterByDate(aDate);
        FilterResult result = manager.filterEvents(filterStub);
        List<Event> test = new ArrayList<>();
        test.add(manager.getHostedEvents().get(2));
        test.add(manager.getHostedEvents().get(3));
        test.add(manager.getHostedEvents().get(4));
        assertEquals(test, result.getaFilteredEvents());
    }

    // Reflection test
    @Test
    void testFestivalPrice() throws NoSuchFieldException, IllegalAccessException {
        List<Event> test = new ArrayList<>();
        test.add(manager.getHostedEvents().get(0));
        test.add(manager.getHostedEvents().get(1));
        Festival dummyFestival = (Festival) Festival.getFestival("Coachella", test);

        Field f = dummyFestival.getClass().getSuperclass().getDeclaredField("location");
        f.setAccessible(true);
        Optional<Location> fieldValue = (Optional<Location>) f.get(dummyFestival);
        assertEquals(Optional.of(Location.Multiple), fieldValue);
    }
}
