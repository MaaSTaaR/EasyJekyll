package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
	private final Color titleColor = new Color( 0, 85, 128 );
	private JTextPane editor;
	
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
		this.createToolbar();
		this.createWorkspace();
	}
	
	private void createToolbar()
	{
		JToolBar toolbar = new JToolBar();
		
		JButton saveBtn = new JButton( "Save" );
		
		saveBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent arg0 )
			{
				try
				{
					currPost.setContent( editor.getText( currPost.getTitle().length() + 2, editor.getText().length() - ( currPost.getTitle().length() + 2 ) ) );
					
					currPost.save();
				}
				catch ( BadLocationException e ) { e.printStackTrace(); }
			}	
		});
		
		toolbar.add( saveBtn );
		
		this.mainPane.add( toolbar, BorderLayout.PAGE_START );
	}
	
	private void createWorkspace()
	{
		try
		{
			editor = new JTextPane();
		
			// ... //
			
			DefaultStyledDocument document = new DefaultStyledDocument();
			
			editor.setDocument( document );
		
			document.insertString( 0, this.currPost.getTitle(), this.getTitleFormat() );
			document.insertString( this.currPost.getTitle().length(), "\n\n" + this.currPost.getContent(), this.getContentFormat() );
			
			// ... //
			
			editor.setCaretPosition( 0 );
			
			// ... //
			
			JScrollPane scrolledEditor = new JScrollPane( editor );
		
			this.mainPane.add( scrolledEditor );

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
}
