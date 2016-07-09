// [MQH] 6 July 2016
package ui.posttable;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.ActionButton;
import ui.editor.Editor;
import jekyll.Blog;
import jekyll.Blog.ContentType;
import jekyll.Draft;
import jekyll.Post;
import easyjekyll.Environment;

public class PostListViewer extends JPanel
{
	private static final long serialVersionUID = 38460541805670656L;
	
	private Blog blog;
	private ContentType contentType;

	private JTable postsTable;
	
	public PostListViewer( ContentType contentType )
	{
		this.blog = Environment.getInstance().getBlog();
		this.contentType = contentType;
		
		// ... //
		
		this.setLayout( new BorderLayout() );
		
		// ... //
		
		this.createActionBtns();
		this.createPostTable();
		
		// ... //
	}
	
	private void createActionBtns()
	{
		JPanel actionPane = new JPanel();
		
		// ... //
		
		ActionButton newDraftBtn = new ActionButton( "New Draft" );
		
		newDraftBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				Draft newDraft = blog.createDraft( "Untitled" );
				
				newDraft.setContent( "Your content here." );
				
				new Editor( newDraft );
			}	
		});
		
		actionPane.add( newDraftBtn, BorderLayout.PAGE_START );
		
		// ... //
		
		ActionButton refreshBtn = new ActionButton( "Refresh" );
		
		refreshBtn.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				refreshTable();
			}	
		});
		
		actionPane.add( refreshBtn, BorderLayout.PAGE_START );
		
		// ... //
		
		this.add( actionPane, BorderLayout.PAGE_START );
	}
	
	private void createPostTable()
	{
		this.postsTable = new JTable();
		
		this.refreshTable();
		
		this.postsTable.setRowHeight( 100 );
		this.postsTable.setDefaultRenderer( Object.class, PostTableRendererFactory.getRenderer( contentType ) );
		this.postsTable.setTableHeader( null );
		
		this.postsTable.addMouseListener( new MouseAdapter() 
		{
			public void mouseClicked( MouseEvent e )
			{
				if ( e.getClickCount() == 2 )
					new Editor( ( Post ) postsTable.getValueAt( postsTable.getSelectedRow(), 0 ) );
			}
		});
		
		JScrollPane scrolledTable = new JScrollPane( postsTable );
		
		this.add( scrolledTable, BorderLayout.CENTER );
	}
	
	public void refreshTable()
	{
		ArrayList<Post> postsSource = blog.getPosts( this.contentType );
		
		this.postsTable.setModel( new PostTableModel( postsSource ) );
	}
}
