// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishedPost extends Post
{
	private String date;
	private static DateFormat postDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
	public PublishedPost( File file, Blog blog )
	{
		super( file, blog );
	}
	
	public PublishedPost( String title, Blog blog )
	{
		super( title, blog );
		
		this.date = postDateFormat.format( new Date() );
	}

	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, true );
	}
	
	public String getDate()
	{
		return this.date.split( " " )[ 0 ];
	}
	
	public String getTime()
	{
		return this.date.split( " " )[ 1 ];
	}
	
	public String getPostDate()
	{
		return this.date;
	}
	
	@Override
	public void save()
	{
		this.postDir = this.blog.getPostsDir();
		super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
		String loadedDate = this.frontMatter.get( "date" );
		
		if ( loadedDate == null )
			this.date = this.postDateFormat.format( new Date() );
		else
			this.date = loadedDate;	
	}

	@Override
	protected String generateFilename()
	{
		return this.getDate() + "-" + Post.getFilenameFromTitle( this.getTitle() );
	}

	@Override
	protected String generateFrontMatter()
	{
		String postFrontMatter = "date: " + this.getPostDate() + "\n";
		
		return postFrontMatter;
	}
}
