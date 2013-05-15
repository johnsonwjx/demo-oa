/**
 * 
 */
package xiong.domain.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiong 实体类属性描述
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnDesc {
	String value();

	int width() default 70;

	boolean hidden() default false;

	boolean sortable() default true;

	boolean renderer() default false;
	
	boolean locked() default false ;
	
	String xtype() default "gridcolumn";
	
	String format() default "" ;
	
	boolean useField() default true ;
}
