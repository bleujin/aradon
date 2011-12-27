/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Exception thrown when no <code>Item</code> exists at the specified path or
 * when the specified path implies intermediary <code>Node</code>s that do not
 * exist.
 */
public class PathNotFoundException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public PathNotFoundException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public PathNotFoundException(String message) {
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
    public PathNotFoundException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public PathNotFoundException(Throwable rootCause) {
        super(rootCause);
    }
}
