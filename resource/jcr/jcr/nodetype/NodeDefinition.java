/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

/**
 * A node definition. Used in node type definitions.
 *
 * @see NodeType#getChildNodeDefinitions
 * @see javax.jcr.Node#getDefinition
 */
public interface NodeDefinition extends ItemDefinition {

    /**
     * Gets the minimum set of primary node types that the child node must have.
     * Returns an array to support those implementations with multiple
     * inheritance. This method never returns an empty array. If this node
     * definition places no requirements on the primary node type, then this
     * method will return an array containing only the <code>NodeType</code>
     * object representing <code>nt:base</code>, which is the base of all
     * primary node types and therefore constitutes the least restrictive node
     * type requirement. Note that any particular node instance still has only
     * one assigned primary node type, but in multiple-inheritance-supporting
     * implementations the <code>RequiredPrimaryTypes</code> attribute can be
     * used to restrict that assigned node type to be a subtype of <i>all</i> of
     * a specified set of node types.
     * <p>
     * In implementations that support node type registration an
     * <code>NodeDefinition</code> object may be acquired (in the form of a
     * <code>NodeDefinitionTemplate</code>) that is not attached to a live
     * <code>NodeType</code>. In such cases this method returns
     * <code>null</code>.
     *
     * @return an array of <code>NodeType</code> objects.
     */
    public NodeType[] getRequiredPrimaryTypes();

    /**
     * Returns the names of the required primary node types.
     * <p>
     * If this <code>NodeDefinition</code> is acquired from a live
     * <code>NodeType</code> this list will reflect the node types returned by
     * <code>getRequiredPrimaryTypes</code>, above.
     * <p>
     * If this <code>NodeDefinition</code> is actually a
     * <code>NodeDefinitionTemplate</code> that is not part of a registered node
     * type, then this method will return the required primary types as set in
     * that template. If that template is a newly-created empty one, then this
     * method will return </code>null<code>.
     *
     * @return a String array
     * @since JCR 2.0
     */
    public String[] getRequiredPrimaryTypeNames();

    /**
     * Gets the default primary node type that will be assigned to the child
     * node if it is created without an explicitly specified primary node type.
     * This node type must be a subtype of (or the same type as) the node types
     * returned by <code>getRequiredPrimaryTypes</code>.
     * <p>
     * If <code>null</code> is returned this indicates that no default primary
     * type is specified and that therefore an attempt to create this node
     * without specifying a node type will throw a <code>ConstraintViolationException</code>.
     * <p>
     * In implementations that support node type registration an
     * <code>NodeDefinition</code> object may be acquired (in the form of a
     * <code>NodeDefinitionTemplate</code>) that is not attached to a live
     * <code>NodeType</code>. In such cases this method returns
     * <code>null</code>.
     *
     * @return a <code>NodeType</code>.
     */
    public NodeType getDefaultPrimaryType();

    /**
     * Returns the name of the default primary node type.
     * <p>
     * If this <code>NodeDefinition</code> is acquired from a live
     * <code>NodeType</code> this list will reflect the NodeType returned by
     * getDefaultPrimaryType, above.
     * <p>
     * If this <code>NodeDefinition</code> is actually a
     * <code>NodeDefinitionTemplate</code> that is not part of a registered node
     * type, then this method will return the required primary types as set in
     * that template. If that template is a newly-created empty one, then this
     * method will return <code>null</code>.
     *
     * @return a String
     * @since JCR 2.0
     */
    public String getDefaultPrimaryTypeName();

    /**
     * Reports whether this child node can have same-name siblings. In other
     * words, whether the parent node can have more than one child node of this
     * name.
     * <p>
     * If this <code>NodeDefinition</code> is actually a
     * <code>NodeDefinitionTemplate</code> that is not part of a registered node
     * type, then this method will return the same name siblings status as set
     * in that template. If that template is a newly-created empty one, then
     * this method will return <code>false</code>.
     *
     * @return a boolean.
     */
    public boolean allowsSameNameSiblings();
}