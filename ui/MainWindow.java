// [MQH] 11 June 2016
package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import easyjekyll.Environment;
import ui.posttable.PostListViewer;
import jekyll.Blog;

public class MainWindow
{
	private JFrame mainWin;
	private JPanel mainPane;
	private JPanel workspacePane;
	private String postSpaceName = "Posts Workspace";
	private String draftSpaceName = "Drafts Workdspace";
	
	public MainWindow()
	{
		setNativeLookAndFeel();
		createMainWindow();
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
		
		//optionsPane.setLayout( new BoxLayout( optionsPane, BoxLayout.X_AXIS ) );
		optionsPane.setBorder( BorderFactory.createEmptyBorder( 10, 0, 10, 0 ) );
		
		// ... //
		
		ActionButton deployBtn = new ActionButton( "Deploy" );
		
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
