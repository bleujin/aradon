/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.retention;

import javax.jcr.RepositoryException;

/**
 * <code>Hold</code> represents a hold that can be applied to an existing node
 * in order to prevent the node from being modified or removed. The format and
 * interpretation of the name are not specified. They are
 * application-dependent.
 * <p>
 * If {@link #isDeep()} is <code>true</code>, the hold applies to the node and
 * its entire subgraph. Otherwise the hold applies to the node and its
 * properties only.
 *
 * @see RetentionManager#getHolds(String)
 * @see RetentionManager#addHold(String, String, boolean)
 * @see RetentionManager#removeHold(String, Hold)
 * @since JCR 2.0
 */
public interface Hold {

    /**
     * Returns <code>true</code> if this <code>Hold</code> is deep.
     *
     * @return <code>true</code> if this <code>Hold</code> is deep.
     * @throws RepositoryException if an error occurs.
     */
    public boolean isDeep() throws RepositoryException;

    /**
     * Returns the name of this <code>Hold</code>. A JCR name.
     *
     * @return the name of this <code>Hold</code>. A JCR name.
     * @throws RepositoryException if an error occurs.
     */
    public String getName() throws RepositoryException;
}
