package uk.co.techblue.cgh.dnap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface PersistProperties.
 * 
 * @author dheeraj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface PersistProperties {

    /**
     * The Enum ResourceBasePath.
     */
    public enum ResourceBasePath {

        /** The user home. */
        USER_HOME("user.home"),
        /** The none. */
        NONE("none");

        /**
         * Instantiates a new resource base path.
         * 
         * @param pathValue the path value
         */
        ResourceBasePath(String pathValue) {
            this.value = pathValue;
        }

        /** The value. */
        private String value;

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return this.value;
        }
    }

    /**
     * Path.
     * 
     * @return the string
     */
    String path() default "";

    /**
     * Default path.
     * 
     * @return the string
     */
    String defaultPath();

    /**
     * Base path.
     * 
     * @return the resource base path
     */
    ResourceBasePath basePath() default ResourceBasePath.NONE;

}
