/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Exception thrown by <code>{@link Repository#login(Credentials,
 * String)}</code> when a specific workspace is not found.
 */
public class NoSuchWorkspaceException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public NoSuchWorkspaceException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public NoSuchWorkspaceException(String message) {
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
    public NoSuchWorkspaceException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public NoSuchWorkspaceException(Throwable rootCause) {
        super(rootCause);
    }
}
