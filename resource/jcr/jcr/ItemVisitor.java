/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr;

/**
 * This interface defines two signatures of the <code>visit</code> method; one
 * taking a <code>Node</code>, the other a <code>Property</code>. When an object
 * implementing this interface is passed to <code>{@link Item#accept(ItemVisitor
 * visitor)}</code> the appropriate <code>visit</code> method is automatically
 * called, depending on whether the <code>Item</code> in question is a
 * <code>Node</code> or a <code>Property</code>. Different implementations of
 * this interface can be written for different purposes. It is, for example,
 * possible for the <code>{@link #visit(Node node)}</code> method to call
 * <code>accept</code> on the children of the passed node and thus recurse
 * through the tree performing some operation on each <code>Item</code>.
 */
public interface ItemVisitor {

    /**
     * This method is called when the <code>ItemVisitor</code> is passed to the
     * <code>accept</code> method of a <code>Property</code>. If this method
     * throws an exception the visiting process is aborted.
     *
     * @param property The <code>Property</code> that is accepting this
     *                 visitor.
     * @throws RepositoryException if an error occurs
     */
    public void visit(Property property) throws RepositoryException;

    /**
     * This method is called when the <code>ItemVisitor</code> is passed to the
     * <code>accept</code> method of a <code>Node</code>. If this method throws
     * an exception the visiting process is aborted.
     *
     * @param node The <code>Node</code that is accepting this visitor.
     * @throws RepositoryException if an error occurs
     */
    public void visit(Node node) throws RepositoryException;
}