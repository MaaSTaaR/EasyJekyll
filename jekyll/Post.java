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
	private String content;
	private ArrayList<String> categories;
	private ArrayList<String> tags;
	private boolean newPost;
	protected Blog blog;
	protected File postDir;
	protected HashMap<String, String> frontMatter;
	
	private Post( Blog blog )
	{
		this.categories = new ArrayList<String>();
		this.tags = new ArrayList<String>();
		this.content = "";
		this.blog = blog;
	}
	
	// Load already exists post
	public Post( File file, Blog blog )
	{
		this( blog );
		
		this.file = file;
		this.newPost = false;
		
		try
		{
			BufferedReader reader = new BufferedReader( new FileReader( file ) );
			
			FrontMatterParser frontMatterParser = new FrontMatterParser( reader );
			frontMatter = frontMatterParser.parse();
			
			this.parseGeneralFrontMatter(); // The common parts of the Front Matter such as title and categories.
			this.parseFrontMatter(); // For children to use their own fields in the Front Matter.
			
			String line;
			
			while ( ( line = reader.readLine() ) != null )
				this.content += line + "\n";
		} 
		catch ( FileNotFoundException e ) { e.printStackTrace(); }
		catch ( IOException e ) { e.printStackTrace(); }
	}


	// Create a new post
	public Post( String title, Blog blog )
	{
		this( blog );
		
		this.title = title;
		this.newPost = true;
	}
	
	private void parseGeneralFrontMatter()
	{
		this.title = this.frontMatter.get( "title" ).replace( "\"", "" );
		
		// ... //
		
		if ( frontMatter.get( "category" ) != null )
			this.categories.add( this.frontMatter.get( "category" ) );
		
		if ( frontMatter.get( "categories" ) != null )
		{
			String[] categories = this.frontMatter.get( "categories" ).replace( "[", "" ).replace( "]", "" ).split( "," );
			
			for ( int s = 0; s < categories.length; s++ )
				this.categories.add( categories[ s ] );
		}
		
		// ... //
		
		if ( frontMatter.get( "tag" ) != null )
			this.tags.add( this.frontMatter.get( "tag" ) );
		
		if ( frontMatter.get( "tags" ) != null )
		{
			String[] tags = this.frontMatter.get( "tags" ).replace( "[", "" ).replace( "]", "" ).split( "," );
			
			for ( int s = 0; s < tags.length; s++ )
				this.tags.add( tags[ s ] );
		}
	}
	
	public void save()
	{
		/*if ( this.newPost )
		{
			//
			//this.file = new File();
		}*/
		
		System.out.println( this.generateFilename() );
			
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle( String title )
	{
		this.title = title;
	}
	
	public ArrayList<String> getCategories()
	{
		return this.categories;
	}
	
	public void addCategory( String category )
	{
		this.categories.add( category );
	}
	
	public ArrayList<String> getTags()
	{
		return this.tags;
	}
	
	public void addTag( String tag )
	{
		this.tags.add( tag );
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	public void setContent( String content )
	{
		this.content = content;
	}
	
	protected HashMap<String, String> getFrontMatter()
	{
		return this.frontMatter;
	}
	
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
	
	abstract protected void parseFrontMatter();
	abstract protected String generateFilename();
}
