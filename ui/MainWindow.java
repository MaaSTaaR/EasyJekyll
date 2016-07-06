// [MQH] 11 June 2016
package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jekyll.Blog;
import jekyll.Post;
import jekyll.PublishedPost;
import easyjekyll.Environment;

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
		
		this.mainWin.setSize( 600, 600 );
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
		
		this.workspacePane = new JPanel();
		
		this.workspacePane.setLayout( workspaceLayout );
		
		// ... //
		
		this.createPostSpace();
		this.createDraftSpace();
		
		// ... //
		
		this.mainPane.add( workspacePane );
	}
	
	private void createPostSpace()
	{
		Blog blog = Environment.getInstance().getBlog();
		
		// ... //
		
		JPanel postPane = new JPanel();
		
		postPane.setLayout( new BorderLayout() );
		
		// ... //
		
		ArrayList<Post> postsSource = blog.getPosts( Blog.ContentType.PUBLISHED );
		
		JTable postsTable = new JTable( new PostTableModel( postsSource ) );
		
		postsTable.setDefaultRenderer( Object.class, new PostTableRenderer() );
		
		JScrollPane scrolledTable = new JScrollPane( postsTable );
		
		postPane.add( scrolledTable, BorderLayout.CENTER );
		
		// ... //
		
		this.workspacePane.add( postPane, this.postSpaceName );
	}
	
	private void createDraftSpace()
	{
		JPanel draftPane = new JPanel();
		
		JLabel helloWorld = new JLabel( "Hello Drafts World!" );
		
		draftPane.add( helloWorld );
		
		this.workspacePane.add( draftPane, this.draftSpaceName );
	}
}
