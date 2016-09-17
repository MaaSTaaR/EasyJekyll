// [MQH] 11 June 2016
package ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import easyjekyll.Environment;
import easyjekyll.deployer.FTPDeployer;
import ui.posttable.PostListViewer;
import jekyll.Blog;
import jekyll.Jekyll.NotificationReciever;

public class MainWindow
{
	private JFrame mainWin;
	private JPanel mainPane;
	private JPanel workspacePane;
	private String postSpaceName = "Posts Workspace";
	private String draftSpaceName = "Drafts Workdspace";
	private ActionButton stopBtn;
	private ActionButton runBtn;
	private Statusbar statusbar;
	
	public MainWindow()
	{
		setNativeLookAndFeel();
		
		// Quick and Dirty
		if ( Preferences.userRoot().node( "net.maastaar.easyjekyll" ).get( "blog_path", null ) == null )
			getBlogPath();
		
		createMainWindow();
		
		// ... //
		
		Environment.getInstance().getJekyll().setNotificationReciever( new NotificationReciever() 
		{
			@Override
			public void onStartingServer()
			{
				statusbar.setStatusMessage( "Starting Server...", Statusbar.StatusType.NOTE );
			}
			
			@Override
			public void onServerReady( String serverAddress )
			{
				statusbar.setStatusMessage( "Server is Running on " + serverAddress, Statusbar.StatusType.SUCCESS );
				
				runBtn.setVisible( false );
				stopBtn.setVisible( true );
			}

			@Override
			public void onServerStopped()
			{
				statusbar.setStatusMessage( "Server Stopped.", Statusbar.StatusType.SUCCESS );
				
				runBtn.setVisible( true );
				stopBtn.setVisible( false );
			}	
		});
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
	
	private void getBlogPath()
	{
		JPanel pathPane = new JPanel();
		JLabel pathMessage = new JLabel( "Please enter the path of the blog:" );
		JTextField pathField = new JTextField( 15 );
		
		pathPane.add( pathMessage );
		pathPane.add( pathField );
					
		// ... //
		
		JOptionPane.showOptionDialog( null, pathPane, "Blog Path", 
										JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
										new String[] { "OK" }, null );
		
		File blogDir = new File( pathField.getText() );
		
		if ( !blogDir.exists() )
		{
			JOptionPane.showMessageDialog( null, "Directory not exist" );
			System.exit( -1 );
		}
		
		if ( !blogDir.isDirectory() )
		{
			JOptionPane.showMessageDialog( null, "Provided path is not a directory" );
			System.exit( -1 );
		}
		
		Preferences.userRoot().node( "net.maastaar.easyjekyll" ).put( "blog_path", pathField.getText() );
		
		try
		{
			Preferences.userRoot().node( "net.maastaar.easyjekyll" ).flush();
		}
		catch ( BackingStoreException e ) { e.printStackTrace(); }
	}
	
	private void createMainWindow()
	{
		this.mainPane = new JPanel();
		
		this.mainPane.setLayout( new BoxLayout( mainPane, BoxLayout.Y_AXIS ) );
		
		// ... //
		
		this.mainWin = new JFrame( "EasyJekyll" );
		
		this.mainWin.setExtendedState( JFrame.MAXIMIZED_BOTH );
		this.mainWin.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.mainWin.setContentPane( mainPane );
		
		// ... //
		
		this.createWidgets();
		
		// ... //
		
		mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createNavigationPane();
		this.createWorkspace();
		this.createOptionsPane();
		this.createStatusPane();
	}
	
	private void createNavigationPane()
	{
		JPanel navPane = new JPanel();
		
		// ... //
		
		NavigationButton postsBtn = new NavigationButton( "Posts" );
		
		postsBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				( (CardLayout) workspacePane.getLayout() ).show( workspacePane, postSpaceName );
			}
			
		});
				
		navPane.add( postsBtn );
		
		// ... //
		
		NavigationButton draftsBtn = new NavigationButton( "Drafts" );
		
		draftsBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				( (CardLayout) workspacePane.getLayout() ).show( workspacePane, draftSpaceName );
			}
			
		});
		
		navPane.add( draftsBtn );
		
		// ... //
		
		this.mainPane.add( navPane );
	}
	
	private void createOptionsPane()
	{
		// ... //
		
		JPanel optionsPane = new JPanel();
		
		optionsPane.setBorder( BorderFactory.createEmptyBorder( 10, 0, 10, 0 ) );
		
		// ... //
		
		runBtn = new ActionButton( "Run Locally" );
		
		runBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				new Thread() 
				{
					public void run()
					{
						Environment.getInstance().getJekyll().serve();
					}
				}.start();
			}	
		});
		
		optionsPane.add( runBtn );
		
		// ... //
		
		stopBtn = new ActionButton( "Stop Server" );
		
		stopBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				new Thread() 
				{
					public void run()
					{
						Environment.getInstance().getJekyll().stopServer();
					}
				}.start();
			}	
		});
		
		stopBtn.setVisible( false );
		
		optionsPane.add( stopBtn );
		
		// ... //
		
		ActionButton settingBtn = new ActionButton( "Settings" );
		
		settingBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				new SettingWindow();
			}	
		});
		
		optionsPane.add( settingBtn );
		
		// ... //
		
		ActionButton deployBtn = new ActionButton( "Deploy" );
		
		deployBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				JPanel passwordPane = new JPanel();
				JLabel passwordMessage = new JLabel( "Please enter your FTP password:" );
				final JPasswordField passwordField = new JPasswordField( 15 );
				
				passwordPane.add( passwordMessage );
				passwordPane.add( passwordField );
							
				// ... //
				
				int selectedOption = JOptionPane.showOptionDialog( 	null, passwordPane, "Password", 
																	JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
																	new String[] { "OK", "Cancel" }, null );
				
				if ( selectedOption != 0 )
					return;
				
				// ... //
				
				new Thread() 
				{
					public void run()
					{
						FTPDeployer deployer = new FTPDeployer( new String( passwordField.getPassword() ) );
						
						deployer.setReciever( new FTPDeployer.NotificationReciever()
						{
							@Override
							public void onConnecting()
							{
								statusbar.setStatusMessage( "Connecting to FTP...", Statusbar.StatusType.NOTE );
							}

							@Override
							public void onConnected()
							{
								statusbar.setStatusMessage( "Connected", Statusbar.StatusType.SUCCESS );
							}

							@Override
							public void onUploadingFile( String filename )
							{
								statusbar.setStatusMessage( "Uploading " + filename, Statusbar.StatusType.NOTE );
							}

							@Override
							public void onDeploymentCompleted()
							{
								statusbar.setStatusMessage( "Deployment Completed", Statusbar.StatusType.SUCCESS );
							}

							@Override
							public void onError( String errorMessage )
							{
								statusbar.setStatusMessage( "Error: " + errorMessage, Statusbar.StatusType.ERROR );
							}	
						});
						
						deployer.deploy();
					}
				}.start();
			}	
		});
		
		optionsPane.add( deployBtn );
		
		// ... //
		
		this.mainPane.add( optionsPane );
	}
	
	private void createStatusPane()
	{
		statusbar = new Statusbar();
		
		statusbar.setStatusMessage( "Server is not running", Statusbar.StatusType.NORMAL );
		
		// ... //
		
		this.mainPane.add( statusbar );
	}
	
	private void createWorkspace()
	{
		CardLayout workspaceLayout = new CardLayout();
		
		workspaceLayout.setHgap( 5 );
		workspaceLayout.setVgap( 5 );
		
		// ... //
		
		this.workspacePane = new JPanel( workspaceLayout );
		
		// ... //
		
		this.createPostSpace();
		this.createDraftSpace();
		
		// ... //
		
		this.mainPane.add( workspacePane );
	}
	
	private void createPostSpace()
	{
		JPanel postPane = new PostListViewer( Blog.ContentType.PUBLISHED );
		
		this.workspacePane.add( postPane, this.postSpaceName );
	}
	
	private void createDraftSpace()
	{
		JPanel draftPane = new PostListViewer( Blog.ContentType.DRAFT );
				
		this.workspacePane.add( draftPane, this.draftSpaceName );
	}
}
