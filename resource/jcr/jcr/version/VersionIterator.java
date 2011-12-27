/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.version;

import javax.jcr.RangeIterator;

/**
 * Allows easy iteration through a list of <code>Version</code>s objects with
 * <code>nextVersion</code> as well as a <code>skip</code> method inherited from
 * <code>RangeIterator</code>.
 */
public interface VersionIterator extends RangeIterator {

    /**
     * Returns the next <code>Version</code> in the iteration.
     *
     * @return the next <code>Version</code> in the iteration.
     * @throws java.util.NoSuchElementException
     *          if iteration has no more
     *          <code>Version</code>s.
     */
    public Version nextVersion();
}
