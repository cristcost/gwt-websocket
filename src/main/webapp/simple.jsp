<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Simple WebSocket test</title>

<link href="${pageContext.request.contextPath}/styles/bootstrap.min.css"
	rel="stylesheet" media="screen">

<script type="text/javascript">
	var wsurl = "ws://${pageContext.request.localName}:${pageContext.request.localPort}${pageContext.request.contextPath}/ws";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/ws.js"></script>

</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h1>Simple WebSocket test</h1>
			<p>
				Basic example inspired from <a
					href="https://gist.github.com/manzke/1021982">the one of manzke</a>
				(thanks!)
			</p>
		</div>

		<div class="row">
			<div id="application" class="col-md-12">

				<input type="text" id="intext" />

				<button id="send">Send</button>

				<button id="connect">Connect</button>

				<button id="disconnect">Disconnect</button>

				<ul id="messageList" class="list-group"></ul>
			</div>
		</div>
	</div>
</body>
</html>