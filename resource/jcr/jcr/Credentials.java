/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

import java.io.Serializable;

/**
 * Interface for all credentials that may be passed to the {@link
 * Repository#login(Credentials credentials, String workspaceName)} method.
 * Serves as a marker interface that all repositories must implement when
 * providing a credentials class. See {@link SimpleCredentials} and {@link
 * GuestCredentials} for examples of such a class.
 */
public interface Credentials extends Serializable {
}