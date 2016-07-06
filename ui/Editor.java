package ui;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jekyll.Post;

public class Editor
{
	private JFrame mainWin;
	private JPanel mainPane;
	private Post currPost;
	
	public Editor( Post currPost )
	{
		this.currPost = currPost;
		
		setNativeLookAndFeel();
		createEditorWindow();
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
	
	private void createEditorWindow()
	{
		this.mainPane = new JPanel();
		
		this.mainPane.setLayout( new BoxLayout( mainPane, BoxLayout.PAGE_AXIS ) );
		
		// ... //
		
		this.mainWin = new JFrame( "Editing: " + this.currPost.getTitle() );
		
		this.mainWin.setExtendedState( JFrame.MAXIMIZED_BOTH );
		//this.mainWin.setSize( 600, 600 );
		//this.mainWin.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.mainWin.setContentPane( mainPane );
		
		// ... //
		
		this.createWidgets();
		
		// ... //
		
		mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createWorkspace();
	}
	
	private void createWorkspace()
	{
		JEditorPane editor = new JEditorPane();
		
		// ... //
		
		editor.setText( this.currPost.getTitle() );
		
		// ... //
		
		JScrollPane scrolledEditor = new JScrollPane( editor );
		
		this.mainPane.add( scrolledEditor );
	}
}
