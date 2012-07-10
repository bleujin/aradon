/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Allows easy iteration through a list of <code>Property</code>s with
 * <code>nextProperty</code> as well as a <code>skip</code> method.
 */
public interface PropertyIterator extends RangeIterator {

    /**
     * Returns the next <code>Property</code> in the iteration.
     *
     * @return the next <code>Property</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>Property</code>s.
     */
    public Property nextProperty();
}