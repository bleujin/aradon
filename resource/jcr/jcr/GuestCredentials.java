/*
 * Copyright 2008 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * <code>GuestCredentials</code> implements the <code>Credentials</code>
 * interface and is used to obtain a "guest", "public" or "anonymous" session.
 * Note that the characteristics of the session created from the
 * <code>GuestCredentials</code> remain implementation specific.
 *
 * @since JCR 2.0
 */
public final class GuestCredentials implements Credentials {

    /**
     * The constructor creates a new <code>GuestCredentials</code> object.
     */
    public GuestCredentials() {
    }
}
