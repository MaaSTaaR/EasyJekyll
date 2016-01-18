package jekyll;

import java.io.File;
import java.util.HashMap;

public class Draft extends Post
{
	public Draft( File file )
	{
		super( file );
	}
	
	public Draft( String title )
	{
		super( title );
	}
	
	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, false );
	}

	@Override
	protected void parseFrontMatter( HashMap<String, String> frontMatter )
	{
	}
}
