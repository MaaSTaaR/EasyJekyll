// [MQH] 6 July 2016
package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import jekyll.Post;

public class Toolbar extends JPanel
{
	private static final long serialVersionUID = 7626941592528068304L;
	private Editor editor;
	private Post currPost;
	private JToolBar toolbar;
	
	public Toolbar( Editor editor )
	{
		super( new BorderLayout() );
		
		this.editor = editor;
		this.currPost = this.editor.getPost();
		
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
				editor.getOperations().save();
			}	
		});
		
		toolbar.add( saveBtn );
	}
}
