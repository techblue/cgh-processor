package uk.co.techblue.cgh.dnap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dheeraj
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Property {
    
    /**
     * Property name that will be used to lookup and populate annotated field values.
     * 
     * @return property name.
     */
    String name();
    
    String displayName() default "";
    
    /**
     * Property type that will be used to lookup and build ui controls
     * 
     * @return property name.
     */
    
    Type type() default Type.TEXT;
    
    enum Type{
        BOOLEAN,
        TEXT
    }
    
    boolean editable() default true;
    
    String description() default "";
}
