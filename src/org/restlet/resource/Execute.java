package org.restlet.resource;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.restlet.engine.Method;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Method("EXECUTE")
public @interface Execute {
	 String value() default "";
}