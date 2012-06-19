package org.restlet.resource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.restlet.engine.Method;
import org.restlet.service.MetadataService;

/**
 * Annotation for methods that retrieve properties. Its semantics is equivalent
 * to a WebDAV PROPFIND method.
 * 
 * @author Jerome Louvel
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Method("PROPFIND")
public @interface PropFind {

    /**
     * Specifies the media type extension of the response entity. If several media types are supported, their extension can be specified separated by "|" characters. Note that this isn't the full MIME type value, just the
     * extension name declared in {@link MetadataService}. For a list of all predefined extensions, please check {@link MetadataService#addCommonExtensions()}. New extension can be
     * registered using {@link MetadataService#addExtension(String, org.restlet.data.Metadata)} method.
     * 
     * @return The result media types.
     */
    String value() default "";

}