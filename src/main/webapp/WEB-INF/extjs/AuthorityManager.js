/**
 * xiong
 */
Ext.define('MyDesktop.AuthorityManager', {
	extend : 'Ext.ux.desktop.Module',
	requires : [ 'Ext.data.Store', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer' ],

	id : 'authorityManager',

	init : function() {
		this.launcher = {
			text : '权限管理',
			iconCls : 'icon-grid'
		};
	},

	createWindow : function() {
		var modelName = 'authority', mid = modelName + 'Manager';
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(mid);
		var grid = createGrid(modelName, null, null, '权限管理', true, true, 40);
		if (!win) {
			win = desktop.createWindow({
				id : mid,
				title : '权限管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : [ grid ]
			});
		}
		return win;
	},
});
