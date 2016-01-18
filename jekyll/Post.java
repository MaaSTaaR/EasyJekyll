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
	
	// Load already exists post
	public Post( File file )
	{
		this.file = file;
		
		try
		{
			BufferedReader reader = new BufferedReader( new FileReader( file ) );
			
			FrontMatterParser frontMatterParser = new FrontMatterParser( reader );
			HashMap<String, String> frontMatterFields = frontMatterParser.parse();
			
			this.parseGeneralFrontMatter( frontMatterFields ); // The common parts of the Front Matter such as title and categories.
			this.parseFrontMatter( frontMatterFields ); // For children to parse their own fields in the Front Matter.
		} 
		catch ( FileNotFoundException e ) { e.printStackTrace(); }
		catch ( IOException e ) { e.printStackTrace(); }
	}


	// Create a new post
	public Post( String title )
	{
		this.title = title;
	}
	
	private void parseGeneralFrontMatter( HashMap<String, String> frontMatter )
	{
		this.title = frontMatter.get( "title" ).replace( "\"", "" );
	}
	
	public String getTitle()
	{
		return this.title;
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
