// [MQH] 6 July 2016
package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import easyjekyll.Environment;
import jekyll.PublishedPost;

public class PublishedPostTableRenderer extends PostTableRenderer
{
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
	{
		PublishedPost currPost = (PublishedPost) value;
		
		JPanel cellPane = (JPanel) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		
		JLabel date = new JLabel( currPost.getPostDateAsString() );
		
		date.setFont( new Font( Environment.getInstance().getFonts().getShare().getName() , Font.PLAIN, 20 ) );
		
		cellPane.add( date, BorderLayout.PAGE_END );
		
		return cellPane;
	}
}
