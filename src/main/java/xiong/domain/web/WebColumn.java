/**
 * 
 */
package xiong.domain.web;

/**
 * 创建表格的 demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-2
 */
public class WebColumn {
	private String text;
	private String dataIndex;
	private boolean hidden;
	private int width;
	private String type;
	private boolean sortable;
	private boolean renderer;
	private boolean allowBlank;
	private boolean idField;
	private boolean locked;
	private String xtype;
	private String format;
	private boolean useField;

	public WebColumn(String text, String dataIndex, boolean hidden, int width,
			String type, boolean sortable, boolean renderer,
			boolean allowBlank, boolean idField, boolean locked, String xtype,
			String format, boolean useField) {
		this.text = text;
		this.dataIndex = dataIndex;
		this.hidden = hidden;
		this.width = width;
		this.type = type;
		this.sortable = sortable;
		this.renderer = renderer;
		this.allowBlank = allowBlank;
		this.idField = idField;
		this.locked = locked;
		this.xtype = xtype;
		this.format = format;
		this.useField = useField;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public String getFormat() {
		return format;
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public String getXtype() {
		return xtype;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isIdField() {
		return idField;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isRenderer() {
		return renderer;
	}

	public boolean isSortable() {
		return sortable;
	}

	public boolean isUseField() {
		return useField;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setIdField(boolean idField) {
		this.idField = idField;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setRenderer(boolean renderer) {
		this.renderer = renderer;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUseField(boolean useField) {
		this.useField = useField;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

}
