var socket = new WebSocket("ws://localhost:8080/task9/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var message = JSON.parse(event.data);
    if (message.action === "add") {
    	displayMessages(message);
    }
}

function addMessage(sender, text) {
    var addMessageAction = {
        action: "add",
        sender: sender,
        text: text
    };
    socket.send(JSON.stringify(addMessageAction));
}

function displayMessages(message) {
	$("#allMessages").append(getPanelHtmlString(message.sender, message.text));
}

function getPanelHtmlString(sender, text){
	return "<div class=\"panel panel-info\">" + 
				"<div class=\"panel-body\">" + 
					sender + ": "  + text + 
				"</div>" + 
			"</div>";
}


function postMessage() {
    var sender = $("#message_sender").val();
    var text = $("#message_text").val();
    $("#message_sender").val("");
    $("#message_text").val("");
    addMessage(sender, text);
}

$(document).ready(function () {
	   $("#message_form").submit(function(event){
	       event.preventDefault();
	   });
});

