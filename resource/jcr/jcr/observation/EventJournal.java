/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.observation;

/**
 * An <code>EventJournal</code> is an extension of <code>EventIterator</code>
 * that provides the additional method {@link #skipTo(long)}.
 *
 * @since JCR 2.0
 */
public interface EventJournal extends EventIterator {

    /**
     * Skip all elements of the iterator earlier than <code>date</code>.
     * <p>
     * If an attempt is made to skip past the last element of the iterator, no
     * exception is thrown but the subsequent {@link #nextEvent()} will fail.
     *
     * @param date a long value that represents an offset in milliseconds from
     *             the Epoch, January 1, 1970 00:00:00.000 GMT (Gregorian).
     */
    public void skipTo(long date);
}
