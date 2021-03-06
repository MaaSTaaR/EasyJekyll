// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PublishedPost extends Post
{
	private String date;
	private static DateFormat postDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
	public PublishedPost( File file, Blog blog )
	{
		super( file, blog );
		
		this.initDate();
	}
	
	public PublishedPost( String title, Blog blog )
	{
		super( title, blog );
		
		this.initDate();
		
		this.date = postDateFormat.format( new Date() );
	}
	
	// Make a copy of a draft as a published post.
	public PublishedPost( Draft draft, Blog blog )
	{
		super( draft.getTitle(), blog );
		
		this.initDate();
		
		this.date = postDateFormat.format( new Date() );
		
		this.setContent( draft.getContent() );
		this.setTags( draft.getTags() );
		this.setCategories( draft.getCategories() );
	}
	
	private void initDate()
	{
		postDateFormat.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
	}

	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, true );
	}
	
	public String getDate()
	{
		if ( this.date == null )
		{
			this.initDate();
			
			this.date = postDateFormat.format( new Date() );
		}
		
		return this.date.split( " " )[ 0 ];
	}
	
	public String getTime()
	{
		return this.date.split( " " )[ 1 ];
	}
	
	public String getPostDateAsString()
	{
		return this.date;
	}
	
	public Date getPostDate()
	{
		try
		{
			return postDateFormat.parse( this.date );
		} catch ( ParseException e ) { e.printStackTrace(); }
		
		return null;
	}
	
	@Override
	public boolean save()
	{
		this.postDir = this.blog.getPostsDir();
		
		return super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
		String loadedDate = this.frontMatter.get( "date" );
		
		if ( loadedDate == null )
		{
			String dateComponents[] = this.getFilename().split( "-" );
			
			this.date = dateComponents[ 0 ] + "-" + dateComponents[ 1 ] + "-" + dateComponents[ 2 ] + " 00:00:00";
		}
		else
			this.date = loadedDate;	
		
		// Removing parsed front-matters such that only custom ones remain in front-matter hash table.
		this.frontMatter.remove( "date" );
	}

	@Override
	protected String generateFilename()
	{
		return this.getDate() + "-" + Post.getFilenameFromTitle( this.getTitle() );
	}

	@Override
	protected String generateFrontMatter()
	{
		String postFrontMatter = "date: " + this.getPostDateAsString() + "\n";
		
		postFrontMatter += "layout: post\n";
		
		return postFrontMatter;
	}

	@Override
	public PostType getType()
	{
		return PostType.PUBLISHED;
	}
}
