// [MQH] 5 July 2016
package ui.posttable;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import jekyll.Post;

public class PostTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 3999355160323444062L;
	private ArrayList<Post> source;
	
	public PostTableModel( ArrayList<Post> source )
	{
		super();
		
		this.source = source;
	}

	@Override
	public int getColumnCount()
	{
		return 1;
	}

	@Override
	public int getRowCount()
	{
		return this.source.size();
	}

	@Override
	public Object getValueAt( int row, int column )
	{
		if ( column == 0 )
			return this.source.get( row );
		
		return null;
	}
}
