/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.version;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * A <code>Version</code> object wraps an <code>nt:version</code> node. It
 * provides convenient access to version information.
 */
public interface Version extends Node {

    /**
     * Returns the <code>VersionHistory</code> that contains this
     * <code>Version</code>.
     *
     * @return the <code>VersionHistory</code> that contains this
     *         <code>Version</code>.
     * @throws RepositoryException if an error occurs.
     */
    public VersionHistory getContainingHistory() throws RepositoryException;

    /**
     * Returns the date this version was created. This corresponds to the value
     * of the <code>jcr:created</code> property in the <code>nt:version</code>
     * node that represents this version.
     *
     * @return a <code>Calendar</code> object
     * @throws RepositoryException if an error occurs.
     */
    public Calendar getCreated() throws RepositoryException;

    /**
     * Assuming that this <code>Version</code> object was acquired through a
     * <code>Workspace</code> <code>W</code> and is within the
     * <code>VersionHistory</code> <code>H</code>, this method returns the
     * successor of this version along the same line of descent as is returned
     * by <code>H.getAllLinearVersions()</code> where <code>H</code> was also
     * acquired through <code>W</code>.
     * <p>
     * Note that under simple versioning the behavior of this method is
     * equivalent to getting the unique successor (if any) of this version.
     *
     * @return a <code>Version</code> or <code>null</code> if no linear
     *         successor exists.
     * @throws RepositoryException if an error occurs.
     * @see VersionHistory#getAllLinearVersions
     */
    public Version getLinearSuccessor() throws RepositoryException;

    /**
     * Returns the successor versions of this version. This corresponds to
     * returning all the <code>nt:version</code> nodes referenced by the
     * <code>jcr:successors</code> multi-value property in the
     * <code>nt:version</code> node that represents this version.
     * <p>
     * In a simple versioning repository this method
     *
     * @return a <code>Version</code> array.
     * @throws RepositoryException if an error occurs.
     */
    public Version[] getSuccessors() throws RepositoryException;

    /**
     * Assuming that this <code>Version</code> object was acquired through a
     * <code>Workspace</code> <code>W</code> and is within the
     * <code>VersionHistory</code> <code>H</code>, this method returns the
     * predecessor of this version along the same line of descent as is returned
     * by <code>H.getAllLinearVersions()</code> where <code>H</code> was also
     * acquired through <code>W</code>.
     * <p>
     * Note that under simple versioning the behavior of this method is
     * equivalent to getting the unique predecessor (if any) of this version.
     *
     * @return a <code>Version</code> or <code>null</code> if no linear
     *         predecessor exists.
     * @throws RepositoryException if an error occurs.
     * @see VersionHistory#getAllLinearVersions
     */
    public Version getLinearPredecessor() throws RepositoryException;

    /**
     * In both simple and full versioning repositories, this method returns the
     * predecessor versions of this version. This corresponds to returning all
     * the <code>nt:version</code> nodes whose <code>jcr:successors</code>
     * property includes a reference to the <code>nt:version</code> node that
     * represents this version.
     *
     * @return a <code>Version</code> array.
     * @throws RepositoryException if an error occurs.
     */
    public Version[] getPredecessors() throws RepositoryException;

    /**
     * Returns the frozen node of this version.
     *
     * @return a <code>Node</code> object
     * @throws RepositoryException if an error occurs.
     * @since JCR 2.0
     */
    public Node getFrozenNode() throws RepositoryException;
}