// [MQH] 5 July 2016
package ui.posttable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import easyjekyll.Environment;
import jekyll.Post;

public class PostTableRenderer implements TableCellRenderer
{
	private final Color selectionColor = new Color( 255, 238, 230 );
	
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
	{
		Color backgroundColor =  null;
		
		Post currPost = (Post) value;
		
		// ... //
		
		JPanel cellPane = new JPanel( new BorderLayout( 25, 25 ) );
		
		// ... //
		
		if ( hasFocus )
			backgroundColor = this.selectionColor;
		
		cellPane.setBackground( backgroundColor );
		
		JLabel title = new JLabel( currPost.getTitle() );
		
		title.setFont( new Font( Environment.getInstance().getFonts().getPoiretOne().getName(), Font.PLAIN, 30 ) );
		
		cellPane.add( title, BorderLayout.PAGE_START );
		
		// ... //
		
		//table.setRowHeight(  70 ); // Causes Infinite Recursive call! 
		// http://stackoverflow.com/questions/12880867/gettablecellrenderercomponent-is-called-over-and-over-and-makes-100-cpu-usage
		
		// ... //
		
		return cellPane;
	}
}
