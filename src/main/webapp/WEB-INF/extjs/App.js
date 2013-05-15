/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.App',
		{
			extend : 'Ext.ux.desktop.App',
			requires : [ 'Ext.window.MessageBox', 'MyDesktop.EmployeeManager',
					'Ext.ux.desktop.ShortcutModel', 'MyDesktop.SystemStatus',
					'MyDesktop.VideoWindow', 'MyDesktop.TabWindow',
					'MyDesktop.AccordionWindow', 'MyDesktop.Notepad',
					'MyDesktop.BogusMenuModule', 'MyDesktop.BogusModule',
					'MyDesktop.Settings', 'MyDesktop.DepartmentManager',
					'MyDesktop.AuthorityManager', 'MyDesktop.RoleManager' ],

			init : function() {
				// custom logic before getXYZ methods get called...

				this.callParent();

				// now ready...
			},

			getModules : function() {
				return [ new MyDesktop.EmployeeManager(),
						new MyDesktop.SystemStatus(), new MyDesktop.Notepad(),
						new MyDesktop.BogusMenuModule(),
						new MyDesktop.BogusModule(),
						new MyDesktop.AuthorityManager(),
						new MyDesktop.RoleManager(),
						new MyDesktop.DepartmentManager() ];
			},

			getDesktopConfig : function() {
				var me = this, ret = me.callParent();

				return Ext.apply(ret, {
					// cls: 'ux-desktop-black',

					contextMenuItems : [ {
						text : '改变设置',
						handler : me.onSettings,
						scope : me
					} ],
					shortcuts : Ext.create('Ext.data.Store', {
						model : 'Ext.ux.desktop.ShortcutModel',
						data : [ {
							name : '员工管理',
							iconCls : 'accordion-shortcut',
							module : 'employeeManager'
						}, {
							name : '部门管理',
							iconCls : 'accordion-shortcut',
							module : 'departmentManager'
						}, {
							name : '角色管理',
							iconCls : 'cpu-shortcut',
							module : 'roleManager'
						}, {
							name : '权限管理',
							iconCls : 'cpu-shortcut',
							module : 'authorityManager'

						}, {
							name : '记事本',
							iconCls : 'notepad-shortcut',
							module : 'notepad'
						}, {
							name : '系统状态',
							iconCls : 'cpu-shortcut',
							module : 'systemstatus'
						} ]
					}),

					wallpaper : '',
					wallpaperStretch : false
				});
			},

			// config for the start menu
			getStartConfig : function() {
				var me = this, ret = me.callParent();

				return Ext.apply(ret, {
					title : 'OA-demo',
					iconCls : 'user',
					height : 300,
					toolConfig : {
						width : 100,
						items : [ {
							text : '设置',
							iconCls : 'settings',
							handler : me.onSettings,
							scope : me
						}, '-', {
							text : '退出',
							iconCls : 'logout',
							handler : me.onLogout,
							scope : me
						} ]
					}
				});
			},

			getTaskbarConfig : function() {
				var ret = this.callParent();

				return Ext.apply(ret, {
					quickStart : [ {
						name : '用户管理',
						iconCls : 'icon-grid',
						module : 'employManager'
					} ],
					trayItems : [ {
						xtype : 'trayclock',
						flex : 1
					} ]
				});
			},

			onLogout : function() {
				Ext.Msg.confirm('退出', '您确定退出系统吗?', function(msg) {
					if (msg == 'yes')
						window.location.href = 'auth/logout';
				});

			},

			onSettings : function() {
				var dlg = new MyDesktop.Settings({
					desktop : this.desktop
				});
				dlg.show();
			}
		});
