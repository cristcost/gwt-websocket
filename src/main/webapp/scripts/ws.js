$(function() {
	var appendMessage = function(message, style) {
		$('#messageList').append(
				$('<li class="list-group-item ' + style + '">').html(message));
	};

	var appendServerMessage = function(message) {
		appendMessage(message, 'text-primary');
	};

	var appendClientMessage = function(message) {
		appendMessage(message, 'text-warning');
	};

	var appendErrorMessage = function(message) {
		appendMessage(message, 'text-danger');
	};

	var ws = null;
	$('#connect').click(
			function() {
				var location = 'ws://localhost:8080/ws';
				ws = new WebSocket(location);
				ws.onopen = function() {
					appendClientMessage("Connected to server via WebSocket to "
							+ location);
				};
				ws.onmessage = function(message) {
					if (message.data) {
						appendServerMessage(message.data);
					}
				};
				ws.onclose = function() {
					ws = null;
					appendServerMessage("Disconnected from server");
				};
			});

	$('#send').click(function() {
		var message = $('#intext').val();
		if (ws) {
			ws.send(message);
		} else {
			appendErrorMessage("Not connected, cannot send: " + message);
		}
	});

	$('#disconnect').click(function() {
		if (ws) {
			appendClientMessage("Disconnecting");
			ws.close();
		} else {
			appendErrorMessage("Not connected, can't disconnect");
		}
	});
});