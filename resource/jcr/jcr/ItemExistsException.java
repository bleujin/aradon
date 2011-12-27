/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * An exception thrown when an attempt is made to place an item in a position
 * where another item already exists.
 */
public class ItemExistsException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public ItemExistsException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public ItemExistsException(String message) {
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
    public ItemExistsException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public ItemExistsException(Throwable rootCause) {
        super(rootCause);
    }
}
