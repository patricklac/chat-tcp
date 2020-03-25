
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatUser {
	private String pseudo;
	private UserInterface userInterface;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public static void main(String[] args) {
		ChatUser chatUser = new ChatUser();
		new ChatFrame(chatUser);
		chatUser.receive();
	}

	public ChatUser() {
		try {
			socket = new Socket("localhost", 6666);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		ChatItem chatItem = null;
		try {
			while ((chatItem = (ChatItem) in.readObject()) != null) {
				userInterface.display(chatItem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void send(String message) {
		try {
			out.writeObject(new ChatItem(pseudo, message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void display(ChatItem chatItem) {
		userInterface.display(chatItem);
	}

}
