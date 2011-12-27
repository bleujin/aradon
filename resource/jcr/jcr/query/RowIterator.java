/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.query;

import javax.jcr.RangeIterator;

/**
 * Allows easy iteration through a list of <code>Row</code>s with
 * <code>nextRow</code> as well as a <code>skip</code> method inherited from
 * <code>RangeIterator</code>.
 */
public interface RowIterator extends RangeIterator {

    /**
     * Returns the next <code>Row</code> in the iteration.
     *
     * @return the next <code>Row</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>Row</code>s.
     */
    public Row nextRow();
}

