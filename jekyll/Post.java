// 17 Jan 2016
package jekyll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class Post
{
	private File file;
	
	private String title = null;
	private String content;
	private ArrayList<String> categories;
	private ArrayList<String> tags;
	private String filename;
	
	private boolean newPost;
	protected Blog blog;
	protected File postDir;
	protected HashMap<String, String> frontMatter;
	private boolean titleChanged = false;
	
	public enum PostType { PUBLISHED, DRAFT };
	
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
		this.filename = file.getName();
		
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
		
		this.setTitle( title );
		this.newPost = true;
		this.filename = this.generateFilename();
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
	
	public boolean save()
	{
		String fileContent;
		
		if ( this.newPost )
		{
			this.file = new File( this.getPostDir().getAbsolutePath() + "/" + this.generateFilename() );
			
			try 
			{
				this.file.createNewFile();
			} catch ( IOException e ) { e.printStackTrace(); }
		}
		else
		{
			if ( this.titleChanged )
			{
				File newFile = new File( this.getPostDir().getAbsolutePath() + "/" + this.generateFilename() );
				
				if ( !this.file.renameTo( newFile ) )
					return false;
				
				this.titleChanged = false;
				this.file = newFile;
			}
		}
		
		String frontMatter = "---\n" + this.generateGeneralFrontMatter() + this.generateFrontMatter() + "---\n";
		
		fileContent = frontMatter + this.getContent();
		
		try 
		{
			FileWriter writer = new FileWriter( this.file );
			writer.write( fileContent );
			writer.close();
		} catch ( IOException e ) { e.printStackTrace(); }
		
		return true;
	}
	
	private String generateGeneralFrontMatter()
	{
		String generalFrontMatter = "title: \"" + this.getTitle() + "\"\n";
		
		if ( this.categories.size() > 0 )
		{
			generalFrontMatter += "categories: [";
			
			Iterator<String> catIt = this.categories.iterator();
			
			while ( catIt.hasNext() )
			{
				String currCat = catIt.next();
				
				generalFrontMatter += currCat;
				
				if ( catIt.hasNext() )
					generalFrontMatter += ",";
			}
			
			generalFrontMatter += "]\n";
		}
		
		// ... //
		
		if ( this.tags.size() > 0 )
		{
			generalFrontMatter += "tags: [";
			
			Iterator<String> tagsIt = this.tags.iterator();
			
			while ( tagsIt.hasNext() )
			{
				String currTag = tagsIt.next();
				
				generalFrontMatter += currTag;
				
				if ( tagsIt.hasNext() )
					generalFrontMatter += ",";
			}
			
			generalFrontMatter += "]\n";
		}
		
		// ... //
		
		return generalFrontMatter;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle( String title )
	{
		if ( this.title != null )
			if ( !this.title.equals( title ) )
				this.titleChanged = true;
		
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
	
	public File getPostDir()
	{
		return this.postDir;
	}
	
	protected HashMap<String, String> getFrontMatter()
	{
		return this.frontMatter;
	}
	
	protected void setTags( ArrayList<String> tags )
	{
		this.tags = tags;
	}
	
	protected void setCategories( ArrayList<String> categories )
	{
		this.categories = categories;
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
	
	public static String getFilenameFromTitle( String title )
	{
		String[] titleParts = title.split( " " );
		String filename = "";
		
		for ( int startIdx = 0; startIdx < titleParts.length; startIdx++ )
		{
			filename += titleParts[ startIdx ];
			
			if ( ( startIdx + 1 ) < titleParts.length )
				filename += "-";
		}
		
		filename += ".md";
		
		return filename.toLowerCase();
	}
	
	public boolean isNewlyCreated()
	{
		return this.newPost;
	}
	
	public boolean delete()
	{
		return this.file.delete();
	}
	
	public String getFilename()
	{
		return this.filename;
	}
	
	abstract public PostType getType();
	abstract protected void parseFrontMatter();
	abstract protected String generateFrontMatter();
	abstract protected String generateFilename();
}
