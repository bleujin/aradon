/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.util;

import java.util.LinkedList;

import javax.jcr.Item;
import javax.jcr.ItemVisitor;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;

/**
 * An implementation of <code>ItemVisitor</code>.
 * <p>
 * <code>TraversingItemVisitor</code> is an abstract utility class which allows
 * to easily traverse an <code>Item</code> hierarchy.
 * <p>
 * <p><code>TraversingItemVisitor</code> makes use of the Visitor Pattern as
 * described in the book 'Design Patterns' by the Gang Of Four (Gamma et al.).
 * <p>
 * <p>Tree traversal is done observing the left-to-right order of child
 * <code>Item</code>s if such an order is supported and exists.
 */
public abstract class TraversingItemVisitor implements ItemVisitor {

    /**
     * Indicates if traversal should be done in a breadth-first manner rather
     * than depth-first (which is the default).
     */
    final protected boolean breadthFirst;

    /**
     * The 0-based level up to which the hierarchy should be traversed (if it's
     * -1, the hierarchy will be traversed until there are no more children of
     * the current item).
     */
    final protected int maxLevel;

    /**
     * Queues used to implement breadth-first traversal.
     */
    private LinkedList currentQueue;
    private LinkedList nextQueue;

    /**
     * Used to track hierarchy level of item currently being processed.
     */
    private int currentLevel;

    /**
     * Constructs a new instance of this class.
     * <p>
     * The tree of <code>Item</code>s will be traversed in a depth-first manner
     * (default behavior).
     */
    public TraversingItemVisitor() {
        this(false, -1);
    }

    /**
     * Constructs a new instance of this class.
     *
     * @param breadthFirst if <code>breadthFirst</code> is true then traversal
     *                     is done in a breadth-first manner; otherwise it is done in a depth-first
     *                     manner (which is the default behavior).
     */
    public TraversingItemVisitor(boolean breadthFirst) {
        this(breadthFirst, -1);
    }

    /**
     * Constructs a new instance of this class.
     *
     * @param breadthFirst if <code>breadthFirst</code> is true then traversal
     *                     is done in a breadth-first manner; otherwise it is done in a depth-first
     *                     manner (which is the default behavior).
     * @param maxLevel     the 0-based level up to which the hierarchy should be
     *                     traversed (if it's -1, the hierarchy will be traversed until there are no
     *                     more children of the current item).
     */
    public TraversingItemVisitor(boolean breadthFirst, int maxLevel) {
        this.breadthFirst = breadthFirst;
        if (breadthFirst) {
            currentQueue = new LinkedList();
            nextQueue = new LinkedList();
        }
        currentLevel = 0;
        this.maxLevel = maxLevel;
    }

    /**
     * Implement this method to add behavior performed before a
     * <code>Property</code> is visited.
     *
     * @param property the <code>Property</code> that is accepting this
     *                 visitor.
     * @param level    hierarchy level of this property (the root node starts at
     *                 level 0).
     * @throws RepositoryException if an error occurs.
     */
    protected abstract void entering(Property property, int level)
            throws RepositoryException;

    /**
     * Implement this method to add behavior performed before a
     * <code>Node</code> is visited.
     *
     * @param node  the <code>Node</code> that is accepting this visitor.
     * @param level hierarchy level of this node (the root node starts at level
     *              0).
     * @throws RepositoryException if an error occurs.
     */
    protected abstract void entering(Node node, int level)
            throws RepositoryException;

    /**
     * Implement this method to add behavior performed after a
     * <code>Property</code> is visited.
     *
     * @param property the <code>Property</code> that is accepting this
     *                 visitor.
     * @param level    hierarchy level of this property (the root node starts at
     *                 level 0).
     * @throws RepositoryException if an error occurs.
     */
    protected abstract void leaving(Property property, int level)
            throws RepositoryException;

    /**
     * Implement this method to add behavior performed after a <code>Node</code>
     * is visited.
     *
     * @param node  the <code>Node</code> that is accepting this visitor.
     * @param level hierarchy level of this node (the root node starts at level
     *              0).
     * @throws RepositoryException if an error occurs.
     */
    protected abstract void leaving(Node node, int level)
            throws RepositoryException;

    /**
     * Called when the <code>Visitor</code> is passed to a
     * <code>Property</code>.
     * <p>
     * It calls <code>TraversingItemVisitor.entering(Property, int)</code>
     * followed by <code>TraversingItemVisitor.leaving(Property, int)</code>.
     * Implement these abstract methods to specify behavior on 'arrival at' and
     * 'after leaving' the <code>Property</code>.
     * <p>
     * If this method throws, the visiting process is aborted.
     *
     * @param property the <code>Property</code> that is accepting this
     *                 visitor.
     * @throws RepositoryException if an error occurs.
     */
    public void visit(Property property) throws RepositoryException {
        entering(property, currentLevel);
        leaving(property, currentLevel);
    }

    /**
     * Called when the <code>Visitor</code> is passed to a <code>Node</code>.
     * <p>
     * It calls <code>TraversingItemVisitor.entering(Node, int)</code> followed
     * by <code>TraversingItemVisitor.leaving(Node, int)</code>. Implement these
     * abstract methods to specify behavior on 'arrival at' and 'after leaving'
     * the <code>Node</code>.
     * <p>
     * If this method throws, the visiting process is aborted.
     *
     * @param node the <code>Node</code> that is accepting this visitor.
     * @throws RepositoryException if an error occurs.
     */
    public void visit(Node node)
            throws RepositoryException {
        try {
            if (!breadthFirst) {
                // depth-first traversal
                entering(node, currentLevel);
                if (maxLevel == -1 || currentLevel < maxLevel) {
                    currentLevel++;
                    PropertyIterator propIter = node.getProperties();
                    while (propIter.hasNext()) {
                        propIter.nextProperty().accept(this);
                    }
                    NodeIterator nodeIter = node.getNodes();
                    while (nodeIter.hasNext()) {
                        nodeIter.nextNode().accept(this);
                    }
                    currentLevel--;
                }
                leaving(node, currentLevel);
            } else {
                // breadth-first traversal
                entering(node, currentLevel);
                leaving(node, currentLevel);

                if (maxLevel == -1 || currentLevel < maxLevel) {
                    PropertyIterator propIter = node.getProperties();
                    while (propIter.hasNext()) {
                        nextQueue.addLast(propIter.nextProperty());
                    }
                    NodeIterator nodeIter = node.getNodes();
                    while (nodeIter.hasNext()) {
                        nextQueue.addLast(nodeIter.nextNode());
                    }
                }

                while (!currentQueue.isEmpty() || !nextQueue.isEmpty()) {
                    if (currentQueue.isEmpty()) {
                        currentLevel++;
                        currentQueue = nextQueue;
                        nextQueue = new LinkedList();
                    }
                    Item e = (Item) currentQueue.removeFirst();
                    e.accept(this);
                }
                currentLevel = 0;
            }
        } catch (RepositoryException re) {
            currentLevel = 0;
            throw re;
        }
    }

    /**
     * Convenience class providing default implementations of the abstract
     * methods of <code>TraversingItemVisitor</code>.
     */
    public static class Default extends TraversingItemVisitor {

        /**
         * @see TraversingItemVisitor#TraversingItemVisitor()
         */
        public Default() {
        }

        /**
         * @param breadthFirst a boolean
         * @see TraversingItemVisitor#TraversingItemVisitor()
         */
        public Default(boolean breadthFirst) {
            super(breadthFirst);
        }

        /**
         * @param breadthFirst a boolean
         * @param maxLevel     an int
         * @see TraversingItemVisitor#TraversingItemVisitor(boolean, int)
         */
        public Default(boolean breadthFirst, int maxLevel) {
            super(breadthFirst, maxLevel);
        }

        /**
         * @see TraversingItemVisitor#entering(Node, int)
         */
        protected void entering(Node node, int level)
                throws RepositoryException {
        }

        /**
         * @see TraversingItemVisitor#entering(Property, int)
         */
        protected void entering(Property property, int level)
                throws RepositoryException {
        }

        /**
         * @see TraversingItemVisitor#leaving(Node, int)
         */
        protected void leaving(Node node, int level)
                throws RepositoryException {
        }

        /**
         * @see TraversingItemVisitor#leaving(Property, int)
         */
        protected void leaving(Property property, int level)
                throws RepositoryException {
        }
    }
}
