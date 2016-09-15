// [MQH] 14 September 2016
package easyjekyll.deployer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.prefs.Preferences;

import jekyll.Blog;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import easyjekyll.Environment;

public class FTPDeployer
{
	private Preferences prefs;
	private String host, username, uploadDir, password;
	private Integer port;
	private FTPClient ftp;
	private NotificationReciever reciever;
	
	public FTPDeployer( String password )
	{
		this.prefs = Environment.getInstance().getPreferences();
		
		this.host = this.prefs.get( "ftp_host", null );
		this.port = Integer.parseInt( this.prefs.get( "ftp_port", null ) );
		this.username = this.prefs.get( "ftp_username", null );
		this.uploadDir = this.prefs.get( "ftp_upload_dir", null );
		this.password = password;
		
		// ... //
		
		if ( this.uploadDir.charAt( this.uploadDir.length() - 1 ) != '/' )
			this.uploadDir += "/";
		
		// ... //
		
		this.ftp = new FTPClient();
	}
	
	private void connect() throws Exception
	{
		this.getClient().connect( this.host, this.port );
		this.getClient().setFileType( FTP.BINARY_FILE_TYPE );
		this.getClient().login( this.username, password );
		
		this.getClient().enterLocalPassiveMode();
		
		if ( this.getClient().getReplyCode() != 230 )
			throw new Exception( this.getClient().getReplyString() );
		
		this.getClient().changeWorkingDirectory( this.uploadDir );
	}
	
	private void uploadFile( File file, String remoteDir ) throws Exception
	{
		FileInputStream input = new FileInputStream( file );
		String remotePath = this.uploadDir + remoteDir;
		
		this.useReciever().onUploadingFile( file.getName() );
		
		boolean uploaded = this.getClient().storeFile( remotePath + file.getName(), input );
		
		if ( !uploaded )
			throw new Exception( "Cannot upload " + file.getName() );
	}
	
	private void uploadDir( File currDir ) throws Exception
	{
		String remoteDir = "";
		
		if ( !currDir.getName().equals( "_site" ) )
		{
			remoteDir = currDir.getPath().replace( Environment.getInstance().getBlog().getSiteDir().getPath(), "" ) + "/";
			
			this.getClient().makeDirectory( this.uploadDir + remoteDir );
		}
		
		for ( File currFile: currDir.listFiles() )
		{
			if ( currFile.isFile() )
				this.uploadFile( currFile, remoteDir );
			else if ( currFile.isDirectory() )
				this.uploadDir( currFile );
		}
	}
	
	public void deploy()
	{
		try
		{
			Blog blog = Environment.getInstance().getBlog();
			
			Environment.getInstance().getJekyll().build();
			
			this.useReciever().onConnecting();
			
			this.connect();
			
			this.useReciever().onConnected();
			
			this.uploadDir( blog.getSiteDir() );
			
			this.getClient().disconnect();
			
			this.useReciever().onDeploymentCompleted();
		}
		catch ( SocketException e ) { this.useReciever().onError( e.getMessage() ); }
		catch ( IOException e ) { this.useReciever().onError( e.getMessage() ); }
		catch ( Exception e ) { this.useReciever().onError( e.getMessage() ); }
	}
	
	private FTPClient getClient()
	{
		return this.ftp;
	}
	
	private NotificationReciever useReciever()
	{
		if ( this.reciever == null )
		{
			this.reciever = new NotificationReciever()
			{

				@Override
				public void onConnecting() {}

				@Override
				public void onConnected() {}

				@Override
				public void onUploadingFile( String filename ) {}

				@Override
				public void onDeploymentCompleted() {}

				@Override
				public void onError( String errorMessage ) {}
			};
		}
		
		return this.reciever;
	}
	
	public void setReciever( NotificationReciever reciever )
	{
		this.reciever = reciever;
	}
	
	public interface NotificationReciever
	{
		public void onConnecting();
		public void onConnected();
		public void onUploadingFile( String filename );
		public void onDeploymentCompleted();
		public void onError( String errorMessage );
	}
}
