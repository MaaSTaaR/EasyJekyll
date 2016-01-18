// 17 Jan 2016
package jekyll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Post
{
	private File file;
	private String title;
	private ArrayList<String> categories;
	private ArrayList<String> tags;
	
	private Post()
	{
		this.categories = new ArrayList<String>();
		this.tags = new ArrayList<String>();
	}
	
	// Load already exists post
	public Post( File file )
	{
		this();
		
		this.file = file;
		
		try
		{
			BufferedReader reader = new BufferedReader( new FileReader( file ) );
			
			FrontMatterParser frontMatterParser = new FrontMatterParser( reader );
			HashMap<String, String> frontMatterFields = frontMatterParser.parse();
			
			this.parseGeneralFrontMatter( frontMatterFields ); // The common parts of the Front Matter such as title and categories.
			this.parseFrontMatter( frontMatterFields ); // For children to use their own fields in the Front Matter.
		} 
		catch ( FileNotFoundException e ) { e.printStackTrace(); }
		catch ( IOException e ) { e.printStackTrace(); }
	}


	// Create a new post
	public Post( String title )
	{
		this();
		
		this.title = title;
	}
	
	private void parseGeneralFrontMatter( HashMap<String, String> frontMatter )
	{
		this.title = frontMatter.get( "title" ).replace( "\"", "" );
		
		// ... //
		
		if ( frontMatter.get( "category" ) != null )
			this.categories.add( frontMatter.get( "category" ) );
		
		if ( frontMatter.get( "categories" ) != null )
		{
			String[] categories = frontMatter.get( "categories" ).replace( "[", "" ).replace( "]", "" ).split( "," );
			
			for ( int s = 0; s < categories.length; s++ )
				this.categories.add( categories[ s ] );
		}
		
		// ... //
		
		if ( frontMatter.get( "tag" ) != null )
			this.tags.add( frontMatter.get( "tag" ) );
		
		if ( frontMatter.get( "tags" ) != null )
		{
			String[] tags = frontMatter.get( "tags" ).replace( "[", "" ).replace( "]", "" ).split( "," );
			
			for ( int s = 0; s < tags.length; s++ )
				this.tags.add( tags[ s ] );
		}
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public ArrayList<String> getCategories()
	{
		return this.categories;
	}
	
	public ArrayList<String> getTags()
	{
		return this.tags;
	}
	
	abstract protected void parseFrontMatter( HashMap<String, String> frontMatter );
	
	public static String parseTitleFromFilename( String filename, boolean withDate )
	{
		String title = "";
		String[] nameParts = filename.split( "-" );
		
		int titleStartIdx = ( withDate ) ? 3 : 0;
		
		for ( ; titleStartIdx < nameParts.length; titleStartIdx++ )
			title += Character.toUpperCase( nameParts[ titleStartIdx ].charAt( 0 ) ) +  nameParts[ titleStartIdx ].substring( 1 ) + " ";
		
		title = title.split( "\\." )[ 0 ];
		
		return title;
	}
}
