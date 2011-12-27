/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.security;

import javax.jcr.RepositoryException;

/**
 * An <code>NamedAccessControlPolicy</code> is an opaque access control policy
 * that is described by a JCR name and optionally a description.
 * <code>NamedAccessControlPolicy</code> are immutable and can therefore be
 * directly applied to a node without additional configuration step.
 *
 * @since JCR 2.0
 */
public interface NamedAccessControlPolicy extends AccessControlPolicy {

    /**
     * Returns the name of the access control policy, which is JCR name and
     * should be unique among the choices applicable to any particular node.
     *
     * @return the name of the access control policy. A JCR name.
     * @throws RepositoryException if an error occurs.
     */
    public String getName() throws RepositoryException;
}
