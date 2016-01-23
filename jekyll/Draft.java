package jekyll;

import java.io.File;
import java.util.HashMap;

public class Draft extends Post
{
	public Draft( File file, Blog blog )
	{
		super( file, blog );
	}
	
	public Draft( String title, Blog blog )
	{
		super( title, blog );
	}
	
	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, false );
	}
	
	@Override
	public void save()
	{
		this.postDir = this.blog.getDraftsDir();
		
		super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
	}

	@Override
	protected String generateFilename()
	{
		return this.getTitle();
	}
}
