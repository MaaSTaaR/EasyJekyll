// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.util.HashMap;

public class PublishedPost extends Post
{
	// create
	// save
	
	public PublishedPost( File file )
	{
		super( file );
	}
	
	public PublishedPost( String title )
	{
		super( title );
	}

	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, true );
	}

	@Override
	protected void parseFrontMatter( HashMap<String, String> frontMatter )
	{
	}
}
