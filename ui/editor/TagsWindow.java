package ui.editor;

import jekyll.Post;

public class TagsWindow extends ClassificationWindow
{
	public TagsWindow( Post currPost )
	{
		super( currPost, "Tags of " + currPost.getTitle() );
	}

	@Override
	protected void initData()
	{
		String[][] data = new String[ this.currPost.getTags().size() ][ 1 ];
		
		int s = 0;
		
		for ( String currTag: this.currPost.getTags() )
			data[ s++ ][ 0 ] = currTag;
		
		this.setData( data );
		this.setColumnName( "Tag" );
	}

	@Override
	void addData( String currData )
	{
		this.currPost.addTag( currData );
	}

	@Override
	void removeData( String currData )
	{
		this.currPost.removeTag( currData );
	}
}
