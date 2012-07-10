/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

import javax.jcr.security.AccessControlException;

/**
 * Exception thrown by access-related methods.
 */
public class AccessDeniedException extends AccessControlException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public AccessDeniedException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public AccessDeniedException(String message) {
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
    public AccessDeniedException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public AccessDeniedException(Throwable rootCause) {
        super(rootCause);
    }
}
