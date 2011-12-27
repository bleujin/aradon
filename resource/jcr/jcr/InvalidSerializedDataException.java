/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * Exception thrown by the deserialization methods of <code>Session</code> if
 * the serialized data being input has an invalid format.
 */
public class InvalidSerializedDataException extends RepositoryException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public InvalidSerializedDataException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public InvalidSerializedDataException(String message) {
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
    public InvalidSerializedDataException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public InvalidSerializedDataException(Throwable rootCause) {
        super(rootCause);
    }
}
