/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.version;

/**
 * Exception will be thrown by <code>Node.checkout</code> and
 * <code>Node.checkpoint</code> if an activity A is present on the current
 * session and any of the following conditions is met: <ul> <li>There already is
 * a node in another workspace that has a checked-out node for the version
 * history of N whose jcr:activity references A.</li> <li>There is a version in
 * the version history of N that is not a predecessor of N but whose
 * jcr:activity references A</li>. </ul>
 *
 * @since JCR 2.0
 */
public class ActivityViolationException extends VersionException {
    /**
     * Constructs a new instance of this class with <code>null</code> as its
     * detail message.
     */
    public ActivityViolationException() {
        super();
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public ActivityViolationException(String message) {
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
    public ActivityViolationException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause root failure cause
     */
    public ActivityViolationException(Throwable rootCause) {
        super(rootCause);
    }
}
