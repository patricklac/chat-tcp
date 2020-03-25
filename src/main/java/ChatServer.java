
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

	public static void main(String[] args) throws IOException {
		new ChatServer().receive();
		;
	}

	private ArrayList<ChatHandler> handlers;

	public ChatServer() throws IOException {
		handlers = new ArrayList<ChatHandler>();
	}

	private void receive() throws IOException {
		ServerSocket serverSocket = new ServerSocket(6666);
		while (true) {
			Socket socket = serverSocket.accept();
			handlers.add(new ChatHandler(socket, this));
		}

	}

	public void send(ChatItem chatItem) throws IOException {
		for (ChatHandler handler : handlers) {
			handler.send(chatItem);
		}
	}

	public void remove(ChatHandler chatHandler) {
		handlers.remove(chatHandler);

	}

}
