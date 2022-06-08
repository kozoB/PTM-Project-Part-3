package test;


import test.Commands.DefaultIO;
import test.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler
{
	public class SocketIO implements DefaultIO
	{
		@Override
		public String readText()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void write(String text)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public float readVal()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void write(float val)
		{
			// TODO Auto-generated method stub
			
		}
	}
}
