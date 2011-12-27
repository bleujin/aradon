/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.observation;

import javax.jcr.RangeIterator;

/**
 * Allows easy iteration through a list of <code>Event</code>s with
 * <code>nextEvent</code> as well as a <code>skip</code> method inherited from
 * <code>RangeIterator</code>.
 */
public interface EventIterator extends RangeIterator {

    /**
     * Returns the next <code>Event</code> in the iteration.
     *
     * @return the next <code>Event</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>Event</code>s.
     */
    public Event nextEvent();
}