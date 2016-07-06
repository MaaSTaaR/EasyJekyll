package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import easyjekyll.Environment;
import jekyll.Post;

public class Editor
{
	private JFrame mainWin;
	private JPanel mainPane;
	private Post currPost;
	private JTextPane editor;
	
	private final Color titleColor = new Color( 0, 85, 128 );
	private final String titleAndContentSeparator = "\n\n";
	
	public Editor( Post currPost )
	{
		this.currPost = currPost;
		
		this.editor = new JTextPane();
		
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
		this.mainPane = new JPanel( new BorderLayout() );
		
		// ... //
		
		this.mainWin = new JFrame( "Editing: " + this.currPost.getTitle() );
		
		this.mainWin.setExtendedState( JFrame.MAXIMIZED_BOTH );
		this.mainWin.setContentPane( mainPane );
		
		// ... //
		
		this.createWidgets();
		
		// ... //
		
		mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createToolbar();
		this.createWorkspace();
	}
	
	private void createToolbar()
	{
		this.mainPane.add( new EditorToolbar( this, this.currPost ), BorderLayout.PAGE_START );
	}
	
	private void createWorkspace()
	{
		try
		{
			DefaultStyledDocument document = new DefaultStyledDocument();
			
			editor.setDocument( document );
		
			document.insertString( 0, this.currPost.getTitle(), this.getTitleFormat() );
			document.insertString( this.currPost.getTitle().length(), this.titleAndContentSeparator + this.currPost.getContent(), this.getContentFormat() );
			
			// ... //
			
			editor.setCaretPosition( 0 );
			
			// ... //
			
			JScrollPane scrolledEditor = new JScrollPane( editor );
		
			this.mainPane.add( scrolledEditor, BorderLayout.CENTER );

		}
		catch ( BadLocationException e ) { e.printStackTrace(); }
	}
	
	private AttributeSet getTitleFormat()
	{
		StyleContext styleContext = StyleContext.getDefaultStyleContext();
		
		AttributeSet attributes = styleContext.addAttribute( SimpleAttributeSet.EMPTY, StyleConstants.Foreground, this.titleColor );
		
		attributes = styleContext.addAttribute( attributes, StyleConstants.FontFamily, Environment.getInstance().getFonts().getCookie().getName() );
		attributes = styleContext.addAttribute( attributes, StyleConstants.FontSize, 55 );
		
		return attributes;
	}
	
	private AttributeSet getContentFormat()
	{
		StyleContext styleContext = StyleContext.getDefaultStyleContext();
		
		AttributeSet attributes = styleContext.addAttribute( SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, Environment.getInstance().getFonts().getShare().getName() );
		
		attributes = styleContext.addAttribute( attributes, StyleConstants.FontSize, 35 );
		
		return attributes;
	}
	
	private String getText()
	{
		return this.editor.getText();
	}
	
	public String getTitle()
	{
		return this.getText().split( this.titleAndContentSeparator, 2 )[ 0 ];
	}
	
	public String getContent()
	{
		return this.getText().split( this.titleAndContentSeparator, 2 )[ 1 ];
	}
	
	public void saveNotification( boolean succeed )
	{
		if ( succeed )
		{
			this.mainWin.setTitle( "Editing " + this.currPost.getTitle() );
		}
		else
		{
			// God, Help us!
		}
	}
}
