package event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Main event manager for the application
 */
public class EventManager {
    private static Map<EventType, Set<Observer>> eventMap = new HashMap<>();

    /**
     * Triggers event in all registered observers for the given EventType
     * @param event the EventType of the event to be executed
     */
    public static void post(EventType event){
        for(Observer observer : eventMap.get(event)){
            observer.doEvent();
        }
    }

    /**
     * Attaches an Observer for a given type of event
     * @param event the EventType of the event to be listened for
     * @param handler the Observer that handles the event when it is posted :: post(EventType event)
     */
    public static void attach(EventType event, Observer handler){
        if(eventMap.get(event) == null){
            eventMap.put(event, new HashSet<>());
        }
        eventMap.get(event).add(handler);
    }
}
