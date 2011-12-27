/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.version;

/**
 * Exception thrown by <code>VersionHistory.addVersionLabel</code> if
 * <code>moveLabel</code> is set to <code>false</code> and an attempt is made to
 * add a label that already exists in the <code>VersionHistory</code>.
 *
 * @since JCR 2.0
 */
public class LabelExistsVersionException extends VersionException {

    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public LabelExistsVersionException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public LabelExistsVersionException(String message) {
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
    public LabelExistsVersionException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public LabelExistsVersionException(Throwable rootCause) {
        super(rootCause);
    }
}

