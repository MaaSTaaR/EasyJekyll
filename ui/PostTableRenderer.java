// [MQH] 5 July 2016
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import easyjekyll.Environment;
import jekyll.Post;
import jekyll.PublishedPost;

public class PostTableRenderer implements TableCellRenderer
{
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
	{
		Post currPost = (Post) value;
		
		// ... //
		
		JPanel cellPane = new JPanel();
		
		cellPane.setBackground( Color.WHITE );
		
		JLabel title = new JLabel( currPost.getTitle() );
		JLabel date = new JLabel( ( ( PublishedPost ) currPost ).getPostDate() );
		
		title.setFont( new Font( Environment.getInstance().getFonts().getDomineBold().getName(), Font.PLAIN, 25 ) );

		cellPane.add( title );
		cellPane.add( date );
		
		// ... //
		
		table.setRowHeight(  70 );
		
		return cellPane;
	}
}
