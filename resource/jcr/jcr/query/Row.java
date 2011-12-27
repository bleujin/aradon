/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.query;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

/**
 * A row in the query result table.
 */
public interface Row {

    /**
     * Returns an array of all the values in the same order as the column names
     * returned by {@link QueryResult#getColumnNames()}.
     *
     * @return a <code>Value</code> array.
     * @throws RepositoryException if an error occurs
     */
    public Value[] getValues() throws RepositoryException;

    /**
     * Returns the value of the indicated column in this <code>Row</code>.
     *
     * @param columnName name of query result table column
     * @return a <code>Value</code>
     * @throws ItemNotFoundException if <code>columnName</code> is not among the
     *                               column names of the query result table.
     * @throws RepositoryException   if another error occurs.
     */
    public Value getValue(String columnName) throws ItemNotFoundException, RepositoryException;

    /**
     * Returns the <code>Node</code> corresponding to this <code>Row</code>.
     *
     * @return a <code>Node</code>
     * @throws RepositoryException if this query has more than one selector (and
     *                             therefore, this <code>Row</code> corresponds to more than one
     *                             <code>Node</code>) or if another error occurs.
     * @since JCR 2.0
     */
    public Node getNode() throws RepositoryException;

    /**
     * Returns the <code>Node</code> corresponding to this <code>Row</code> and
     * the specified selector. If this <code>Row</code> is from a result
     * involving outer joins, it may have no <code>Node</code> corresponding to
     * the specified selector. In such a case this method returns
     * <code>null</code>.
     *
     * @param selectorName a <code>String</code>
     * @return a <code>Node</code>
     * @throws RepositoryException if <code>selectorName</code> is not the alias
     *                             of a selector in this query or if another error occurs.
     * @since JCR 2.0
     */
    public Node getNode(String selectorName) throws RepositoryException;

    /**
     * Equivalent to <code>Row.getNode().getPath()</code>. However, some
     * implementations may be able gain efficiency by not resolving the actual
     * <code>Node</code>.
     *
     * @return a <code>String</code>
     * @throws RepositoryException if this query has more than one selector (and
     *                             therefore, this <code>Row</code> corresponds to more than one
     *                             <code>Node</code>) or if another error occurs.
     * @since JCR 2.0
     */
    public String getPath() throws RepositoryException;

    /**
     * Equivalent to <code>Row.getNode(selectorName).getPath()</code>. However,
     * some implementations may be able gain efficiency by not resolving the
     * actual <code>Node</code>. If this <code>Row</code> is from a result
     * involving outer joins, it may have no <code>Node</code> corresponding to
     * the specified selector. In such a case this method returns
     * <code>null</code>.
     *
     * @param selectorName a <code>String</code>
     * @return a <code>String</code>
     * @throws RepositoryException if <code>selectorName</code> is not the alias
     *                             of a selector in this query or if another error occurs.
     * @since JCR 2.0
     */
    public String getPath(String selectorName) throws RepositoryException;

    /**
     * Returns the full text search score for this row associated with the
     * default selector. This corresponds to the score of a particular node.
     * <p>
     * If no <code>FullTextSearchScore</code> AQM object is associated with the
     * default selector this method will still return a value. However, in that
     * case the returned value may not be meaningful or may simply reflect the
     * minimum possible relevance level (for example, in some systems this might
     * be a score of 0).
     * <p>
     * Note, in JCR-SQL2 a <code>FullTextSearchScore</code> AQM object is
     * represented by a <code>SCORE()</code> function. In JCR-JQOM it is
     * represented by a Java object of type <code>javax.jcr.query.qom.FullTextSearchScore</code>.
     *
     * @return a <code>double</code>
     * @throws RepositoryException if this query has more than one selector (and
     *                             therefore, this <code>Row</code> corresponds to more than one
     *                             <code>Node</code>) or if another error occurs.
     * @since JCR 2.0
     */
    public double getScore() throws RepositoryException;

    /**
     * Returns the full text search score for this row associated with the
     * specified selector. This corresponds to the score of a particular node.
     * <p>
     * If no <code>FullTextSearchScore</code> AQM object is associated with the
     * selector <code>selectorName</code> this method will still return a value.
     * However, in that case the returned value may not be meaningful or may
     * simply reflect the minimum possible relevance level (for example, in some
     * systems this might be a score of 0).
     * <p>
     * Note, in JCR-SQL2 a <code>FullTextSearchScore</code> AQM object is
     * represented by a <code>SCORE()</code> function. In JCR-JQOM it is
     * represented by a Java object of type <code>javax.jcr.query.qom.FullTextSearchScore</code>.
     * <p>
     * If this <code>Row</code> is from a result involving outer joins, it may
     * have no <code>Node</code> corresponding to the specified selector. In
     * such a case this method returns an implementation selected value, as it
     * would if there were no <code>FullTextSearchScore</code> associated with
     * the selector.
     *
     * @param selectorName a <code>String</code>
     * @return a <code>double</code>
     * @throws RepositoryException if <code>selectorName</code> is not the alias
     *                             of a selector in this query or if another error occurs.
     * @since JCR 2.0
     */
    public double getScore(String selectorName) throws RepositoryException;
}
