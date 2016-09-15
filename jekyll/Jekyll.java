// [MQH] 10 July 2016
package jekyll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO: Singlton
public class Jekyll
{
	private String jekyll;
	private Runtime runtime;
	private Process serveProcess = null;
	private String serverAddress;
	private String blogPath;
	private NotificationReciever reciever = null;
	
	public Jekyll( String jekyll, String blogPath )
	{
		this.jekyll = jekyll;
		this.blogPath = blogPath;
		this.runtime = Runtime.getRuntime();
	}
	
	public void setNotificationReciever( NotificationReciever reciever )
	{
		this.reciever = reciever;
	}
	
	public void serve()
	{
		if ( this.serveProcess != null )
		{
			if ( this.serveProcess.isAlive() )
			{
				this.serveProcess.destroy();				
				this.serveProcess = null;
				this.serverAddress = null;
			}
		}
		
		try
		{
			String command = this.jekyll + " serve -s " + this.blogPath;
			
			this.useReciever().onStartingServer();
			
			this.serveProcess = runtime.exec( command );
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( serveProcess.getInputStream() ) );
			
			String line = null;
			
			while ( ( line = reader.readLine() ) != null )
			{
				String currLine = line.trim();
				
				if ( currLine.startsWith( "Server address" ) )
				{
					this.serverAddress = currLine.replace( "Server address: ", "" ).trim();
					
					this.useReciever().onServerReady( this.serverAddress );
				}

			}
		} catch ( IOException e ) { e.printStackTrace(); }
	}
	
	public void stopServer()
	{
		if ( this.serveProcess != null )
		{
			this.serveProcess.destroy();
			
			this.useReciever().onServerStopped();
		}
	}
	
	private NotificationReciever useReciever()
	{
		if ( this.reciever == null )
		{
			this.reciever = new NotificationReciever()
			{
				@Override
				public void onStartingServer() { }
				@Override
				public void onServerReady( String serverAddress ) { }

				@Override
				public void onServerStopped() { }
			};
		}
		
		return this.reciever;
	}
	
	public interface NotificationReciever
	{
		public void onStartingServer();
		public void onServerReady( String serverAddress );
		public void onServerStopped();
	}
}
