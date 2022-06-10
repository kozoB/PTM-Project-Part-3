package test;

import java.io.IOException;
import java.io.InputStream;        // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.io.OutputStream;       // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.net.ServerSocket;      // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.net.Socket;			   // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET
import java.net.SocketTimeoutException;   // NOT SURE IF CAN USE IMPORTS - IT IS FOR THE SOCKET

public class Server
{
	public interface ClientHandler
	{
		// define...
		void handle(InputStream inFromClient, OutputStream outToClient);
		void close();
	}

	volatile boolean stop;
	public Server()
	{
		stop=false;
	}
	
	
	private void startServer(int port, ClientHandler ch)
	{
		// implement here the server...
		
		try
		{
			ServerSocket server = new ServerSocket(port);
			server.setSoTimeout(1000);
			while(!stop)
			{
				try
				{
					Socket client=server.accept();
					ch.handle(client.getInputStream(), client.getOutputStream());
					ch.close();
					client.close();
				} catch (SocketTimeoutException e) {}
			}
			server.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// runs the server in its own thread
	public void start(int port, ClientHandler ch)
	{
		new Thread(()->startServer(port,ch)).start();
	}
	
	public void stop()
	{
		stop=true;
	}
}
