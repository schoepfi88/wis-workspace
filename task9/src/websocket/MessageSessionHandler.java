package websocket;

import javax.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;
import com.google.gson.JsonObject;
import models.Message;;

@ApplicationScoped
public class MessageSessionHandler {
	private int messageId = 0;
	private final Set<Session> sessions = new HashSet<Session>();
	private final Set<Message> messages = new HashSet<Message>();
	private static MessageSessionHandler instance = null;

	private MessageSessionHandler() {

	}

	public static MessageSessionHandler getInstance() {
		if (instance == null)
			instance = new MessageSessionHandler();
		return instance;
	}

	public void addSession(Session session) {
		sessions.add(session);
		for (Message mes : messages) {
			JsonObject addMessage = createAddMessage(mes);
			sendToSession(session, addMessage);
		}
	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}

	public List<Message> getDevices() {
		return new ArrayList<Message>(messages);
	}

	public void addMessage(Message message) {
		message.setID(messageId);
		messages.add(message);
		messageId++;
		JsonObject addMessage = createAddMessage(message);
		sendToAllConnectedSessions(addMessage);
	}

	private JsonObject createAddMessage(Message device) {
		JsonObject addMessage = new JsonObject();
		addMessage.addProperty("action", "add");
		addMessage.addProperty("id", device.getID());
		addMessage.addProperty("sender", device.getSender());
		addMessage.addProperty("text", device.getText());
		return addMessage;
	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (Session session : sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, JsonObject message) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException ex) {
			sessions.remove(session);
		}
	}
}