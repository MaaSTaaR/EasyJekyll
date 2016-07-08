// [MQH] 11 June 2016
package ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		
		this.mainPane.setLayout( new BoxLayout( mainPane, BoxLayout.PAGE_AXIS ) );
		
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
		this.createMainButtons();
		this.createWorkspace();
	}
	
	private void createMainButtons()
	{
		JPanel mainButtonsPane = new JPanel();
		
		// ... //
		
		JButton postsBtn = new JButton( "Posts" );
		
		postsBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				( (CardLayout) workspacePane.getLayout() ).show( workspacePane, postSpaceName );
			}
			
		});
		
		mainButtonsPane.add( postsBtn );
		
		// ... //
		
		JButton draftsBtn = new JButton( "Drafts" );
		
		draftsBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				( (CardLayout) workspacePane.getLayout() ).show( workspacePane, draftSpaceName );
			}
			
		});
		
		mainButtonsPane.add( draftsBtn );
		
		// ... //
		
		this.mainPane.add( mainButtonsPane );
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
