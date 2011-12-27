/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Exception thrown when an attempt is made to assign a value to a property that
 * has an invalid format, given the type of the property. Also thrown if an
 * attempt is made to read the value of a property using a type-specific read
 * method of a type into which it is not convertible.
 */
public class ValueFormatException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public ValueFormatException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public ValueFormatException(String message) {
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
    public ValueFormatException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public ValueFormatException(Throwable rootCause) {
        super(rootCause);
    }
}
