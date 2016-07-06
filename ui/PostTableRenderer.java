// [MQH] 5 July 2016
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import easyjekyll.Environment;
import jekyll.Post;
import jekyll.PublishedPost;

public class PostTableRenderer implements TableCellRenderer
{
	private final Color selectionColor = new Color( 255, 238, 230 );
	
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
	{
		Color backgroundColor = Color.WHITE;
		
		final Post currPost = (Post) value;
		
		// ... //
		
		JPanel cellPane = new JPanel();
		
		if ( hasFocus )
			backgroundColor = this.selectionColor;
		
		cellPane.setBackground( backgroundColor );
		
		JLabel title = new JLabel( currPost.getTitle() );
		JLabel date = new JLabel( ( ( PublishedPost ) currPost ).getPostDate() );
		
		title.setFont( new Font( Environment.getInstance().getFonts().getDomineBold().getName(), Font.PLAIN, 25 ) );

		cellPane.add( title );
		cellPane.add( date );
		
		// ... //
		
		//table.setRowHeight(  70 ); // Causes Infinite Recursive call! 
		// http://stackoverflow.com/questions/12880867/gettablecellrenderercomponent-is-called-over-and-over-and-makes-100-cpu-usage
		
		// ... //
		
		return cellPane;
	}
}
