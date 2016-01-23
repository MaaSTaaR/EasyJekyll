// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PublishedPost extends Post
{
	private String date;
	private DateFormat postDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
	public PublishedPost( File file, Blog blog )
	{
		super( file, blog );
	}
	
	public PublishedPost( String title, Blog blog )
	{
		super( title, blog );
		
		this.date = this.postDateFormat.format( new Date() );
	}

	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, true );
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	@Override
	public void save()
	{
		this.postDir = this.blog.getPostsDir();
		
		System.out.println( this.getDate() );
		
		super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
		String loadedDate = this.frontMatter.get( "date" );
		
		if ( date == null )
			this.date = this.postDateFormat.format( new Date() );
		else
			this.date = loadedDate;	
	}

	@Override
	protected String generateFilename()
	{
		return this.getTitle();
	}
}
