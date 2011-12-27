/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.observation;

/**
 * An event listener.
 * <p>
 * An <code>EventListener</code> can be registered via the <code>{@link
 * javax.jcr.observation.ObservationManager}</code> object. Event listeners are
 * notified asynchronously, and see events after they occur and the transaction
 * is committed. An event listener only sees events for which the session that
 * registered it has sufficient access rights.
 */
public interface EventListener {

    /**
     * This method is called when a bundle of events is dispatched.
     *
     * @param events The event set received.
     */
    public void onEvent(EventIterator events);
}
