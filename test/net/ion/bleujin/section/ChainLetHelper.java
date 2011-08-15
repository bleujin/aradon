package net.ion.bleujin.section;


import java.util.logging.Level;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.engine.RestletHelper;
import org.restlet.routing.Filter;

/**
 * Chain helper serving as base class for Application and Component helpers.
 * 
 * @author Jerome Louvel
 */
public abstract class ChainLetHelper<T extends Restlet> extends RestletHelper<T> {
    /** The first Restlet. */
    private volatile Restlet first;

    /** The last Filter. */
    private volatile Filter last;

    /**
     * Constructor.
     * 
     * @param helped
     *            The helped Restlet.
     */
    public ChainLetHelper(T helped) {
        super(helped);
        this.first = null;
    }

    /**
     * Adds a new filter to the chain.
     * 
     * @param filter
     *            The filter to add.
     */
    protected synchronized void addFilter(Filter filter) {
        if (getLast() != null) {
            getLast().setNext(filter);
            setLast(filter);
        } else {
            setFirst(filter);
            setLast(filter);
        }
    }

    /**
     * Clears the chain. Sets the first and last filters to null.
     */
    public void clear() {
        setFirst(null);
        setNext(null);
    }

    /**
     * Returns the first Restlet.
     * 
     * @return the first Restlet.
     */
    protected Restlet getFirst() {
        return this.first;
    }

    /**
     * Returns the last Filter.
     * 
     * @return the last Filter.
     */
    protected Filter getLast() {
        return this.last;
    }

    @Override
    public void handle(Request request, Response response) {
        super.handle(request, response);

        if (getFirst() != null) {
            getFirst().handle(request, response);
        } else {
            response.setStatus(Status.SERVER_ERROR_INTERNAL);
            getHelped().getLogger().log(Level.SEVERE, 
            		"The " + getHelped().getClass().getName() + " class has no Restlet defined to process calls. Maybe it wasn't properly started.");
        }
    }

    /**
     * Sets the first Restlet.
     * 
     * @param first
     *            The first Restlet.
     */
    protected void setFirst(Restlet first) {
        this.first = first;
    }

    /**
     * Sets the last Filter.
     * 
     * @param last
     *            The last Filter.
     */
    protected void setLast(Filter last) {
        this.last = last;
    }

    /**
     * Sets the next Restlet after the chain.
     * 
     * @param next
     *            The Restlet to process after the chain.
     */
    protected synchronized void setNext(Restlet next) {
        if (getFirst() == null) {
            setFirst(next);
        } else {
            getLast().setNext(next);
        }
    }

}

