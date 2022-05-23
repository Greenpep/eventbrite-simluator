import java.util.*;

public interface FilterStrategy{
    List<Event> filter(List<Event> allEvents);
}
