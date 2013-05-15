package xiong.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import xiong.domain.web.ColumnDesc;
import xiong.domain.web.WebColumn;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-6
 */
public class EntityUtil {
	public static <T> List<WebColumn> getGridColumn(Class<T> entityClass) {
		List<WebColumn> columnList = new ArrayList<WebColumn>();
		Field[] fields = entityClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			ColumnDesc columnDesc = field.getAnnotation(ColumnDesc.class);
			if (columnDesc == null
					|| field.getName().equals("serialVersionUID"))
				continue;
			Column columnPro = field.getAnnotation(Column.class);
			Id id = field.getAnnotation(Id.class);
			boolean allowBlank = id == null;
			if (columnPro != null)
				allowBlank = columnPro.nullable();
			String fieldType = field.getType().getSimpleName();
			if (fieldType.equals("Float") || fieldType.equals("long")
					|| fieldType.equals("Long")) {
				fieldType = "float";
			} else if (fieldType.equals("Integer")) {
				fieldType = "int";
			} else if (fieldType.equals("String")) {
				fieldType = "string";
			} else if (fieldType.equals("Date")) {
				fieldType = "date";
			}
			columnList.add(new WebColumn(
					columnDesc.value(),
					field.getName(),//
					columnDesc.hidden(), columnDesc.width(), fieldType,
					columnDesc.sortable(), columnDesc.renderer(), allowBlank,
					id != null, columnDesc.locked(), columnDesc.xtype(),
					columnDesc.format(), columnDesc.useField()));
		}
		return columnList;
	}
}
