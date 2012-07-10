/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

import javax.jcr.Value;

/**
 * The <code>PropertyDefinitionTemplate</code> interface extends
 * <code>PropertyDefinition</code> with the addition of write methods, enabling
 * the characteristics of a child property definition to be set, after which the
 * <code>PropertyDefinitionTemplate</code> is added to a
 * <code>NodeTypeTemplate</code>.
 * <p>
 * See the corresponding <code>get</code> methods for each attribute in
 * <code>PropertyDefinition</code> for the default values assumed when a new
 * empty <code>PropertyDefinitionTemplate</code> is created (as opposed to one
 * extracted from an existing <code>NodeType</code>).
 *
 * @since JCR 2.0
 */
public interface PropertyDefinitionTemplate extends PropertyDefinition {

    /**
     * Sets the name of the property. This must be a JCR name in either
     * qualified or expanded form.
     *
     * @param name a JCR name.
     * @throws ConstraintViolationException if <code>name</code> is not a
     * syntactically valid JCR name in either qualified or expanded form.
     */
    public void setName(String name) throws ConstraintViolationException;

    /**
     * Sets the auto-create status of the property.
     *
     * @param autoCreated a <code>boolean</code>.
     */
    public void setAutoCreated(boolean autoCreated);

    /**
     * Sets the mandatory status of the property.
     *
     * @param mandatory a <code>boolean</code>.
     */
    public void setMandatory(boolean mandatory);

    /**
     * Sets the on-parent-version status of the property.
     *
     * @param opv an <code>int</code> constant member of
     *            <code>OnParentVersionAction</code>.
     */
    public void setOnParentVersion(int opv);

    /**
     * Sets the protected status of the property.
     *
     * @param protectedStatus a <code>boolean</code>.
     */
    public void setProtected(boolean protectedStatus);

    /**
     * Sets the required type of the property.
     *
     * @param type an <code>int</code> constant member of
     *             <code>PropertyType</code>.
     */
    public void setRequiredType(int type);

    /**
     * Sets the value constraints of the property.
     *
     * @param constraints a <code>String</code> array.
     */
    public void setValueConstraints(String[] constraints);

    /**
     * Sets the default value (or values, in the case of a multi-value property)
     * of the property.
     *
     * @param defaultValues a <code>Value</code> array.
     */
    public void setDefaultValues(Value[] defaultValues);

    /**
     * Sets the multi-value status of the property.
     *
     * @param multiple a <code>boolean</code>.
     */
    public void setMultiple(boolean multiple);

    /**
     * Sets the queryable status of the property.
     *
     * @param operators an array of String constants. See {@link
     *                  PropertyDefinition#getAvailableQueryOperators()} .
     */
    public void setAvailableQueryOperators(String[] operators);

    /**
     * Sets the full-text-searchable status of the property.
     *
     * @param fullTextSearchable a <code>boolean</code>.
     */
    public void setFullTextSearchable(boolean fullTextSearchable);

    /**
     * Sets the query-orderable status of the property.
     *
     * @param queryOrderable a <code>boolean</code>.
     */
    public void setQueryOrderable(boolean queryOrderable);
}
