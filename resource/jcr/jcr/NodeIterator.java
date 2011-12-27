/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Allows easy iteration through a list of <code>Node</code>s with
 * <code>nextNode</code> as well as a <code>skip</code> method inherited from
 * <code>RangeIterator</code>.
 */
public interface NodeIterator extends RangeIterator {

    /**
     * Returns the next <code>Node</code> in the iteration.
     *
     * @return the next <code>Node</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>Node</code>s.
     */
    public Node nextNode();
}
