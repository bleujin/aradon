/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * The entry point into the content repository. The <code>Repository</code>
 * object is usually acquired through the {@link RepositoryFactory}.
 */
public interface Repository {

    /**
     * The descriptor key for the version of the specification that this
     * repository implements. For JCR 2.0 the value of this descriptor is the
     * <code>String</code> "2.0".
     */
    public static final String SPEC_VERSION_DESC = "jcr.specification.version";

    /**
     * The descriptor key for the name of the specification that this repository
     * implements. For JCR 2.0 the value of this descriptor is the
     * <code>String</code> "Content Repository for Java Technology API".
     */
    public static final String SPEC_NAME_DESC = "jcr.specification.name";

    /**
     * The descriptor key for the name of the repository vendor. The descriptor
     * returned for this key is a <code>String</code>.
     */
    public static final String REP_VENDOR_DESC = "jcr.repository.vendor";

    /**
     * The descriptor key for the URL of the repository vendor. The descriptor
     * returned for this key is a <code>String</code>.
     */
    public static final String REP_VENDOR_URL_DESC = "jcr.repository.vendor.url";

    /**
     * The descriptor key for the name of this repository implementation. The
     * descriptor returned for this key is a <code>String</code>.
     */
    public static final String REP_NAME_DESC = "jcr.repository.name";

    /**
     * The descriptor key for the version of this repository implementation. The
     * descriptor returned for this key is a <code>String</code>.
     */
    public static final String REP_VERSION_DESC = "jcr.repository.version";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if repository content can be updated through the JCR API (as
     * opposed to having read-only access).
     *
     * @since JCR 2.0
     */
    public static final String WRITE_SUPPORTED = "write.supported";

    /**
     * Key to a <code>String</code> descriptor. Returns one of the following
     * <code>javax.jcr.Repository</code> constants indicating the stability of
     * identifiers: <dl>
     * <p>
     * <dt>IDENTIFIER_STABILITY_METHOD_DURATION</dt> <dd>Identifiers may change
     * between method calls.</dd>
     * <p>
     * <dt>IDENTIFIER_STABILITY_SAVE_DURATION</dt> <dd>Identifiers are
     * guaranteed stable within a single save/refresh cycle.</dd>
     * <p>
     * <dt>IDENTIFIER_STABILITY_SESSION_DURATION</dt> <dd>Identifiers are
     * guaranteed stable within a single session.</dd>
     * <p>
     * <dt>IDENTIFIER_STABILITY_INDEFINITE_DURATION</dt> <dd>Identifiers are
     * guaranteed to be stable forever.</dd>
     * <p>
     * </dl>
     *
     * @since JCR 2.0
     */
    public static final String IDENTIFIER_STABILITY = "identifier.stability";

    /**
     * One of four possible values for the descriptor <code>IDENTIFIER_STABILITY</code>.
     * Indicates that identifiers may change between method calls.
     *
     * @since JCR 2.0
     */
    public static final String IDENTIFIER_STABILITY_METHOD_DURATION = "identifier.stability.method.duration";

    /**
     * One of four possible values for the descriptor <code>IDENTIFIER_STABILITY</code>.
     * Indicates that identifiers are guaranteed stable within a single
     * save/refresh cycle.
     *
     * @since JCR 2.0
     */
    public static final String IDENTIFIER_STABILITY_SAVE_DURATION = "identifier.stability.save.duration";

    /**
     * One of four possible values for the descriptor <code>IDENTIFIER_STABILITY</code>.
     * Indicates that identifiers are guaranteed stable within a single
     * session.
     *
     * @since JCR 2.0
     */
    public static final String IDENTIFIER_STABILITY_SESSION_DURATION = "identifier.stability.session.duration";

    /**
     * One of four possible values for the descriptor <code>IDENTIFIER_STABILITY</code>.
     * Indicates that identifiers are guaranteed to be stable forever.
     *
     * @since JCR 2.0
     */
    public static final String IDENTIFIER_STABILITY_INDEFINITE_DURATION = "identifier.stability.indefinite.duration";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if XML export is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_XML_EXPORT_SUPPORTED = "option.xml.export.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if XML import is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_XML_IMPORT_SUPPORTED = "option.xml.import.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if unfiled content is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_UNFILED_CONTENT_SUPPORTED = "option.unfiled.content.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if full versioning is supported.
     */
    public static final String OPTION_VERSIONING_SUPPORTED = "option.versioning.supported";


    public static final String OPTION_SIMPLE_VERSIONING_SUPPORTED = "option.simple.versioning.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if activities are supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_ACTIVITIES_SUPPORTED = "option.activities.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if configurations and baselines are supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_BASELINES_SUPPORTED = "option.baselines.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if access control is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_ACCESS_CONTROL_SUPPORTED = "option.access.control.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if locking is supported.
     */
    public static final String OPTION_LOCKING_SUPPORTED = "option.locking.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if asynchronous observation is supported.
     */
    public static final String OPTION_OBSERVATION_SUPPORTED = "option.observation.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if journaled observation is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_JOURNALED_OBSERVATION_SUPPORTED = "option.journaled.observation.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if retention and hold are supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_RETENTION_SUPPORTED = "option.retention.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if lifecycles are supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_LIFECYCLE_SUPPORTED = "option.lifecycle.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if transactions are supported.
     */
    public static final String OPTION_TRANSACTIONS_SUPPORTED = "option.transactions.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if workspace management is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_WORKSPACE_MANAGEMENT_SUPPORTED = "option.workspace.management.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if the primary node type of an existing node can be updated.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_UPDATE_PRIMARY_NODE_TYPE_SUPPORTED = "option.update.primary.node.type.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if the mixin node types of an existing node can be added and
     * removed.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_UPDATE_MIXIN_NODE_TYPES_SUPPORTED = "option.update.mixin.node.types.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if the creation of shareable nodes is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_SHAREABLE_NODES_SUPPORTED = "option.shareable.nodes.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if node type management is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_NODE_TYPE_MANAGEMENT_SUPPORTED = "option.node.type.management.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if node and property with same name is supported.
     *
     * @since JCR 2.0
     */
    public static final String OPTION_NODE_AND_PROPERTY_WITH_SAME_NAME_SUPPORTED = "option.node.and.property.with.same.name.supported";

    /**
     * Key to <code>String</code> descriptor. Returns one of the following
     * <code>javax.jcr.Repository</code> constants indicating the level of
     * support for node type inheritance: <dl> <dt>NODE_TYPE_MANAGEMENT_INHERITANCE_MINIMAL</dt>
     * <dd> Registration of primary node types is limited to those which have
     * only nt:base as supertype. Registration of mixin node types is limited to
     * those without any supertypes. </dd> <dt>NODE_TYPE_MANAGEMENT_INHERITANCE_SINGLE</dt>
     * <dd> Registration of primary node types is limited to those with exactly
     * one supertype. Registration of mixin node types is limited to those with
     * at most one supertype. </dd> <dt>NODE_TYPE_MANAGEMENT_INHERITANCE_MULTIPLE</dt>
     * <dd> Primary node types can be registered with one or more supertypes.
     * Mixin node types can be registered with zero or more supertypes. </dd>
     * </dl>
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_INHERITANCE = "node.type.management.inheritance";

    /**
     * One of three possible values for the descriptor <code>NODE_TYPE_MANAGEMENT_INHERITANCE</code>.
     * Indicates that registration of primary node types is limited to those
     * which have only nt:base as supertype. Registration of mixin node types is
     * limited to those without any supertypes.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_INHERITANCE_MINIMAL = "node.type.management.inheritance.minimal";

    /**
     * One of three possible values for the descriptor <code>NODE_TYPE_MANAGEMENT_INHERITANCE</code>.
     * Indicates that registration of primary node types is limited to those
     * with exactly one supertype. Registration of mixin node types is limited
     * to those with at most one supertype.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_INHERITANCE_SINGLE = "node.type.management.inheritance.single";

    /**
     * One of three possible values for the descriptor <code>NODE_TYPE_MANAGEMENT_INHERITANCE</code>.
     * Indicates that primary node types can be registered with one or more
     * supertypes. Mixin node types can be registered with zero or more
     * supertypes.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_INHERITANCE_MULTIPLE = "node.type.management.inheritance.multiple";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if override of inherited property or child node definitions is
     * supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_OVERRIDES_SUPPORTED = "node.type.management.overrides.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if primary items are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_PRIMARY_ITEM_NAME_SUPPORTED = "node.type.management.primary.item.name.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if preservation of child node ordering is supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_ORDERABLE_CHILD_NODES_SUPPORTED = "node.type.management.orderable.child.nodes.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if residual property and child node definitions are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_RESIDUAL_DEFINITIONS_SUPPORTED = "node.type.management.residual.definitions.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if autocreated properties and child nodes are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_AUTOCREATED_DEFINITIONS_SUPPORTED = "node.type.management.autocreated.definitions.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if same-name sibling child nodes are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_SAME_NAME_SIBLINGS_SUPPORTED = "node.type.management.same.name.siblings.supported";

    /**
     * Key to a <code>long[]</code> descriptor. Returns an array holding the
     * <code>javax.jcr.PropertyType</code> constants for the property types
     * (including <code>UNDEFINED</code>, if supported) that a registered node
     * type can specify, or a zero-length array if registered node types cannot
     * specify property definitions.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_PROPERTY_TYPES = "node.type.management.property.types";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if multivalue properties are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_MULTIVALUED_PROPERTIES_SUPPORTED = "node.type.management.multivalued.properties.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if registration of a node types with more than one
     * <code>BINARY</code> property is permitted.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_MULTIPLE_BINARY_PROPERTIES_SUPPORTED = "node.type.management.multiple.binary.properties.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns true if and only
     * value-constraints are supported.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_VALUE_CONSTRAINTS_SUPPORTED = "node.type.management.value.constraints.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns true if and only the
     * update of node types is supported for node types currently in use as the
     * type of an existing node in the repository.
     *
     * @since JCR 2.0
     */
    public static final String NODE_TYPE_MANAGEMENT_UPDATE_IN_USE_SUPORTED = "node.type.management.update.in.use.suported";

    /**
     * Key to a <code>String[]</code> descriptor. Returns an array holding the
     * constants representing the supported query languages, or a zero-length if
     * query is not supported.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_LANGUAGES = "query.languages";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if stored queries are supported.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_STORED_QUERIES_SUPPORTED = "query.stored.queries.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if full-text search is supported.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_FULL_TEXT_SEARCH_SUPPORTED = "query.full.text.search.supported";

    /**
     * Key to <code>String</code> descriptor. Returns one of the following
     * <code>javax.jcr.Repository</code> constants indicating the level of
     * support for joins in queries: <dl> <dt>QUERY_JOINS_NONE</dt> <dd>Joins
     * are not supported. Queries are limited to a single selector.</dd>
     * <dt>QUERY_JOINS_INNER</dt> <dd> Inner joins are supported.</dd>
     * <dt>QUERY_JOINS_INNER_OUTER</dt> <dd>Inner and outer joins are
     * supported.</dd> </dl>
     *
     * @since JCR 2.0
     */
    public static final String QUERY_JOINS = "query.joins";

    /**
     * One of three possible values for the descriptor <code>QUERY_JOINS
     * </code>. Indicates that joins are not supported. Queries are limited to a
     * single selector.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_JOINS_NONE = "query.joins.none";

    /**
     * One of three possible values for the descriptor <code>QUERY_JOINS
     * </code>. Indicates that inner joins are supported.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_JOINS_INNER = "query.joins.inner";

    /**
     * One of three possible values for the descriptor <code>QUERY_JOINS
     * </code>. Indicates that inner and outer joins are supported.
     *
     * @since JCR 2.0
     */
    public static final String QUERY_JOINS_INNER_OUTER = "query.joins.inner.outer";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if <ul> <li><code>OPTION_XML_EXPORT_SUPPORTED = true</code>
     * and</li> <li><code>QUERY_LANGUAGES</code> is of non-zero length.</li>
     * </ul> These semantics are identical to those in JCR 1.0. This constant is
     * deprecated.
     *
     * @deprecated As of JCR 2.0.
     */
    public static final String LEVEL_1_SUPPORTED = "level.1.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if <ul> <li><code>LEVEL_1_SUPPORTED = true</code>,</li>
     * <li><code>WRITE_SUPPORTED = true</code> and</li> <li><code>OPTION_XML_IMPORT_SUPPORTED
     * = true</code>.</li> </ul> These semantics are identical to those in JCR
     * 1.0. This constant is deprecated.
     *
     * @deprecated As of JCR 2.0.
     */
    public static final String LEVEL_2_SUPPORTED = "level.2.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns <code>true</code> if
     * and only if the (deprecated) JCR 1.0 XPath query language is supported.
     * This constant is deprecated.
     *
     * @deprecated As of JCR 2.0.
     */
    public static final String OPTION_QUERY_SQL_SUPPORTED = "option.query.sql.supported";

    /**
     * Key to a <code>boolean</code> descriptor. Returns false unless the
     * (deprecated) JCR 1.0 XPath query language is supported. If JCR 1.0 XPath
     * is supported then this descriptor has the same semantics as in JCR 1.0.
     * This constant is deprecated.
     *
     * @deprecated As of JCR 2.0.
     */
    public static final String QUERY_XPATH_POS_INDEX = "query.xpath.pos.index";

    /**
     * Key to a <code>boolean</code> descriptor. Returns false unless the
     * (deprecated) JCR 1.0 XPath query language is supported. If JCR 1.0 XPath
     * is supported then this descriptor has the same semantics as in JCR 1.0.
     * This constant is deprecated.
     *
     * @deprecated As of JCR 2.0.
     */
    public static final String QUERY_XPATH_DOC_ORDER = "query.xpath.doc.order";

    /**
     * Returns a string array holding all descriptor keys available for this
     * implementation, both the standard descriptors defined by the string
     * constants in this interface and any implementation-specific descriptors.
     * Used in conjunction with {@link #getDescriptorValue(String key)} and
     * {@link #getDescriptorValues(String key)} to query information about this
     * repository implementation.
     *
     * @return a string array holding all descriptor keys.
     */
    public String[] getDescriptorKeys();

    /**
     * Returns <code>true</code> if <code>key</code> is a standard descriptor
     * defined by the string constants in this interface and <code>false</code>
     * if it is either a valid implementation-specific key or not a valid key.
     *
     * @param key a descriptor key.
     * @return whether <code>key</code> is a standard descriptor.
     * @since JCR 2.0
     */
    public boolean isStandardDescriptor(String key);

    /**
     * Returns <code>true</code> if <code>key</code> is a valid single-value
     * descriptor; otherwise returns <code>false</code>.
     *
     * @param key a descriptor key.
     * @return whether the specified desdfriptor is multi-valued.
     * @since JCR 2.0
     */
    public boolean isSingleValueDescriptor(String key);

    /**
     * The value of a single-value descriptor is found by passing the key for
     * that descriptor to this method. If <code>key</code> is the key of a
     * multi-value descriptor or not a valid key this method returns
     * <code>null</code>.
     *
     * @param key a descriptor key.
     * @return The value of the indicated descriptor
     * @since JCR 2.0
     */
    public Value getDescriptorValue(String key);

    /**
     * The value array of a multi-value descriptor is found by passing the key
     * for that descriptor to this method. If <code>key</code> is the key of a
     * single-value descriptor then this method returns that value as an array
     * of size one. If <code>key</code> is not a valid key this method returns
     * <code>null</code>.
     *
     * @param key a descriptor key.
     * @return the value array for the indicated descriptor
     * @since JCR 2.0
     */
    public Value[] getDescriptorValues(String key);

    /**
     * <p> A convenience method. The call  <p> <code>String s =
     * repository.getDescriptor(key);</code>  <p> is equivalent to
     * <p>
     * <code>Value v = repository.getDescriptorValue(key);<br/> String s = (v ==
     * null) ? null : v.getString();</code>
     *
     * @param key a descriptor key.
     * @return a descriptor value in string form.
     */
    public String getDescriptor(String key);

    /**
     * Authenticates the user using the supplied <code>credentials</code>. If
     * <code>workspaceName</code> is recognized as the name of an existing
     * workspace in the repository and authorization to access that workspace is
     * granted, then a new <code>Session</code> object is returned. The format
     * of the string <code>workspaceName</code> depends upon the
     * implementation.
     * <p>
     * If <code>credentials</code> is <code>null</code>, it is assumed that
     * authentication is handled by a mechanism external to the repository
     * itself (for example, through the JAAS framework) and that the repository
     * implementation exists within a context (for example, an application
     * server) that allows it to handle authorization of the request for access
     * to the specified workspace.
     * <p>
     * If <code>workspaceName</code> is <code>null</code>, a default workspace
     * is automatically selected by the repository implementation. This may, for
     * example, be the "home workspace" of the user whose credentials were
     * passed, though this is entirely up to the configuration and
     * implementation of the repository. Alternatively, it may be a "null
     * workspace" that serves only to provide the method {@link
     * Workspace#getAccessibleWorkspaceNames}, allowing the client to select
     * from among available "real" workspaces.
     *
     * @param credentials   The credentials of the user
     * @param workspaceName the name of a workspace.
     * @return a valid session for the user to access the repository.
     * @throws LoginException           if authentication or authorization for the
     *                                  specified workspace fails.
     * @throws NoSuchWorkspaceException if the specified
     *                                  <code>workspaceName</code> is not recognized.
     * @throws RepositoryException      if another error occurs.
     */
    public Session login(Credentials credentials, String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException;

    /**
     * Equivalent to <code>login(credentials, null)</code>.
     *
     * @param credentials The credentials of the user
     * @return a valid session for the user to access the repository.
     * @throws LoginException      if authentication or authorization fails.
     * @throws RepositoryException if another error occurs.
     */
    public Session login(Credentials credentials) throws LoginException, RepositoryException;

    /**
     * Equivalent to <code>login(null, workspaceName)</code>.
     *
     * @param workspaceName the name of a workspace.
     * @return a valid session for the user to access the repository.
     * @throws LoginException           if authentication or authorization for the
     *                                  specified workspace fails.
     * @throws NoSuchWorkspaceException if the specified
     *                                  <code>workspaceName</code> is not recognized.
     * @throws RepositoryException      if another error occurs.
     */
    public Session login(String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException;

    /**
     * Equivalent to <code>login(null, null)</code>.
     *
     * @return a valid session for the user to access the repository.
     * @throws LoginException      if authentication or authorization fails.
     * @throws RepositoryException if another error occurs.
     */
    public Session login() throws LoginException, RepositoryException;
}
