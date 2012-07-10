/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.version;

/**
 * The possible actions specified by the <code>onParentVersion</code> attribute
 * in a property definition within a node type definition.
 * <p>
 * This interface defines the following actions: <UL> <LI><code>COPY</code>
 * <LI><code>VERSION</code> <LI><code>INITIALIZE</code> <LI><code>COMPUTE</code>
 * <LI><code>IGNORE</code> <LI><code>ABORT</code> </UL>
 * <p>
 * Every item (node or property) in the repository has a status indicator that
 * governs what happens to that item when its parent node is versioned. This
 * status is defined by the <code>onParentVersion</code> attribute in the
 * <code>PropertyDefinition</code> or <code>NodeDefinition</code> that applies
 * to the item in question.
 */
public final class OnParentVersionAction {

    /**
     * The action constants.
     */
    public static final int COPY = 1;
    public static final int VERSION = 2;
    public static final int INITIALIZE = 3;
    public static final int COMPUTE = 4;
    public static final int IGNORE = 5;
    public static final int ABORT = 6;

    /**
     * The names of the defined on-version actions, as used in serialization.
     */
    public static final String ACTIONNAME_COPY = "COPY";
    public static final String ACTIONNAME_VERSION = "VERSION";
    public static final String ACTIONNAME_INITIALIZE = "INITIALIZE";
    public static final String ACTIONNAME_COMPUTE = "COMPUTE";
    public static final String ACTIONNAME_IGNORE = "IGNORE";
    public static final String ACTIONNAME_ABORT = "ABORT";

    /**
     * Returns the name of the specified <code>action</code>, as used in
     * serialization.
     *
     * @param action the on-version action
     * @return the name of the specified <code>action</code>
     * @throws IllegalArgumentException if <code>action</code> is not a valid
     *                                  on-version action.
     */
    public static String nameFromValue(int action) {
        switch (action) {
            case COPY:
                return ACTIONNAME_COPY;
            case VERSION:
                return ACTIONNAME_VERSION;
            case INITIALIZE:
                return ACTIONNAME_INITIALIZE;
            case COMPUTE:
                return ACTIONNAME_COMPUTE;
            case IGNORE:
                return ACTIONNAME_IGNORE;
            case ABORT:
                return ACTIONNAME_ABORT;
            default:
                throw new IllegalArgumentException("unknown on-version action: " + action);
        }
    }

    /**
     * Returns the numeric constant value of the on-version action with the
     * specified name.
     *
     * @param name the name of the on-version action
     * @return the numeric constant value
     * @throws IllegalArgumentException if <code>name</code> is not a valid
     *                                  on-version action name.
     */
    public static int valueFromName(String name) {
        if (name.equals(ACTIONNAME_COPY)) {
            return COPY;
        } else if (name.equals(ACTIONNAME_VERSION)) {
            return VERSION;
        } else if (name.equals(ACTIONNAME_INITIALIZE)) {
            return INITIALIZE;
        } else if (name.equals(ACTIONNAME_COMPUTE)) {
            return COMPUTE;
        } else if (name.equals(ACTIONNAME_IGNORE)) {
            return IGNORE;
        } else if (name.equals(ACTIONNAME_ABORT)) {
            return ABORT;
        } else {
            throw new IllegalArgumentException("unknown on-version action: " + name);
        }
    }

    /**
     * private constructor to prevent instantiation
     */
    private OnParentVersionAction() {
    }
}