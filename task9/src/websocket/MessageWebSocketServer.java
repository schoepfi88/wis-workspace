package websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Message;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ServerEndpoint("/actions")
public class MessageWebSocketServer {

	private MessageSessionHandler sessionHandler = MessageSessionHandler.getInstance();

	@OnOpen
	public void open(Session session) {
		sessionHandler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		sessionHandler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		JsonObject json = new Gson().fromJson(message, JsonObject.class);
		String act = json.get("action").getAsString();
		String sender = json.get("sender").getAsString();
		String text = json.get("text").getAsString();

		if ("add".equals(act)) {
			Message chatMessage = new Message(sender, text);
			sessionHandler.addMessage(chatMessage);
		}
	}
}
