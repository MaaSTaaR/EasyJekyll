// [MQH] 6 July 2016
package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import jekyll.Post;

public class EditorToolbar extends JPanel
{
	private static final long serialVersionUID = 7626941592528068304L;
	private Editor editor;
	private Post currPost;
	private JToolBar toolbar;
	
	public EditorToolbar( Editor editor, Post currPost )
	{
		super( new BorderLayout() );
		
		this.editor = editor;
		this.currPost = currPost;
		
		this.createToolbar();
		
		this.add( this.toolbar );
	}
	
	private void createToolbar()
	{
		this.toolbar = new JToolBar();
		
		this.toolbar.setFloatable( false );
		
		this.createSaveButton();
	}
	
	private void createSaveButton()
	{
		JButton saveBtn = new JButton( "Save" );
		
		saveBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent ev )
			{
				currPost.setTitle( editor.getTitle() );
				currPost.setContent( editor.getContent() );
				
				editor.saveNotification( currPost.save() );
			}	
		});
		
		toolbar.add( saveBtn );
	}
}
