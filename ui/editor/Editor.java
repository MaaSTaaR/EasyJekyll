package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	private JTextPane editor;
	
	private Statusbar statusbar;
	private Operations operations;
	
	private Post currPost;
	
	private final Color titleColor = new Color( 0, 85, 128 );
	private final String titleAndContentSeparator = "\n\n";
	
	private boolean documentModified = false;
	
	public Editor( Post currPost )
	{
		this.currPost = currPost;
		
		this.mainWin = new JFrame();
		this.editor = new JTextPane();
		this.statusbar = new Statusbar( this );
		this.operations = new Operations( this );
		
		this.setNativeLookAndFeel();
		this.createEditorWindow();
		this.setShortcuts();
	}
	
	private void refreshEditorTitle()
	{
		this.mainWin.setTitle( ( ( this.documentModified ) ? "(*) " : "" ) + this.currPost.getTitle() );
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
	
	private void setShortcuts()
	{
		this.editor.getInputMap().put( KeyStroke.getKeyStroke( "ctrl S" ), "enterPressed" );
		this.editor.getActionMap().put( "enterPressed", new AbstractAction() 
		{
			private static final long serialVersionUID = 5039415689079797737L;

			@Override
			public void actionPerformed( ActionEvent arg0 )
			{
				getOperations().save();
			}
		});
	}
	
	private void createEditorWindow()
	{
		this.mainPane = new JPanel( new BorderLayout() );
		
		// ... //
		
		this.mainWin.setExtendedState( JFrame.MAXIMIZED_BOTH );
		this.mainWin.setContentPane( mainPane );
		
		this.mainWin.addWindowListener( new WindowListener() 
		{
			@Override
			public void windowClosing( WindowEvent e )
			{
				if ( documentModified )
				{
					int response = JOptionPane.showConfirmDialog( null, "Do you want to save?", "Saving", JOptionPane.YES_NO_OPTION );
					
					if ( response == JOptionPane.YES_OPTION )
						getOperations().save();
				}
			}
			
			@Override
			public void windowActivated( WindowEvent e ) { }

			@Override
			public void windowClosed( WindowEvent e ) { }

			@Override
			public void windowDeactivated( WindowEvent e ) { }

			@Override
			public void windowDeiconified( WindowEvent e ) { }

			@Override
			public void windowIconified( WindowEvent e ) { }

			@Override
			public void windowOpened( WindowEvent e ) { }
		});
		
		// ... //
		
		this.refreshEditorTitle();
		this.createWidgets();
		
		// ... //
		
		this.mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createToolbar();
		this.createWorkspace();
		this.createStatusbar();
	}
	
	private void createToolbar()
	{
		this.mainPane.add( new Toolbar( this ), BorderLayout.PAGE_START );
	}
	
	private void createStatusbar()
	{
		this.mainPane.add( this.statusbar, BorderLayout.PAGE_END );
	}
	
	private void createWorkspace()
	{
		try
		{
			DefaultStyledDocument document = new DefaultStyledDocument();
			
			this.editor.setDocument( document );
		
			document.insertString( 0, this.currPost.getTitle(), this.getTitleFormat() );
			document.insertString( this.currPost.getTitle().length(), this.titleAndContentSeparator + this.currPost.getContent(), this.getContentFormat() );
			
			document.addDocumentListener( new DocumentListener()
			{
				@Override
				public void changedUpdate( DocumentEvent ev )
				{
					modificationNotification();
				}

				@Override
				public void insertUpdate( DocumentEvent ev )
				{
					modificationNotification();
				}

				@Override
				public void removeUpdate( DocumentEvent ev )
				{
					modificationNotification();
				}
				
			});
			
			// ... //
			
			this.editor.setCaretPosition( 0 );
			
			// ... //
			
			JScrollPane scrolledEditor = new JScrollPane( this.editor );
			
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
			this.documentModified = false;
			
			this.refreshEditorTitle();
			this.statusbar.setStatusMessage( "Saved", Statusbar.StatusType.SUCCESS );
			
			// Not Yet!
			//if ( this.currPost.isNewlyCreated() )
			//	this.postListViewer.refreshTables();
		}
		else
		{
			this.statusbar.setStatusMessage( "Could not save the file!", Statusbar.StatusType.ERROR );
		}
	}
	
	public void modificationNotification()
	{
		this.documentModified = true;
		
		this.statusbar.setStatusMessage( "Modified", Statusbar.StatusType.NOTE );
		this.refreshEditorTitle();
	}
	
	public void publishNotification( boolean succeed )
	{
		if ( succeed )
		{
			JOptionPane.showMessageDialog( null, "The draft has been published. This window will be closed now" );
			
			this.mainWin.dispose();
		}
	}
	
	public Post getPost()
	{
		return this.currPost;
	}
	
	public Operations getOperations()
	{
		return this.operations;
	}
}
