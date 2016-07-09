package ui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SettingWindow
{
	private JPanel mainPane;
	private JFrame mainWin;

	public SettingWindow()
	{
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
		
		this.mainWin.setSize( 400, 300 );
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
		this.createSaveButton();
	}
	
	private void createHost()
	{
		JLabel hostLabel = new JLabel( "Host" );
		
		this.mainPane.add( hostLabel );
		
		// ... //
		
		JTextField hostValue = new JTextField( 15 );
		
		this.mainPane.add( hostValue );
	}
	
	private void createPort()
	{
		
		JLabel portLabel = new JLabel( "Port" );
		
		this.mainPane.add( portLabel );
		
		// ... //
		
		JTextField portValue = new JTextField( 5 );
		
		this.mainPane.add( portValue );
	}
	
	private void createUsername()
	{
		
		JLabel usernametLabel = new JLabel( "Username" );
		
		this.mainPane.add( usernametLabel );
		
		// ... //
		
		JTextField usernameValue = new JTextField( 15 );
		
		this.mainPane.add( usernameValue );
	}
	
	private void createUploadDirectory()
	{
		
		JLabel uploadDirLabel = new JLabel( "Upload Directory" );
		
		this.mainPane.add( uploadDirLabel );
		
		// ... //
		
		JTextField uploadDirValue = new JTextField( 15 );
		
		this.mainPane.add( uploadDirValue );
	}
	
	private void createSaveButton()
	{
		JButton saveBtn = new JButton( "Save" );
		
		this.mainPane.add( saveBtn );
	}
}
