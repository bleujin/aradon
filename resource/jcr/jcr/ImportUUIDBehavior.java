/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * The possible actions specified by the <code>uuidBehavior</code> parameter in
 * {@link Workspace#importXML}, {@link Session#importXML}, {@link
 * Workspace#getImportContentHandler} and {@link Session#getImportContentHandler}.
 */

public interface ImportUUIDBehavior {
    public static final int IMPORT_UUID_CREATE_NEW = 0;
    public static final int IMPORT_UUID_COLLISION_REMOVE_EXISTING = 1;
    public static final int IMPORT_UUID_COLLISION_REPLACE_EXISTING = 2;
    public static final int IMPORT_UUID_COLLISION_THROW = 3;
}