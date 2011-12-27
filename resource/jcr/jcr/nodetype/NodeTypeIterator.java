/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

import javax.jcr.RangeIterator;

/**
 * An iterator for <code>NodeType</code> objects.
 */
public interface NodeTypeIterator extends RangeIterator {

    /**
     * Returns the next <code>NodeType</code> in the iteration.
     *
     * @return the next <code>NodeType</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>NodeType</code>s.
     */
    public NodeType nextNodeType();
}
