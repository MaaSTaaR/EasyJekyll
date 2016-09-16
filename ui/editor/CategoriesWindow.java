package ui.editor;

import jekyll.Post;

public class CategoriesWindow extends ClassificationWindow
{
	public CategoriesWindow( Post currPost )
	{
		super( currPost, "Categories of " + currPost.getTitle() );
	}

	@Override
	protected void initData()
	{
		String[][] data = new String[ this.currPost.getCategories().size() ][ 1 ];
		
		int s = 0;
		
		for ( String currCategory: this.currPost.getCategories() )
			data[ s++ ][ 0 ] = currCategory;
		
		this.setData( data );
		this.setColumnName( "Category" );
	}

	@Override
	void addData( String currData )
	{
		this.currPost.addCategory( currData );
	}

	@Override
	void removeData( String currData )
	{
		this.currPost.removeCategory( currData );
	}
}
