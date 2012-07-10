/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.retention;

import javax.jcr.RepositoryException;

/**
 * An <code>RetentionPolicy</code> is an object with a name and an optional
 * description.
 *
 * @see RetentionManager#getRetentionPolicy(String)
 * @see RetentionManager#setRetentionPolicy(String, RetentionPolicy)
 * @see RetentionManager#removeRetentionPolicy(String)
 * @since JCR 2.0
 */
public interface RetentionPolicy {
    /**
     * Returns the name of the retention policy. A JCR name.
     *
     * @return the name of the access control policy. A JCR name.
     * @throws RepositoryException if an error occurs.
     */
    public String getName() throws RepositoryException;

}
