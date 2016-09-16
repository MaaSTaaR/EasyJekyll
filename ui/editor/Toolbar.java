// [MQH] 6 July 2016
package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import ui.ActionButton;
import jekyll.Post;
import jekyll.Post.PostType;

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
		
		if ( this.currPost.getType() == PostType.DRAFT )
			this.createPublishButton();
		
		this.createCategoriesButton();
		//this.createTagsButton();
		//this.createCustomFrontMatterButton();
	}
	
	private void createSaveButton()
	{
		ActionButton saveBtn = new ActionButton( "Save" );
		
		saveBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent ev )
			{
				editor.getOperations().save();
			}	
		});
		
		this.toolbar.add( saveBtn );
	}
	
	private void createPublishButton()
	{
		ActionButton publishBtn = new ActionButton( "Publish" );
		
		publishBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				editor.getOperations().publish();
			}	
		});
		
		this.toolbar.add( publishBtn );
	}
	
	private void createCategoriesButton()
	{
		ActionButton categoriesBtn = new ActionButton( "Categories" );
		
		categoriesBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				new Categories( currPost );
			}	
		});
		
		this.toolbar.add( categoriesBtn );
	}
}
