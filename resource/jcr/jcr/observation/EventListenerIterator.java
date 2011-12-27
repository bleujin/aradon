/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.observation;

import javax.jcr.RangeIterator;

/**
 * Allows easy iteration through a list of <code>EventListener</code>s with
 * <code>nextEventListener</code> as well as a <code>skip</code> method
 * inherited from <code>RangeIterator</code>.
 */
public interface EventListenerIterator extends RangeIterator {

    /**
     * Returns the next <code>EventListener</code> in the iteration.
     *
     * @return the next <code>EventListener</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>EventListener</code>s.
     */
    public EventListener nextEventListener();
}