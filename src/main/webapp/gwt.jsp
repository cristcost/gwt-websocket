<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>GWT WebSocket test</title>

<link href="${pageContext.request.contextPath}/styles/bootstrap.min.css"
	rel="stylesheet" media="screen">

<script type="text/javascript">
	var config = {
		wsurl : "ws://${pageContext.request.localName}:${pageContext.request.localPort}${pageContext.request.contextPath}/ws"
	};
</script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/wsstudy/wsstudy.nocache.js"></script>

</head>
<body>
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>

	<div class="container">
		<div class="jumbotron">
			<h1>GWT WebSocket test</h1>
			<p>Testing various ways to use GWT and WebSockets</p>
		</div>

		<div class="row">
			<div id="application" class="col-md-12"></div>
		</div>
	</div>
</body>
</html>