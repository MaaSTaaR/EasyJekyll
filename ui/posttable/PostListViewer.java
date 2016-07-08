// [MQH] 6 July 2016
package ui.posttable;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.editor.Editor;
import jekyll.Blog;
import jekyll.Post;
import easyjekyll.Environment;

public class PostListViewer extends JPanel
{
	private static final long serialVersionUID = 38460541805670656L;

	public PostListViewer( Blog.ContentType contentType )
	{
		Blog blog = Environment.getInstance().getBlog();
		
		// ... //
		
		this.setLayout( new BorderLayout() );
		
		// ... //
		
		ArrayList<Post> postsSource = blog.getPosts( contentType );
		
		final JTable postsTable = new JTable( new PostTableModel( postsSource ) );
		
		postsTable.setRowHeight( 80 );
		postsTable.setDefaultRenderer( Object.class, PostTableRendererFactory.getRenderer( contentType ) );
		postsTable.setTableHeader( null );
		
		postsTable.addMouseListener( new MouseAdapter() 
		{
			public void mouseClicked( MouseEvent e )
			{
				if ( e.getClickCount() == 2 )
					new Editor( ( Post ) postsTable.getValueAt( postsTable.getSelectedRow(), 0 ) );
			}
		});
		
		JScrollPane scrolledTable = new JScrollPane( postsTable );
		
		this.add( scrolledTable, BorderLayout.CENTER );
		
		// ... //
	}
}
