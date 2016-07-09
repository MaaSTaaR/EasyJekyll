package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import easyjekyll.Environment;

public class SettingWindow
{
	private JPanel mainPane;
	private JFrame mainWin;
	private Preferences prefs;
	private JTextField hostValue;
	private JTextField portValue;
	private JTextField usernameValue;
	private JTextField uploadDirValue;
	private JTextField jekyllCmdValue;
	
	public SettingWindow()
	{
		this.prefs = Environment.getInstance().getPreferences();
		
		setNativeLookAndFeel();
		createSettingWindow();
	}
	
	private void setNativeLookAndFeel()
	{
		try 
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch ( ClassNotFoundException e ) {}
		catch ( InstantiationException e ) {}
		catch ( IllegalAccessException e ) {}
		catch ( UnsupportedLookAndFeelException e ) {}
	}
	
	private void createSettingWindow()
	{
		this.mainPane = new JPanel( new GridLayout( 0, 2, 20, 15 ) );
		
		this.mainPane.setBorder( BorderFactory.createEmptyBorder( 15, 15, 15, 15 ) );
		
		// ... //
		
		this.mainWin = new JFrame( "Settings" );
		
		this.mainWin.setSize( 400, 340 );
		this.mainWin.setContentPane( mainPane );
		
		// ... //
		
		this.createWidgets();
		
		// ... //
		
		mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createDeploymentMethod();
		this.createDeploymentSetting();
	}
	
	// For now. Just FTP
	private void createDeploymentMethod()
	{
		JLabel deploymentMethodLabel = new JLabel( "Deployment Method" );
		
		this.mainPane.add( deploymentMethodLabel );
		
		// ... //
		
		JLabel deploymentMethodValue = new JLabel( "FTP" );
		
		this.mainPane.add( deploymentMethodValue );
	}
	
	private void createDeploymentSetting()
	{
		this.createHost();
		this.createPort();
		this.createUsername();
		this.createUploadDirectory();
		this.createJekyllCommandPath();
		this.createSaveButton();
	}
	
	private void createHost()
	{
		JLabel hostLabel = new JLabel( "Host" );
		
		this.mainPane.add( hostLabel );
		
		// ... //
		
		hostValue = new JTextField( 15 );
		
		hostValue.setText( this.prefs.get( "ftp_host", "" ) );
		
		this.mainPane.add( hostValue );
	}
	
	private void createPort()
	{
		
		JLabel portLabel = new JLabel( "Port" );
		
		this.mainPane.add( portLabel );
		
		// ... //
		
		portValue = new JTextField( 5 );
		
		portValue.setText( this.prefs.get( "ftp_port", "21" ) );
		
		this.mainPane.add( portValue );
	}
	
	private void createUsername()
	{
		
		JLabel usernametLabel = new JLabel( "Username" );
		
		this.mainPane.add( usernametLabel );
		
		// ... //
		
		usernameValue = new JTextField( 15 );
		
		usernameValue.setText( this.prefs.get( "ftp_username", "" ) );
		
		this.mainPane.add( usernameValue );
	}
	
	private void createUploadDirectory()
	{
		
		JLabel uploadDirLabel = new JLabel( "Upload Directory" );
		
		this.mainPane.add( uploadDirLabel );
		
		// ... //
		
		uploadDirValue = new JTextField( 15 );
		
		uploadDirValue.setText( this.prefs.get( "ftp_upload_dir", "/public_html" ) );
		
		this.mainPane.add( uploadDirValue );
	}
	
	private void createJekyllCommandPath()
	{
		
		JLabel jekyllCmdLabel = new JLabel( "Jekyll Command Path" );
		
		this.mainPane.add( jekyllCmdLabel );
		
		// ... //
		
		jekyllCmdValue = new JTextField( 15 );
		
		jekyllCmdValue.setText( this.prefs.get( "jekyll_command", "jekyll" ) );
		
		this.mainPane.add( jekyllCmdValue );
	}
	
	private void createSaveButton()
	{
		JButton saveBtn = new JButton( "Save" );
		
		saveBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent ev )
			{
				prefs.put( "ftp_host", hostValue.getText() );
				prefs.put( "ftp_port", portValue.getText() );
				prefs.put( "ftp_username", usernameValue.getText() );
				prefs.put( "ftp_upload_dir", uploadDirValue.getText() );
				prefs.put( "jekyll_command", jekyllCmdValue.getText() );
				
				try
				{
					prefs.flush();
					
					mainWin.dispose();
				}
				catch ( BackingStoreException e ) { e.printStackTrace(); }
			}	
		});
		
		this.mainPane.add( saveBtn );
	}
}
