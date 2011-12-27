/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Exception thrown by the write methods of {@link Node} and {@link Property}
 * and by {@link Session#save} and {@link Session#refresh} if an attempted
 * change would conflict with a change to the persistent workspace made through
 * another {@link Session}. Also thrown by methods of <code>Node</code> and
 * <code>Property</code> if that object represents an item that has been removed
 * from the workspace.
 */
public class InvalidItemStateException extends RepositoryException {

    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public InvalidItemStateException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public InvalidItemStateException(String message) {
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
    public InvalidItemStateException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public InvalidItemStateException(Throwable rootCause) {
        super(rootCause);
    }
}