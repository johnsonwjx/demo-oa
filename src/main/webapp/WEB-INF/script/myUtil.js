/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-2
 */
var auths = [];// 不改变，全局权限 （在创建权限选择selectitemgrid有用到）

/**
 * 创建grid的工厂
 * 
 * @param ModelName
 * @param height
 * @param width
 * @param title
 * @param border
 *            是否表格线
 * @param rowNum
 *            是否序号
 * @returns
 */
function createGrid(ModelName, height, width, title, border, rowNum, pageSize) {
	if (Ext.isEmpty(ModelName))
		return;
	var columns = [];
	Ext.Ajax.request({
		url : ModelName + '/getGridColumns',
		async : false,
		success : function(response) {
			columns = Ext.JSON.decode(response.responseText);
		},
		failure : function(response) {
			Ext.Msg.alert('错误', response.responseText);
			return;
		}
	});
	if (columns.length <= 0)
		return;
	var fields = [], gridType = reBuildColumns(columns), isTree = false, StoreType, bbar = null, storeConfig = {}, gridConfig = {}//
	, url, reader;
	if (gridType == 'Ext.tree.Panel') {
		isTree = true;
		url = ModelName + '/listAll';
		reader = {
			type : 'json'
		};
	} else {
		url = ModelName + '/list';
		reader = {
			type : 'json',
			root : 'data'
		};
	}
	for ( var i = 0; i < columns.length; i++) {
		var column = columns[i];
		if (!column.useField)
			continue;
		fields.push({
			name : column.dataIndex,
			type : column.type,
			useNull : column.idField
		});
	}
	Ext.define(ModelName, {
		extend : 'Ext.data.Model',
		fields : fields,
	});
	rowNum && Ext.Array.insert(columns, 0, [ new Ext.grid.RowNumberer() ]);

	var listproxy = {
		type : 'ajax',
		url : url,
		reader : reader,
		listeners : {
			exception : function(me, request, operation, eOpts) {
				Ext.Msg.alert('错误', '发生错误');
				return;
			}
		}
	};

	var queryProxy = {
		type : 'ajax',
		url : ModelName + '/query',
		reader : reader,
		listeners : {
			exception : function(me, request, operation, eOpts) {
				Ext.Msg.alert('错误', '发生错误');
				return;
			}
		}
	};

	storeConfig = {
		storeId : ModelName,
		model : ModelName,
		autoLoad : true,
		proxy : listproxy
	};
	if (isTree) {
		StoreType = 'Ext.data.TreeStore';
		Ext.apply(storeConfig, {
			root : {
				expanded : true,
			}
		});
	} else {
		Ext.apply(storeConfig, {
			pageSize : pageSize
		});
		StoreType = 'Ext.data.Store';
	}

	var store = Ext.create(StoreType, storeConfig);
	store.listproxy = listproxy;
	store.queryProxy = queryProxy;

	store.on('load', function(me, records, sussful) {
		me.setProxy(me.listproxy);
	});
	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
		clicksToMoveEditor : 1,
		autoCancel : false
	});

	gridConfig = {
		header : false,
		border : border,
		store : store,
		columns : columns,
		height : height,
		selModel : {
			mode : isTree?'SINGLE':"SIMPLE",
		   allowDeselect:true
		},
		width : width,
		plugins : [ rowEditing ],
		viewConfig : {
			trackOver : false,
			stripeRows : false,
			// getRowClass : function(record, rowIndex, rowParams, store) {
			// return '男';
			// },
			plugins : [ {
				ptype : 'preview',
				bodyField : '',
				expanded : true,
				pluginId : 'preview'
			} ]
		}
	};

	if (isTree) {
		Ext.apply(gridConfig, {
			rootVisible : false
		});
	} else {
		bbar = Ext.create('Ext.PagingToolbar', {
			store : store,
			displayInfo : true,
			displayMsg : '显示 {0} - {1} of {2}',
			emptyMsg : "没有数据",
			items : [ '-', {
				text : '表顶数据',
				pressed : true,
				enableToggle : true,
				toggleHandler : function(btn, pressed) {
					var preview = grid.getView().getPlugin('preview');
					preview.toggleExpanded(pressed);
				}
			} ]
		});
		Ext.apply(gridConfig, {
			bbar : bbar
		});
	}

	grid = Ext.create(gridType, gridConfig);
	return grid;
}

// var fields=[];
// for(var i=0;i<grid.columns.length;i++){
// fields.push(grid.columns[i]['dataIndex']);
// }
// for ( var i = 0; i < modis.length; i++) {
// var temp={};
// for(var j=0;j<fields.length;j++){
// temp[fields[j]]=modis[i].get(fields[j]);
// }
// params.push(temp);
// }

/**
 * 组织树状格式json数据
 * 
 * @param nodes
 *            要修改的节点
 * @param fields
 *            需要获得数据的属性字段
 * @returns {Array}
 */
function buildTreeData(nodes, fields) {
	var treeData = [];
	if (nodes.length <= 0)
		return [];
	for ( var i = 0; i < nodes.length; i++) {
		treeData.push(formatNode(nodes[i], fields));
	}
	return treeData;
}
/*
 * 格式话节点成json的树节点，孩子key为children @param node @param fields @returns {Object}
 */
function formatNode(node, fields) {
	var temp = {
		children : []
	};
	for ( var i = 0; i < fields.length; i++)
		temp[fields[i]] = node.get(fields[i]);
	var children = node.childNodes;
	if (children.length > 0) {
		for ( var i = 0; i < children.length; i++) {
			var child = children[i];
			// 如果是修改数据，不是新添加的,不需要递归了
			// child.childNodes=[];
			temp.children.push(formatNode(child, fields));
		}
	}
	return temp;
}

/**
 * 创建操作栏
 * 
 * @param modelName
 * @param idPro
 * @param store
 * @param editor
 * @param grid
 * @returns
 */
function createTbar(modelName, idPro, store, editor, grid) {
	return [
			{
				text : '添加',
				icon : 'images/icons/user_add.png',
				handler : function() {
					editor.cancelEdit();
					var model = Ext.create(modelName, {
						expanded : true,
						leaf : true
					});
					if (store.tree) {
						var sm = grid.getSelectionModel();
						var sel = null;
						if (sm.getCount() <= 0) {
							sel = store.getRootNode();
						} else
							sel = sm.getSelection()[0];
						if (model.get('children') != undefined)
							model.set('children', []);
						if (sel.isLeaf()) {
							sel.updateInfo(true, {
								leaf : false
							});
						}
						sel.appendChild(model);
						// // 是数据裤，保存了的
						// if (sel.get('id') || sel.isRoot())
						// grid.modiNodes.push(sel);
						sel.expand(true);
					} else {
						if (model.get('roles') != undefined)
							model.set('roles', []);
						if (model.get('auths') != undefined)
							model.set('auths', []);
						if (model.get('department') != undefined)
							model.set('department', null);
						if (model.get('gender') != undefined)
							model.set('gender', 'Female');
						store.insert(0, model);
						editor.startEdit(0, 0);
					}
				}
			},
			'-',
			{
				text : '删除',
				icon : 'images/icons/user_delete.png',
				handler : function() {
					var sm = grid.getSelectionModel();
					editor.cancelEdit();
					var sels = sm.getSelection(), ids = [], noCount = 0;
					if (sm.getCount() > 0) {
						var news = store.getNewRecords();
						for ( var i = 0; i < sels.length; i++) {
							var sel = sels[i];
							if (Ext.isEmpty(sel.get(idPro))) {
								if (news && news.length > 0
										&& Ext.Array.contains(news, sel))
									if (store.tree)
										for ( var i = 0; i < sels.length; i++) {
											sels[i].remove();
										}
									else
										store.remove(sels);
								else
									noCount++;
								continue;
							}
							ids.push(sel.get(idPro));
						}
						if (noCount > 0 && ids.length <= 0) {
							Ext.Msg.alert('提示', '删除了' + noCount
									+ '个保存没刷新数据,没有操作数据');
							return;
						} else if (noCount > 0 && ids.length > 0) {
							Ext.Msg.alert('提示', '删除了' + noCount + '个保存没刷新数据,共有'
									+ (sels.length - noCount) + '个数据操作');
						}
						grid.getEl().mask("正在删除数据....");
						Ext.Ajax
								.request({
									url : modelName + '/manager/delete',
									method : 'DELETE',
									headers : {
										'Content-Type' : 'application/json; charset=UTF-8'
									},
									params : Ext.JSON.encode(ids),
									success : function(response) {
										if (store.tree)
											for ( var i = 0; i < sels.length; i++) {
												sels[i].remove();
											}
										else
											store.remove(sels);
										grid.getEl().unmask();
									},
									failure : function(response) {
										Ext.Msg.alert('错误',
												response.responseText);
										store.rejectChanges();
										grid.getEl().unmask();
									}
								});
					} else
						Ext.Msg.alert('提示', '没有操作数据');
				}
			},
			'-',
			{
				text : '保存',
				icon : 'images/icons/accept.png',
				handler : function() {
					var modis = store.getModifiedRecords();
					if (modis && modis.length > 0) {
						grid.getEl().mask("正在保存数据....");
						var params = [];
						if (store.tree) {
							var fields = [], temp = [];
							for ( var i = 0; i < grid.columns.length; i++) {
								fields.push(grid.columns[i]['dataIndex']);
							}
							var newRecords = store.getNewRecords(), newParents = [];
							for ( var i = 0; i < newRecords.length; i++) {
								var parent = newRecords[i].parentNode;
								if (!parent || parent.isRoot()) {
									temp.push(newRecords[i]);
									continue;
								} else {
									if (!Ext.Array.contains(newRecords, parent))
										newParents.push(parent);
								}
							}
							temp = Ext.Array.push(temp, newParents);
							var updateRecords = store.getUpdatedRecords();
							if (newParents.length > 0)
								for ( var i = 0; i < updateRecords.length; i++) {
									if (Ext.Array.contains(newParents,
											updateRecords[i]))
										updateRecords = Ext.Array
												.remove(updateRecords,
														updateRecords[i]);
								}
							temp = Ext.Array.push(temp, updateRecords);
							params = buildTreeData(temp, fields);
						} else {
							for ( var i = 0; i < modis.length; i++) {
								params.push(modis[i].data);
							}
						}
						Ext.Ajax
								.request({
									url : modelName + '/manager/saveorupdate',
									method : 'POST',
									params : Ext.JSON.encode(params),
									headers : {
										'Content-Type' : 'application/json; charset=UTF-8'
									},
									success : function(response) {
										grid.getEl().unmask();
										if (response.responseText
												.indexOf("html") > 0)
											window.location.href = login.html;
										if (!store.tree) {
											store.commitChanges();
										} else
											store.reload();
									},
									failure : function(response) {
										Ext.Msg.alert('错误',
												response.responseText);
										if (!store.tree) {
											store.rejectChanges();
										} else
											store.reload();
										grid.getEl().unmask();
									}
								});
					}
				}
			}, '-', {
				text : '取消',
				icon : 'images/icons/delete.gif',
				handler : function() {
					if (!store.tree) {
						store.rejectChanges();
					} else
						store.reload();
				}
			}, '-', {
				text : '刷新',
				icon : 'images/icons/table_refresh.png',
				handler : function() {
					store.reload();
				}
			}, '-', {
				text : '高级搜索',
				icon : 'images/icons/information.png',
				handler : function() {
					condiPanel(modelName);
				}
			}, {
				text : '打印',
				icon : 'images/icons/grid.png',
				handler : function() {
					window.print();
				}
			}, {
				text : '创建图表数据',
				icon : 'images/icons/feed_add.png',
				handler : function() {
					
				}
			}];
}

function createEmpCondiItems() {
	return [ {
		fieldLabel : '登录名',
		itemId : 'username'
	}, {
		fieldLabel : '真实姓名',
		itemId : 'realname'
	}, {
		fieldLabel : '年龄大于：',
		itemId : 'gtAge',
		xtype : 'numberfield',
		minValue : 0,
		maxValue : 100
	}, {
		fieldLabel : '薪金大于:',
		xtype : 'numberfield',
		minValue : 1000,
		itemId : 'gtSalary'
	}, {
		fieldLabel : '是否停职',
		itemId : 'die',
		xtype : 'checkboxfield'
	}, {
		fieldLabel : '部门',
		id : 'department',
		xtype : 'triggerfield',
		editable : false,
		onTriggerClick : function() {
			var tree = createTree('department', null, false, 300, 400);
			tree.on('select', function(me, treeRecord, index) {
				this.ownerCt.selNode = treeRecord;
			});
			Ext.create('Ext.window.Window', {
				title : '设置部门',
				width : 300,
				height : 400,
				modal : true,
				items : tree,
				buttons : [ {
					text : '取消',
					handler : function() {
						this.ownerCt.ownerCt.selNode = null;
						this.ownerCt.ownerCt.close();
					}
				}, {
					text : '保存',
					handler : function() {
						var selNode = this.ownerCt.ownerCt.selNode;
						if (selNode) {
							if (!selNode.isLeaf()) {
								Ext.Msg.alert('提示', '请选择子部门');
								this.ownerCt.ownerCt.selNode = null;
								return;
							}
							var department = Ext.getCmp('department');
							department.realValue = selNode;
							department.setValue(selNode.get('name'));
						}
						this.ownerCt.ownerCt.close();
					}
				} ],
			}).show();
		}
	} ];
}

function query() {
	var win = this.ownerCt.ownerCt.ownerCt;
	var form = win.items.items[0], modelName = form.modelName;
	if (!form.isValid()) {
		Ext.Msg.alert('提示', '请填写正确数据');
		return;
	}
	var store = Ext.StoreMgr.lookup(modelName), params = null;
	if (modelName == 'employee')
		params = empQueryModel(form, store);
	else if (modelName == 'department')
		params = deptQueryModel(form, store);
	store.setProxy(store.queryProxy);
	if (store.tree)
		store.load({
			params : params
		});
	else
		store.loadPage(1, {
			params : params
		});
	win.close();
}

function empQueryModel(form, store) {
	var username = form.getComponent('username').getValue();
	var realname = form.getComponent('realname').getValue();
	var gtSalary = form.getComponent('gtSalary').getValue();
	var die = form.getComponent('die').getValue();
	var gtAge = form.getComponent('gtAge').getValue();
	var department = form.getComponent('department').realValue;
	var deptId = null;
	if (department) {
		deptId = department.get('id');
	}
	return {
		'username' : username,
		'realname' : realname,
		'gtSalary' : gtSalary,
		'die' : die,
		'gtAge' : gtAge,
		'deptId' : deptId
	};
}

function createDeptCondiItems() {

}

function deptQueryModel(form, store) {
	var model = {};
	return model;
}

/**
 * 创建自定义查询工厂
 * 
 * @param modelName
 * @returns
 */
function condiPanel(modelName) {
	var items = [], form = null;
	if (modelName == 'employee') {
		items = createEmpCondiItems();
	} else if (modelName == 'department') {
		items = createDeptCondiItems();
	}
	form = Ext.widget({
		xtype : 'form',
		modelName : modelName,
		layout : 'form',
		collapsible : true,
		url : modelName + '/queryByCondi',
		frame : true,
		header : false,
		bodyPadding : '5 5 0',
		fieldDefaults : {
			msgTarget : 'side',
			labelWidth : 75
		},
		defaultType : 'textfield',
		items : items,
		buttons : [ {
			text : '取消',
			handler : function() {
				this.ownerCt.ownerCt.ownerCt.close();
			}
		}, {
			text : '搜索',
			handler : query
		} ]
	});

	Ext.create('Ext.window.Window', {
		title : '高级搜索',
		width : 600,
		height : 400,
		modal : true,
		layout : 'fit',
		items : form
	}).show();
}

/**
 * 重新装饰列
 * 
 * @param columns
 * @returns
 */
function reBuildColumns(columns) {
	var gridType = 'Ext.grid.Panel';
	for ( var i = 0; i < columns.length; i++) {
		var column = columns[i];
		column.align = 'center';
		switch (column.type) {
		case 'int':
			column.editor = {
				xtype : 'numberfield'
			};
			break;
		case 'string':
			column.editor = {
				xtype : 'textfield'
			};
			break;
		case 'boolean':
			column.editor = {
				xtype : 'checkbox',
				cls : 'x-grid-checkheader-editor'
			};
			column.trueText = '是', column.falseText = '否';
			break;
		case 'float':
			column.editor = {
				xtype : 'numberfield',
			};
			break;
		case 'Gender':
			column.type = 'string';
			column.editor = {
				xtype : 'radiogroup',
				value : 'Female',
				items : [ {
					boxLabel : '男',
					name : column.dataIndex,
					inputValue : 'Female',
					checked : true
				}, {
					boxLabel : '女',
					name : column.dataIndex,
					inputValue : 'Male'
				} ]
			};
			break;
		default:
			column.type = 'auto';
		}

		if (column.xtype == 'datecolumn') {
			column.editor = {
				xtype : 'datefield',
				format : 'Y/m/d',
				minValue : '2006/01/01',
				minText : '不能早于2006.1.1',
				maxValue : Ext.Date.format(new Date(), 'Y/m/d')
			};
		} else if (column.xtype == 'actioncolumn') {
			column.icon = 'images/icons/cog_edit.png';
			if (column.dataIndex == 'roles') {
				column.handler = createRoleSelect;
			} else if (column.dataIndex == 'auths') {
				column.handler = createAuthoSelect;
			} else if (column.dataIndex == 'department') {
				column.handler = createDeptSelect;
			}
		} else if (column.xtype == 'treecolumn') {
			gridType = 'Ext.tree.Panel';
			column.align = 'left';
		}

		if (column.editor) {
			column.editor.allowBlank = column.allowBlank;
			if (column.idField)
				column.editor = null;
			else if (column.dataIndex == 'username') {
				column.editor.listeners = {
					'blur' : function(me) {
						if (me.getValue().length <= 5) {
							me.markInvalid("登录名长度必须大于5个字符");
							me.setValue('');
							Ext.Msg.alert('提示', '登录名长度必须大于5个字符');
							return;
						}
						Ext.Ajax.request({
							url : 'employee/checkName/' + me.getValue(),
							success : function(response) {
								if (!Ext.isEmpty(response.responseText)) {
									me.markInvalid(response.responseText);
									me.setValue('');
									Ext.Msg.alert('提示', response.responseText);
									return;
								}
							},
							failure : function(response) {
								Ext.Msg.alert('错误', response.responseText);
								return;
							}
						});
					}
				};
			}
		}

		if (column.renderer) {
			column.renderer = myRender;
		}

		/*
		 * if (column.editor) {
		 * 
		 * if (column.dataIndex.toLocaleLowerCase().indexOf('email') ||
		 * column.text.toLocaleLowerCase().indexOf('email')) {
		 * column.editor.vtype = 'email'; } else if
		 * (column.dataIndex.toLocaleLowerCase().indexOf('phone') ||
		 * column.text.toLocaleLowerCase().indexOf('phone')) {
		 * column.editor.vtype = 'phone'; } }
		 */
	}
	return gridType;
}

/**
 * 列渲染
 * 
 * @param value
 * @param metaData
 * @param record
 * @param rowIndex
 * @param colIndex
 * @param store
 * @param view
 * @returns
 */
function myRender(value, metaData, record, rowIndex, colIndex, store, view) {
	/*
	 * //设置权限Button function authsButton(authArr, pid,rowIndex) { var valueArr =
	 * []; for ( var i = 0; authArr && i < authArr.length; i++) {
	 * valueArr.push(authArr[i]['id']); } record.auths=valueArr; return "<input
	 * type='button' value='设置权限' onclick='createAuthoSelect(" + pid
	 * +","+rowIndex+")'/>"; }
	 */
	function genderFormat(origin) {
		if (origin == 'Female')
			return '男';
		else if (origin == 'Male')
			return '女';
	}

	var column = metaData.column;
	if (!column)
		column = grid.columns[colIndex];

	if (column.text == '性别')
		return genderFormat(value);

}

/**
 * 设置角色选择panel创建
 * 
 * @param view
 * @param rowIndex
 * @param colIndex
 * @param item
 * @param e
 * @param record
 * @param row
 * @returns
 */
function createRoleSelect(view, rowIndex, colIndex, item, e, record, row) {
	if (!record.get('id')) {
		Ext.Msg.alert('提示', "为新增数据，请操作保存后刷新的数据");
		return 

		

				

		

						

		

				

		

									

		

				

		

						

		

				

		

									

		

				

		

						

		

				

		

		

	}
	var allData = [];
	Ext.Ajax.request({
		url : 'role/listAll',
		async : false,
		success : function(response) {
			allData = Ext.JSON.decode(response.responseText);
		},
		failure : function(response) {
			Ext.Msg.alert('错误', response.responseText);
			return;
		}
	});
	if (allData.length <= 0)
		return;
	return createItemSelect('角色选择', '所有角色', '已选角色', 'roleName', 'id', record,
			allData, 'role/setRoles/', 'roleIds', false);

}

/**
 * // 设置权限的选择pannal创建
 * 
 * @param view
 * @param rowIndex
 * @param colIndex
 * @param item
 * @param e
 * @param record
 * @param row
 * @returns
 */
function createAuthoSelect(view, rowIndex, colIndex, item, e, record, row) {
	if (auths.length <= 0) {
		Ext.Ajax.request({
			url : 'authority/listAll',
			async : false,
			success : function(response) {
				auths = Ext.JSON.decode(response.responseText);
			},
			failure : function(response) {
				Ext.Msg.alert('错误', response.responseText);
				return;
			}
		});
	}
	if (auths.length <= 0) {
		Ext.Msg.alert('提示', "系统没初始话权限，请管理员初始权限");
		return;
	}
	return createItemSelect('权限选择', '所有权限', '已选权限', 'authDesc', 'id', record,
			auths, 'role/setAuths/', 'auths', true);

}

/**
 * 基本的创建选择panel工厂
 * 
 * @param title
 * @param fromTitle
 * @param toTitle
 * @param displayField
 * @param valueField
 * @param record
 * @param allData
 * @param oprUrl
 * @param oprKey
 * @param changeStore
 * @returns
 */
function createItemSelect(title, fromTitle, toTitle, displayField, valueField,
		record, allData, oprUrl, oprKey, changeStore) {
	var store = Ext.create('Ext.data.Store', {
		fields : [ {
			name : valueField
		}, {
			name : displayField
		}, ],
		data : allData
	});
	var selValues;
	if (changeStore) {
		selValues = [];
		var oprArr = record.get(oprKey);
		for ( var i = 0; i < oprArr.length; i++) {
			selValues.push(oprArr[i][valueField]);
		}
	} else {
		if (record.oprKey)
			selValues = record.oprKey;
		else
			selValues = record.raw[oprKey];
	}
	var itemselect = Ext.create('Ext.ux.form.ItemSelector', {
		anchor : '100%',
		imagePath : 'images/',
		store : store,
		displayField : displayField,
		valueField : valueField,
		value : selValues,
		allowBlank : true,
		msgTarget : 'side',
		fromTitle : fromTitle,
		toTitle : toTitle,
	});
	Ext
			.create(
					'Ext.window.Window',
					{
						title : title,
						layout : 'fit',
						width : 400,
						height : 400,
						modal : true,
						items : itemselect,
						buttons : [
								{
									text : '恢复初始值',
									handler : function() {
										itemselect.reset();
									}
								},
								{
									text : '取消',
									handler : function() {
										itemselect.ownerCt.close();
									}
								},
								{
									text : '保存',
									handler : function() {
										if (changeStore) {
											var newOprArr = [];
											if (itemselect.value
													&& itemselect.value.length > 0) {
												for ( var i = 0; i < allData.length; i++)
													Ext.Array
															.contains(
																	itemselect.value,
																	allData[i][valueField])
															&& newOprArr
																	.push(allData[i]);
											}
											record.set(oprKey, newOprArr);
										} else {
											Ext.getBody().mask('正在设置...');
											Ext.Ajax
													.request({
														url : oprUrl
																+ record
																		.get('id'),
														headers : {
															'Content-Type' : 'application/json; charset=UTF-8'
														},
														params : Ext.JSON
																.encode(itemselect.value),
														method : 'PUT',
														success : function(
																response) {
															Ext.getBody()
																	.unmask();
															record.oprKey = itemselect.value;
															Ext.Msg
																	.alert(
																			'提示',
																			response.responseText);
														},
														failure : function(
																response) {
															Ext.getBody()
																	.unmask();
															Ext.Msg
																	.alert(
																			'错误',
																			response.responseText);
															itemselect.ownerCt
																	.close();
															return;
														}
													});
										}
										itemselect.ownerCt.close();
									}
								} ]
					}).show();
}

/**
 * 创建部门选择窗口
 * 
 * @param view
 * @param rowIndex
 * @param colIndex
 * @param item
 * @param e
 * @param record
 * @param row
 * @returns
 */
function createDeptSelect(view, rowIndex, colIndex, item, e, record, row) {
	var tree = createTree('department', null, false, 300, 350), department = record
			.get('department'), text = Ext.create('Ext.draw.Text', {
		text : '当前选择部门为:' + (department ? department.name : '空')
	});
	tree.on('select', function(me, treeRecord, index) {
		text.setText('当前选择部门为:' + treeRecord.get('name'));
		this.selNode = treeRecord;
	});
	var panel = Ext.create('Ext.form.Panel', {
		herder : false,
		padding : '10 0 0 10',
		width : 300,
		height : 50,
		frame : true,
		items : [ text ]
	});
	Ext.create('Ext.window.Window', {
		title : '设置部门',
		width : 300,
		height : 400,
		modal : true,
		items : [ panel, tree ],
		buttons : [ {
			text : '没有部门',
			handler : function() {
				text.setText('当前选择部门为:空');
				tree.selNode = null;
			}
		}, {
			text : '取消',
			handler : function() {
				this.ownerCt.ownerCt.close();
			}
		}, {
			text : '保存',
			handler : function() {
				var selNode = this.ownerCt.ownerCt.items.items[1].selNode;
				if (selNode !== undefined) {
					if (selNode === null) {
						record.set('department', null);
					} else {
						if (!selNode.isLeaf()) {
							selNode = null;
							Ext.Msg.alert('提示', '请选择子部门');
							return;
						}
						record.set('department', {
							id : selNode.get('id'),
							name : selNode.get('name')
						});
					}
				}
				this.ownerCt.ownerCt.close();
			}
		} ],
	}).show();
}

/**
 * 创建树的工厂
 * 
 * @param ModelName
 * @param title
 * @param header
 * @param width
 * @param height
 * @returns
 */
function createTree(ModelName, title, header, width, height) {
	if (Ext.isEmpty(ModelName))
		return;
	var columns = [];
	Ext.Ajax.request({
		url : ModelName + '/getGridColumns',
		async : false,
		success : function(response) {
			columns = Ext.JSON.decode(response.responseText);
		},
		failure : function(response) {
			Ext.Msg.alert('错误', response.responseText);
			return;
		}
	});
	if (columns.length <= 0)
		return;
	var fields = [];
	for ( var i = 0; i < columns.length; i++) {
		var column = columns[i];
		if (!column.useField)
			continue;
		fields.push({
			name : column.dataIndex,
			type : column.type,
			useNull : column.idField
		});
	}
	fields.push({
		name : 'text'
	});
	Ext.define(ModelName, {
		extend : 'Ext.data.Model',
		fields : fields
	});

	var store = Ext.create('Ext.data.TreeStore', {
		model : ModelName,
		root : {
			expanded : true,
		},
		proxy : {
			type : 'ajax',
			url : ModelName + '/listAll',
			reader : {
				type : 'json'
			}
		}

	});
	return Ext.create('Ext.tree.Panel', {
		title : title,
		header : header,
		width : width,
		height : height,
		store : store,
		rootVisible : false
	});
}
