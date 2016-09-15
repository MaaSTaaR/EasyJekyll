// [MQH] 11 June 2016
package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
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
		
		ActionButton deployBtn = new ActionButton( "Deploy" );
		
		deployBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				JPanel passwordPane = new JPanel();
				JLabel passwordMessage = new JLabel( "Please enter your FTP password:" );
				JPasswordField passwordField = new JPasswordField( 15 );
				
				passwordPane.add( passwordMessage );
				passwordPane.add( passwordField );
				
				// ... //
				
				int selectedOption = JOptionPane.showOptionDialog( 	null, passwordPane, "Password", 
																	JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
																	new String[] { "OK", "Cancel" }, null );
				
				if ( selectedOption != 0 )
					return;
				
				// ... //
				
				FTPDeployer deployer = new FTPDeployer( new String( passwordField.getPassword() ) );
				
				deployer.deploy();
			}	
		});
		
		optionsPane.add( deployBtn );
		
		// ... //
		
		ActionButton settingBtn = new ActionButton( "Deployment Settings" );
		
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
