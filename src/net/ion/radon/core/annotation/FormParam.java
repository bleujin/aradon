package net.ion.radon.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FormParam {

    /**
     * Default is <code>false</code>.
     */
    boolean optional() default false;

    /**
     * No default.
     */
    String value();
}
