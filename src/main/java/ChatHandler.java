

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;




public class ChatHandler implements Runnable {
  
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private ChatServer chatServer;
  private Socket socket;


  
  public ChatHandler(Socket socket, ChatServer chatServer) throws IOException {
    this.chatServer = chatServer;
    this.socket=socket;
    out = new ObjectOutputStream (socket.getOutputStream ());
    in = new ObjectInputStream(socket.getInputStream());
    new Thread(this).start();
  }


  public void run() {
    ChatItem chatItem = null;
      try {
        while ((chatItem = (ChatItem) in.readObject()) != null) {
        	chatServer.send(chatItem);
        }
      } catch (SocketException e) {
      } catch (IOException e) {
    	  e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
	  try {
			socket.close();
			chatServer.remove(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
  }


  public void send(ChatItem chatItem) throws IOException {
        out.writeObject(chatItem);
  }

}
