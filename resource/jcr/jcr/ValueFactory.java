/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * The <code>ValueFactory</code> object provides methods for the creation Value
 * objects that can then be used to set properties.
 */
public interface ValueFactory {

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#STRING} with
     * the specified <code>value</code>.
     *
     * @param value a <code>String</code>
     * @return a <code>Value</code> of {@link PropertyType#STRING}
     */
    public Value createValue(String value);

    /**
     * Returns a <code>Value</code> object of the {@link PropertyType} specified
     * by <code>type</code> with the specified <code>value</code>.
     *
     * @param value a <code>String</code>
     * @param type  one of the constants defined in {@link PropertyType}.
     * @return a <code>Value</code> of {@link PropertyType} <code>type</code>.
     * @throws ValueFormatException if the specified <code>value</code> cannot
     *                              be converted to the specified <code>type</code>.
     */
    public Value createValue(String value, int type) throws ValueFormatException;

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#LONG} with the
     * specified <code>value</code>.
     *
     * @param value a <code>long</code>
     * @return a <code>Value</code> of {@link PropertyType#LONG}
     */
    public Value createValue(long value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#DOUBLE} with
     * the specified <code>value</code>.
     *
     * @param value a <code>double</code>
     * @return a <code>Value</code> of {@link PropertyType#DOUBLE}
     */
    public Value createValue(double value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#DECIMAL} with
     * the specified <code>value</code>.
     *
     * @param value a <code>double</code>
     * @return a <code>Value</code> of {@link PropertyType#DECIMAL}
     * @since JCR 2.0
     */
    public Value createValue(BigDecimal value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#BOOLEAN} with
     * the specified <code>value</code>.
     *
     * @param value a <code>boolean</code>
     * @return a <code>Value</code> of {@link PropertyType#BOOLEAN}
     */
    public Value createValue(boolean value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#DATE} with the
     * specified <code>value</code>.
     *
     * @param value a <code>Calendar</code>
     * @return a <code>Value</code> of {@link PropertyType#DATE}
     * @throws IllegalArgumentException if the specified <code>value</code>
     *                                  cannot be expressed in the ISO 8601-based format defined in the JCR 2.0
     *                                  specification and the implementation does not support dates incompatible
     *                                  with that format.
     */
    public Value createValue(Calendar value);

    /**
     * Returns a <code>Value</code> object of <code>PropertyType.BINARY</code>
     * with a value consisting of the content of the specified
     * <code>InputStream</code>.
     * <p>
     * The passed <code>InputStream</code> is closed before this method returns
     * either normally or because of an exception.
     *
     * @param value an <code>InputStream</code>
     * @return a <code>Value</code> of {@link PropertyType#BINARY}
     * @deprecated As of JCR 2.0, {@link #createValue(Binary)} should be used
     *             instead.
     */
    public Value createValue(InputStream value);

    /**
     * Returns a <code>Value</code> object of <code>PropertyType.BINARY</code>
     * with a value consisting of the content of the specified
     * <code>Binary</code>.
     *
     * @param value a <code>Binary</code>
     * @return a <code>Value</code> of {@link PropertyType#BINARY}
     * @since JCR 2.0
     */
    public Value createValue(Binary value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#REFERENCE}
     * that holds the identifier of the specified <code>Node</code>. This
     * <code>Value</code> object can then be used to set a property that will be
     * a reference to that <code>Node</code>.
     *
     * @param value a <code>Node</code>
     * @return a <code>Value</code> of {@link PropertyType#REFERENCE}
     * @throws RepositoryException if the specified <code>Node</code> is not
     *                             referenceable, the current <code>Session</code> is no longer active, or
     *                             another error occurs.
     */
    public Value createValue(Node value) throws RepositoryException;

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#REFERENCE} (if
     * <code>weak</code> is <code>false</code>) or {@link
     * PropertyType#REFERENCE} (if <code>weak</code> is <code>true</code>) that
     * holds the identifier of the specified <code>Node</code>. This
     * <code>Value</code> object can then be used to set a property that will be
     * a reference to that <code>Node</code>.
     *
     * @param value a <code>Node</code>
     * @param weak  a <code>boolean</code>. If true then a {@link
     *              PropertyType#WEAKREFERENCE} is created, otherwise a {@link
     *              PropertyType#REFERENCE} is created.
     * @return a <code>Value</code> of {@link PropertyType#REFERENCE} or {@link
     *         PropertyType#REFERENCE}
     * @throws RepositoryException if the specified <code>Node</code> is not
     *                             referenceable, the current <code>Session</code> is no longer active, or
     *                             another error occurs.
     */
    public Value createValue(Node value, boolean weak) throws RepositoryException;

    /**
     * Returns a <code>Binary</code> object with a value consisting of the
     * content of the specified <code>InputStream</code>.
     * <p>
     * The passed <code>InputStream</code> is closed before this method returns
     * either normally or because of an exception.
     *
     * @param stream an <code>InputStream</code>
     * @return a <code>Binary</code>
     * @throws RepositoryException if an error occurs.
     * @since JCR 2.0
     */
    public Binary createBinary(InputStream stream) throws RepositoryException;
}