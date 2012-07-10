/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

import javax.jcr.Value;

/**
 * A <code>NodeType</code> object represents a "live" node type that is
 * registered in the repository.
 */
public interface NodeType extends NodeTypeDefinition {

    /**
     * A constant for the node type name <code>nt:base</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>nt:base</code> are: <ul> <li>{@link javax.jcr.Property#JCR_PRIMARY_TYPE}</li>
     * <li>{@link javax.jcr.Property#JCR_MIXIN_TYPES}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_BASE = "{http://www.jcp.org/jcr/nt/1.0}base";

    /**
     * A constant for the node type name <code>nt:hierarchyNode</code> (in
     * expanded form).
     *
     * @since JCR 2.0
     */
    public static final String NT_HIERARCHY_NODE = "{http://www.jcp.org/jcr/nt/1.0}hierarchyNode";

    /**
     * A constant for the node type name <code>nt:folder</code> (in expanded
     * form).
     *
     * @since JCR 2.0
     */
    public static final String NT_FOLDER = "{http://www.jcp.org/jcr/nt/1.0}folder";

    /**
     * A constant for the node type name <code>nt:file</code> (in expanded
     * form). A constant for the name of the child node declared by
     * <code>nt:file</code> is: <ul> <li>{@link javax.jcr.Node#JCR_CONTENT}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_FILE = "{http://www.jcp.org/jcr/nt/1.0}file";

    /**
     * A constant for the node type name <code>nt:linkedFile</code> (in expanded
     * form). A constant for the name of the property declared by
     * <code>nt:linkedFile</code> is: <ul> <li>{@link javax.jcr.Property#JCR_CONTENT}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_LINKED_FILE = "{http://www.jcp.org/jcr/nt/1.0}linkedFile";

    /**
     * A constant for the node type name <code>nt:resource</code> (in expanded
     * form). A constant for the name of the property declared by
     * <code>nt:resource</code> is: <ul> <li>{@link javax.jcr.Property#JCR_DATA}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_RESOURCE = "{http://www.jcp.org/jcr/nt/1.0}resource";

    /**
     * A constant for the node type name <code>nt:unstructured</code> (in
     * expanded form).
     *
     * @since JCR 2.0
     */
    public static final String NT_UNSTRUCTURED = "{http://www.jcp.org/jcr/nt/1.0}unstructured";

    /**
     * A constant for the node type name <code>nt:address</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>nt:base</code> are: <ul> <li>{@link javax.jcr.Property#JCR_PROTOCOL}</li>
     * <li>{@link javax.jcr.Property#JCR_HOST}</li> <li>{@link
     * javax.jcr.Property#JCR_PORT}</li> <li>{@link javax.jcr.Property#JCR_REPOSITORY}</li>
     * <li>{@link javax.jcr.Property#JCR_WORKSPACE}</li> <li>{@link
     * javax.jcr.Property#JCR_PATH}</li> <li>{@link javax.jcr.Property#JCR_ID}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_ADDRESS = "{http://www.jcp.org/jcr/nt/1.0}address";

    /**
     * A constant for the node type name <code>mix:referenceable</code> (in
     * expanded form). A constant for the name of the property declared by
     * <code>mix:referenceable</code> is: <ul> <li>{@link
     * javax.jcr.Property#JCR_UUID}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_REFERENCEABLE = "{http://www.jcp.org/jcr/mix/1.0}referenceable";

    /**
     * A constant for the node type name <code>mix:title</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>mix:title</code> are: <ul> <li>{@link javax.jcr.Property#JCR_TITLE}</li>
     * <li>{@link javax.jcr.Property#JCR_DESCRIPTION}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_TITLE = "{http://www.jcp.org/jcr/mix/1.0}title";

    /**
     * A constant for the node type name <code>mix:created</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>mix:created</code> are: <ul> <li>{@link javax.jcr.Property#JCR_CREATED}</li>
     * <li>{@link javax.jcr.Property#JCR_CREATED_BY}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_CREATED = "{http://www.jcp.org/jcr/mix/1.0}created";

    /**
     * A constant for the node type name <code>mix:lastModified</code> (in
     * expanded form). Constants for the names of the properties declared by
     * <code>mix:lastModified</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_LAST_MODIFIED}</li> <li>{@link
     * javax.jcr.Property#JCR_LAST_MODIFIED_BY}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_LAST_MODIFIED = "{http://www.jcp.org/jcr/mix/1.0}lastModified";

    /**
     * A constant for the node type name <code>mix:language</code> (in expanded
     * form). A constant for the name of the property declared by
     * <code>mix:language</code> is: <ul> <li>{@link javax.jcr.Property#JCR_LANGUAGE}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_LANGUAGE = "{http://www.jcp.org/jcr/mix/1.0}language";

    /**
     * A constant for the node type name <code>mix:mimeType</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>mix:mimeType</code> are: <ul> <li>{@link javax.jcr.Property#JCR_MIMETYPE}</li>
     * <li>{@link javax.jcr.Property#JCR_ENCODING}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_MIMETYPE = "{http://www.jcp.org/jcr/mix/1.0}mimeType";

    /**
     * A constant for the node type name <code>nt:nodeType</code> (in expanded
     * form). Constants for the names of the child items declared by
     * <code>nt:nodeType</code> are: <ul> <li>{@link javax.jcr.Property#JCR_NODE_TYPE_NAME}</li>
     * <li>{@link javax.jcr.Property#JCR_SUPERTYPES}</li> <li>{@link
     * javax.jcr.Property#JCR_IS_ABSTRACT}</li> <li>{@link
     * javax.jcr.Property#JCR_IS_MIXIN}</li> <li>{@link javax.jcr.Property#JCR_HAS_ORDERABLE_CHILD_NODES}</li>
     * <li>{@link javax.jcr.Property#JCR_PRIMARY_ITEM_NAME}</li> <li>{@link
     * javax.jcr.Node#JCR_PROPERTY_DEFINITION}</li> <li>{@link
     * javax.jcr.Node#JCR_CHILD_NODE_DEFINITION}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_NODE_TYPE = "{http://www.jcp.org/jcr/nt/1.0}nodeType";

    /**
     * A constant for the node type name <code>nt:propertyDefinition</code> (in
     * expanded form). Constants for the names of the properties declared by
     * <code>nt:propertyDefinition</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_NAME}</li> <li>{@link javax.jcr.Property#JCR_AUTOCREATED}</li>
     * <li>{@link javax.jcr.Property#JCR_MANDATORY}</li> <li>{@link
     * javax.jcr.Property#JCR_PROTECTED}</li> <li>{@link
     * javax.jcr.Property#JCR_ON_PARENT_VERSION}</li> <li>{@link
     * javax.jcr.Property#JCR_REQUIRED_TYPE}</li> <li>{@link
     * javax.jcr.Property#JCR_VALUE_CONSTRAINTS}</li> <li>{@link
     * javax.jcr.Property#JCR_DEFAULT_VALUES}</li> <li>{@link
     * javax.jcr.Property#JCR_MULTIPLE}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_PROPERTY_DEFINITION = "{http://www.jcp.org/jcr/nt/1.0}propertyDefinition";

    /**
     * A constant for the node type name <code>nt:childNodeDefinition</code> (in
     * expanded form). Constants for the names of the properties declared by
     * <code>nt:childNodeDefinition</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_NAME}</li> <li>{@link javax.jcr.Property#JCR_AUTOCREATED}</li>
     * <li>{@link javax.jcr.Property#JCR_MANDATORY}</li> <li>{@link
     * javax.jcr.Property#JCR_PROTECTED}</li> <li>{@link
     * javax.jcr.Property#JCR_ON_PARENT_VERSION}</li> <li>{@link
     * javax.jcr.Property#JCR_REQUIRED_PRIMARY_TYPES}</li> <li>{@link
     * javax.jcr.Property#JCR_DEFAULT_PRIMARY_TYPE}</li> <li>{@link
     * javax.jcr.Property#JCR_SAME_NAME_SIBLINGS}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_CHILD_NODE_DEFINITION = "{http://www.jcp.org/jcr/nt/1.0}childNodeDefinition";

    /**
     * A constant for the node type name <code>mix:shareable</code> (in expanded
     * form).
     *
     * @since JCR 2.0
     */
    public static final String MIX_SHAREABLE = "{http://www.jcp.org/jcr/mix/1.0}shareable";

    /**
     * A constant for the node type name <code>mix:lockable</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>mix:lockable</code> are: <ul> <li>{@link javax.jcr.Property#JCR_LOCK_OWNER}</li>
     * <li>{@link javax.jcr.Property#JCR_LOCK_IS_DEEP}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_LOCKABLE = "{http://www.jcp.org/jcr/mix/1.0}lockable";

    /**
     * A constant for the node type name <code>mix:lifecycle</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>mix:lifecycle</code> are: <ul> <li>{@link javax.jcr.Property#JCR_LIFECYCLE_POLICY}</li>
     * <li>{@link javax.jcr.Property#JCR_CURRENT_LIFECYCLE_STATE}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_LIFECYCLE = "{http://www.jcp.org/jcr/mix/1.0}lifecycle";

    /**
     * A constant for the node type name <code>mix:simpleVersionable</code> (in
     * expanded form). A constant for the name of the property declared by
     * <code>mix:simpleVersionable</code> is: <ul> <li>{@link
     * javax.jcr.Property#JCR_IS_CHECKED_OUT}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_SIMPLE_VERSIONABLE = "{http://www.jcp.org/jcr/mix/1.0}simpleVersionable";

    /**
     * A constant for the node type name <code>mix:versionable</code> (in
     * expanded form). Constants for the names of the properties declared by
     * <code>mix:versionable</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_VERSION_HISTORY}</li> <li>{@link
     * javax.jcr.Property#JCR_BASE_VERSION}</li> <li>{@link
     * javax.jcr.Property#JCR_PREDECESSORS}</li> <li>{@link
     * javax.jcr.Property#JCR_MERGE_FAILED}</li> <li>{@link
     * javax.jcr.Property#JCR_ACTIVITY}</li> <li>{@link javax.jcr.Property#JCR_CONFIGURATION}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String MIX_VERSIONABLE = "{http://www.jcp.org/jcr/mix/1.0}versionable";

    /**
     * A constant for the node type name <code>nt:versionHistory</code> (in
     * expanded form). Constants for the names of the child items declared by
     * <code>nt:versionHistory</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_VERSIONABLE_UUID}</li> <li>{@link
     * javax.jcr.Property#JCR_COPIED_FROM}</li> <li>{@link
     * javax.jcr.Node#JCR_ROOT_VERSION}</li> <li>{@link javax.jcr.Node#JCR_VERSION_LABELS}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_VERSION_HISTORY = "{http://www.jcp.org/jcr/nt/1.0}versionHistory";

    /**
     * A constant for the node type name <code>nt:version</code> (in expanded
     * form). Constants for the names of the child items declared by
     * <code>nt:version</code> are: <ul> <li>{@link javax.jcr.Property#JCR_CREATED}</li>
     * <li>{@link javax.jcr.Property#JCR_PREDECESSORS}</li> <li>{@link
     * javax.jcr.Property#JCR_SUCCESSORS}</li> <li>{@link
     * javax.jcr.Property#JCR_ACTIVITY}</li> <li>{@link javax.jcr.Node#JCR_FROZEN_NODE}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_VERSION = "{http://www.jcp.org/jcr/nt/1.0}version";

    /**
     * A constant for the node type name <code>nt:frozenNode</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>nt:frozenNode</code> are: <ul> <li>{@link javax.jcr.Property#JCR_FROZEN_PRIMARY_TYPE}</li>
     * <li>{@link javax.jcr.Property#JCR_FROZEN_MIXIN_TYPES}</li> <li>{@link
     * javax.jcr.Property#JCR_FROZEN_UUID}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_FROZEN_NODE = "{http://www.jcp.org/jcr/nt/1.0}frozenNode";

    /**
     * A constant for the node type name <code>nt:versionedChild</code> (in
     * expanded form). A constant for the name of the property declared by
     * <code>nt:versionedChild</code> is: <ul> <li>{@link
     * javax.jcr.Property#JCR_CHILD_VERSION_HISTORY}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_VERSIONED_CHILD = "{http://www.jcp.org/jcr/nt/1.0}versionedChild";

    /**
     * A constant for the node type name <code>nt:activity</code> (in expanded
     * form). A constant for the name of the property declared by
     * <code>nt:activity</code> is: <ul> <li>{@link javax.jcr.Property#JCR_TITLE}</li>
     * </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_ACTIVITY = "{http://www.jcp.org/jcr/nt/1.0}activity";

    /**
     * A constant for the node type name <code>nt:configuration</code> (in
     * expanded form). A constant for the name of the property declared by
     * <code>nt:configuration</code> are: <ul> <li>{@link
     * javax.jcr.Property#JCR_ROOT}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_CONFIGURATION = "{http://www.jcp.org/jcr/nt/1.0}configuration";

    /**
     * A constant for the node type name <code>nt:query</code> (in expanded
     * form). Constants for the names of the properties declared by
     * <code>nt:query</code> are: <ul> <li>{@link javax.jcr.Property#JCR_STATEMENT}</li>
     * <li>{@link javax.jcr.Property#JCR_LANGUAGE}</li> </ul>
     *
     * @since JCR 2.0
     */
    public static final String NT_QUERY = "{http://www.jcp.org/jcr/nt/1.0}query";

    /**
     * Returns all supertypes of this node type in the node type inheritance
     * hierarchy. For primary types apart from <code>nt:base</code>, this list
     * will always include at least <code>nt:base</code>. For mixin types, there
     * is no required supertype.
     *
     * @return an array of <code>NodeType</code> objects.
     * @see #getDeclaredSupertypes
     */
    public NodeType[] getSupertypes();

    /**
     * Returns the <i>direct</i> supertypes of this node type in the node type
     * inheritance hierarchy, that is, those actually declared in this node
     * type. In single-inheritance systems, this will always be an array of size
     * 0 or 1. In systems that support multiple inheritance of node types this
     * array may be of size greater than 1.
     *
     * @return an array of <code>NodeType</code> objects.
     * @see #getSupertypes
     */
    public NodeType[] getDeclaredSupertypes();

    /**
     * Returns all subtypes of this node type in the node type inheritance
     * hierarchy.
     *
     * @return a <code>NodeTypeIterator</code>.
     * @see #getDeclaredSubtypes
     * @since JCR 2.0
     */
    public NodeTypeIterator getSubtypes();

    /**
     * Returns the <i>direct</i> subtypes of this node type in the node type
     * inheritance hierarchy, that is, those which actually declared this node
     * type in their list of supertypes.
     *
     * @return an <code>NodeTypeIterator</code>.
     * @see #getSubtypes
     * @since JCR 2.0
     */
    public NodeTypeIterator getDeclaredSubtypes();

    /**
     * Returns <code>true</code> if the name of <i>this</i> node type or any of
     * its direct or indirect supertypes is equal to <code>nodeTypeName</code>,
     * otherwise returns <code>false</code>.
     *
     * @param nodeTypeName the name of a node type.
     * @return a boolean
     */
    public boolean isNodeType(String nodeTypeName);

    /**
     * Returns an array containing the property definitions of this node type.
     * This includes both those property definitions actually declared in this
     * node type and those inherited from the supertypes of this type.
     *
     * @return an array containing the property definitions.
     * @see #getDeclaredPropertyDefinitions()
     */
    public PropertyDefinition[] getPropertyDefinitions();

    /**
     * Returns an array containing the child node definitions of this node type.
     * This includes both those child node definitions actually declared in this
     * node type and those inherited from the supertypes of this node type.
     *
     * @return an array containing the child node definitions.
     * @see #getDeclaredChildNodeDefinitions()
     */
    public NodeDefinition[] getChildNodeDefinitions();

    /**
     * Returns <code>true</code> if setting <code>propertyName</code> to
     * <code>value</code> is allowed by this node type. Otherwise returns
     * <code>false</code>.
     *
     * @param propertyName The name of the property
     * @param value        A <code>Value</code> object.
     * @return a boolean
     */
    public boolean canSetProperty(String propertyName, Value value);

    /**
     * Returns <code>true</code> if setting <code>propertyName</code> to
     * <code>values</code> is allowed by this node type. Otherwise returns
     * <code>false</code>.
     *
     * @param propertyName The name of the property
     * @param values       A <code>Value</code> array.
     * @return a boolean
     */
    public boolean canSetProperty(String propertyName, Value[] values);

    /**
     * Returns <code>true</code> if this node type allows the addition of a
     * child node called <code>childNodeName</code> without specific node type
     * information (that is, given the definition of this parent node type, the
     * child node name is sufficient to determine the intended child node type).
     * Returns <code>false</code> otherwise.
     *
     * @param childNodeName The name of the child node.
     * @return a boolean
     */
    public boolean canAddChildNode(String childNodeName);

    /**
     * Returns <code>true</code> if this node type allows the addition of a
     * child node called <code>childNodeName</code> of node type
     * <code>nodeTypeName</code>. Returns <code>false</code> otherwise.
     *
     * @param childNodeName The name of the child node.
     * @param nodeTypeName  The name of the node type of the child node.
     * @return a boolean
     */
    public boolean canAddChildNode(String childNodeName, String nodeTypeName);

    /**
     * Returns <code>true</code> if removing the child item called
     * <code>itemName</code> is allowed by this node type. Returns
     * <code>false</code> otherwise.
     *
     * @param itemName The name of the child item
     * @return a boolean
     * @deprecated As of JCR 2.0, clients should use {@link #canRemoveNode(String)}
     *             and {@link #canRemoveProperty(String)} instead.
     */
    public boolean canRemoveItem(String itemName);

    /**
     * Returns <code>true</code> if removing the child node called
     * <code>nodeName</code> is allowed by this node type. Returns
     * <code>false</code> otherwise.
     *
     * @param nodeName The name of the child node
     * @return a boolean
     * @since JCR 2.0
     */
    public boolean canRemoveNode(String nodeName);

    /**
     * Returns <code>true</code> if removing the property called
     * <code>propertyName</code> is allowed by this node type. Returns
     * <code>false</code> otherwise.
     *
     * @param propertyName The name of the property
     * @return a boolean
     * @since JCR 2.0
     */
    public boolean canRemoveProperty(String propertyName);
}