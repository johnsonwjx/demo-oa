<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript"
	src="http://localhost:8082/extjs/ext-all-dev.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://localhost:8082/extjs/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/extjs/desktop/css/desktop.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/myUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
	Ext.Loader.setConfig({
		enabled : true
	});
	Ext.Loader.setPath({
		'Ext.ux.desktop' : '${pageContext.request.contextPath}/extjs/desktop',
		'MyDesktop' : '${pageContext.request.contextPath}/extjs ',
		'Ext.ux' : '${pageContext.request.contextPath}/extjs/ux'
	});
	Ext.require([ 'MyDesktop.App', 'Ext.ux.PreviewPlugin' ]);

	Ext.tip.QuickTipManager.init();
	Ext.apply(Ext.form.field.VTypes, {
		number : function(v) {
			return /[0-9]/i.test(v);
		},
		numberText : '必须是数字',
		numberMask : /[\d\.]/i,
		phone : function(v) {
			//规则区号（3-4位数字）-电话号码（7-8位数字） 
			return /^((\d{11}|\d{12})$/.test(v);
		},
		phoneText : '请输入有效的电话号码',
		phoneMask : /[\d-]/i
	//只允许输入数字和-号 
	});

	var myDesktopApp;
	Ext.onReady(function() {
		myDesktopApp = new MyDesktop.App();
	});
</script>

</head>
<body>
</body>
</html>