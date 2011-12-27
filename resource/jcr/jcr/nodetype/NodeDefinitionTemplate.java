/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

/**
 * The <code>NodeDefinitionTemplate</code> interface extends
 * <code>NodeDefinition</code> with the addition of write methods, enabling the
 * characteristics of a child node definition to be set, after which the
 * <code>NodeDefinitionTemplate</code> is added to a <code>NodeTypeTemplate</code>.
 * <p>
 * See the corresponding <code>get</code> methods for each attribute in
 * <code>NodeDefinition</code> for the default values assumed when a new empty
 * <code>NodeDefinitionTemplate</code> is created (as opposed to one extracted
 * from an existing <code>NodeType</code>).
 *
 * @since JCR 2.0
 */
public interface NodeDefinitionTemplate extends NodeDefinition {

    /**
     * Sets the name of the node. This must be a JCR name in either
     * qualified or expanded form.
     *
     * @param name a JCR name.
     * @throws ConstraintViolationException if <code>name</code> is not a
     * syntactically valid JCR name in either qualified or expanded form.
     */
    public void setName(String name) throws ConstraintViolationException;

    /**
     * Sets the auto-create status of the node.
     *
     * @param autoCreated a <code>boolean</code>.
     */
    public void setAutoCreated(boolean autoCreated);

    /**
     * Sets the mandatory status of the node.
     *
     * @param mandatory a <code>boolean</code>.
     */
    public void setMandatory(boolean mandatory);

    /**
     * Sets the on-parent-version status of the node.
     *
     * @param opv an <code>int</code> constant member of
     *            <code>OnParentVersionAction</code>.
     */
    public void setOnParentVersion(int opv);

    /**
     * Sets the protected status of the node.
     *
     * @param protectedStatus a <code>boolean</code>.
     */
    public void setProtected(boolean protectedStatus);

    /**
     * Sets the names of the required primary types of this node.
     * These must be a JCR names in either qualified or expanded form.
     *
     * @param names an array of JCR names.
     * @throws ConstraintViolationException if <code>names</code> includes a
     * name that is not a syntactically valid JCR name in either qualified or expanded form.
     */
    public void setRequiredPrimaryTypeNames(String[] names) throws ConstraintViolationException;

    /**
     * Sets the name of the default primary type of this node.
     * This must be a JCR name in either qualified or expanded form.
     *
     * @param name a JCR name.
     * @throws ConstraintViolationException if <code>name</code> is not a
     * syntactically valid JCR name in either qualified or expanded form.
     */
    public void setDefaultPrimaryTypeName(String name) throws ConstraintViolationException;


    /**
     * Sets the same-name sibling status of this node.
     *
     * @param allowSameNameSiblings a <code>boolean</code>.
     */
    public void setSameNameSiblings(boolean allowSameNameSiblings);
}
