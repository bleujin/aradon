/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.lock;

import javax.jcr.RepositoryException;

/**
 * Exception thrown by when a lock-related error occurs.
 */
public class LockException extends RepositoryException {

    /*
     * Absolute path of the node that caused the error, in normalized, standard form,
     * that is, each path segment must be a JCR name in qualified form,
     * the path must have no trailing slash, no self or parent segments and
     * no [1] indexes.
     */
    final private String failureNodePath;

    /**
     * Constructs a new instance of this class.
     */
    public LockException() {
        super();
        this.failureNodePath = null;
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public LockException(String message) {
        super(message);
        this.failureNodePath = null;
    }

    /**
     * Constructs a new instance of this class with the specified root cause.
     *
     * @param rootCause the root failure cause.
     */
    public LockException(Throwable rootCause) {
        super(rootCause);
        this.failureNodePath = null;
    }

    /**
     * Constructs a new instance of this class with the specified detail message
     * and root cause.
     *
     * @param message   the detail message.
     * @param rootCause the root failure cause.
     */
    public LockException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.failureNodePath = null;
    }

    /**
     * Constructs a new instance of this class with the specified detail
     * message, root cause and failure node path.
     *
     * @param message         the detail message.
     * @param rootCause       the root failure cause.
     * @param failureNodePath the absolute path of the node that caused the
     *                        error or <code>null</code> if the implementation chooses not to, or
     *                        cannot, return a path.
     *                        <p>
     *                        If a path is passed it must be an absolute path in normalized, standard
     *                        form, that is, each path segment must be a JCR name in qualified form,
     *                        the path must have no trailing slash, no self or parent segments and no
     *                        [1] indexes.
     */
    public LockException(String message, Throwable rootCause, String failureNodePath) {
        super(message, rootCause);
        this.failureNodePath = failureNodePath;
    }

    /**
     * Returns the absolute path of the node that caused the error or
     * <code>null</code> if the implementation chooses not to, or cannot, return
     * a path.
     *
     * @return path of the node that caused the error
     */
    public String getFailureNodePath() {
        return this.failureNodePath;
    }
}