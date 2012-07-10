/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

import java.util.HashMap;

/**
 * <code>SimpleCredentials</code> implements the <code>Credentials</code>
 * interface and represents simple user ID/password credentials.
 */
public final class SimpleCredentials implements Credentials {

    private final String userID;
    private final char[] password;
    private final HashMap attributes = new HashMap();

    /**
     * The constructor creates a new <code>SimpleCredentials</code> object,
     * given a user ID and password.
     * <p>
     * Note that the given password is cloned before it is stored in the new
     * <code>SimpleCredentials</code> object. This should avoid the risk of
     * having unnecessary references to password data lying around in memory.
     *
     * @param userID   the user ID
     * @param password the user's password
     */
    public SimpleCredentials(String userID, char[] password) {
        this.userID = userID;
        this.password = (char[]) password.clone();
    }

    /**
     * Returns the user password.
     * <p>
     * Note that this method returns a reference to the password. It is the
     * caller's responsibility to zero out the password information after it is
     * no longer needed.
     *
     * @return the password
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Stores an attribute in this credentials instance.
     *
     * @param name  a <code>String</code> specifying the name of the attribute
     * @param value the <code>Object</code> to be stored
     */
    public void setAttribute(String name, Object value) {
        // name cannot be null
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        // null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        synchronized (attributes) {
            attributes.put(name, value);
        }
    }

    /**
     * Returns the value of the named attribute as an <code>Object</code>, or
     * <code>null</code> if no attribute of the given name exists.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     * @return an <code>Object</code> containing the value of the attribute, or
     *         <code>null</code> if the attribute does not exist
     */
    public Object getAttribute(String name) {
        synchronized (attributes) {
            return (attributes.get(name));
        }
    }

    /**
     * Removes an attribute from this credentials instance.
     *
     * @param name a <code>String</code> specifying the name of the attribute to
     *             remove
     */
    public void removeAttribute(String name) {
        synchronized (attributes) {
            attributes.remove(name);
        }
    }

    /**
     * Returns the names of the attributes available to this credentials
     * instance. This method returns an empty array if the credentials instance
     * has no attributes available to it.
     *
     * @return a string array containing the names of the stored attributes
     */
    public String[] getAttributeNames() {
        synchronized (attributes) {
            return (String[]) attributes.keySet().toArray(new String[attributes.keySet().size()]);
        }
    }
}
