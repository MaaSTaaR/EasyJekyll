package easyjekyll.frontmatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import easyjekyll.Environment;

public class CustomFrontMatters
{
	private File customFrontMattersFile;
	private ArrayList<CustomFrontMatter> frontmatters;
	
	public CustomFrontMatters() 
	{
		this.frontmatters = new ArrayList<CustomFrontMatter>();
		
		this.customFrontMattersFile = new File( Environment.getInstance().getBlog().getPath() + "/custom_frontmatter.txt" );
		
		if ( !this.customFrontMattersFile.exists() )
			return;
		
		try
		{
			String line = null;
			
			BufferedReader reader = new BufferedReader( new FileReader( this.customFrontMattersFile ) );
			
			while ( ( line = reader.readLine() ) != null )
			{
				String key = line.split( ":" )[ 0 ];
				String value = line.split( ":" )[ 1 ];
				
				this.frontmatters.add( new CustomFrontMatter( key, value ) );
			}
			
			reader.close();
		}
		catch ( FileNotFoundException e ) { e.printStackTrace(); }
		catch ( IOException e ) { e.printStackTrace(); }
	}
	
	public ArrayList<CustomFrontMatter> getFrontMatters()
	{
		return this.frontmatters;
	}
}
