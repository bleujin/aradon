package net.ion.radon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Annotations is a merging apapter for the {@link AnnotatedElement} interface
 * 
 * @author tiwe
 *
 */
public class Annotations implements AnnotatedElement {

    private final Map<Class<? extends Annotation>,Annotation> annotations = new HashMap<Class<? extends Annotation>,Annotation>();

    public Annotations(AnnotatedElement... elements) {
        for (AnnotatedElement element : elements) {
            for (Annotation annotation : element.getAnnotations()) {
                annotations.put(annotation.annotationType(), annotation);
            }
        }
    } 

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return (T) annotations.get(annotationClass);
    }

    public Annotation[] getAnnotations() {
        return annotations.values().toArray(new Annotation[annotations.values().size()]);
    }

    public Annotation[] getDeclaredAnnotations() {
        return getAnnotations();
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return annotations.containsKey(annotationClass);
    }
    
    public void addAnnotation(Annotation annotation) {
        if (annotation != null) {
            annotations.put(annotation.annotationType(), annotation);    
        }
        
    }

}
