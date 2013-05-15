/**
 * xiong
 */
Ext.define('MyDesktop.RoleManager', {
	extend : 'Ext.ux.desktop.Module',
	requires : [ 'Ext.data.Store', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer' ],

	id : 'roleManager',

	init : function() {
		this.launcher = {
			text : '角色管理',
			iconCls : 'icon-grid'
		};
	},

	createWindow : function() {
		var modelName = 'role', mid = modelName + 'Manager';
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(mid);
	
		var grid = createGrid(modelName, null, null, '角色管理', true, true, 20);
		var store = grid.getStore();
		var editor = grid.plugins[0];
		if (!win) {
			win = desktop.createWindow({
				id : mid,
				title : '角色管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : [ grid ],
				tbar : createTbar(modelName, 'id', store, editor, grid)
			});
		}
		return win;
	},
});
