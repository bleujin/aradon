/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

import javax.jcr.RepositoryException;

/**
 * Exception thrown when an attempt is made to register an invalid node type
 * definition template.
 *
 * @since JCR 2.0
 */
public class InvalidNodeTypeDefinitionException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public InvalidNodeTypeDefinitionException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public InvalidNodeTypeDefinitionException(String message) {
        super(message);
    }

    /**
     * Constructs a new instance of this class with the specified detail message
     * and root cause.
     *
     * @param message   the detail message. The detail message is saved for later
     *                  retrieval by the {@link #getMessage()} method.
     * @param rootCause root failure cause
     */
    public InvalidNodeTypeDefinitionException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public InvalidNodeTypeDefinitionException(Throwable rootCause) {
        super(rootCause);
    }
}
