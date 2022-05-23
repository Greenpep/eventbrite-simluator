import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

public class Driver {
    public static void main(String[] args){
        VIP v1 = new VIP("Peter");
        VIP v2 = new VIP("Oliver");
        VIP v3 = new VIP("Christian");
        List<VIP> vipList = new ArrayList<>();
        vipList.add(v1);
        vipList.add(v2);
        vipList.add(v3);
        List<VIP> emptyList = new ArrayList<>();
        EventManagement bob = new EventManagement();

        bob.addConcertEvent("XXXTENTACION", "Heaven's Tour", Location.BellCentre, LocalDate.of(2022,8,1), 149.99, 400, emptyList);
        bob.addComingSoonConcert("Juice World", "Miss You Tour", LocalDate.of(2022,8,1), emptyList);
        bob.addGalaEvent("Grammy's", Location.BellCentre, LocalDate.of(2022,9,1), 205, 700, vipList);
        bob.addComingSoonGala("Grammy's Junior", LocalDate.of(2022,8,20), emptyList);
        bob.addScreeningEvent(Rating.PG13, "No Way Home", Location.PlaceDesArts, LocalDate.of(2022, 8, 1), 28.99, 50);
        bob.addComingSoonScreening(Rating.R, "Deadly Movie", LocalDate.of(2022,10,31));
        bob.addWorkshopEvent("Java Design Patterns", Location.Multiple, LocalDate.of(2022,8,18), 5.98, 60);
        bob.addComingSoonWorkshop("Basic Python", LocalDate.of(2022,8,18));

        List<Event> festivalEvents = new ArrayList<>();
        festivalEvents.add(bob.getHostedEvents().get(0));
        festivalEvents.add(bob.getHostedEvents().get(1));
        festivalEvents.add(bob.getHostedEvents().get(4));
        festivalEvents.add(bob.getHostedEvents().get(5));

        bob.addFestival("Big Summer", festivalEvents);

        System.out.println("Here are the events bob added: ");
        for(int i = 0; i < bob.getHostedEvents().size(); i++){
            System.out.println(bob.getHostedEvents().get(i).getName());
        }
        System.out.println("We have a total of " + bob.calculateVIPs(bob.getHostedEvents().get(2)) + " VIPs at the Grammy's");
    }
}
