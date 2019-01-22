package event;

/**
 * Functional interface representing delegate function to execute when an event is posted
 */
public interface Observer {

    public void doEvent();
}
