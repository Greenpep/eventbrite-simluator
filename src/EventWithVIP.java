import java.time.LocalDate;
import java.util.*;

public abstract class EventWithVIP extends Event{
    private final List<VIP> vipList = new ArrayList<>();

    // Default constructor
    public EventWithVIP(String aName, Location aLocation, LocalDate aDate, double aPrice, int aTicketNumber){
        super(aName, aLocation, aDate, aPrice, aTicketNumber);
    }

    // Constructor for Coming Soon
    public EventWithVIP(String aName, LocalDate aDate){
        super(aName, aDate);
    }

    public List<VIP> getVipList(){
        List<VIP> vipListCopy = new ArrayList<>();
        for(VIP vip: this.vipList){
            String vipName = vip.getName();
            VIP vipCopy = new VIP(vipName);
            vipListCopy.add(vipCopy);
        }
        return vipListCopy;
    }

    public void addVIP(VIP vip){
        String vipName = vip.getName();
        VIP vipCopy = new VIP(vipName);
        this.vipList.add(vipCopy);
    }

    @Override
    public int getNumberOfVIP(){
        return this.vipList.size();
    }

    public abstract double calculatePrice();
    public abstract Event copyEvent();
}
