package jekyll;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FrontMatterParser
{
	private BufferedReader reader;
	private HashMap<String, String> fields;
	private boolean started = false;

	public FrontMatterParser( BufferedReader reader )
	{
		this.reader = reader;
		this.fields = new HashMap<String, String>();
	}
	
	public HashMap<String, String> parse()
	{
		try
		{
			String line;
			
			while ( ( line = reader.readLine() ) != null )
			{
				if ( line.equals( "---" ) )
				{
					this.setStarted( !this.frontMatterStarted() );
					
					if ( !this.frontMatterStarted() ) // The end of Front Matter
						return this.fields;
					else
						continue;
				}
				
				if ( this.frontMatterStarted() )
				{
					String field = line.split( ":", 2 )[ 0 ].trim();
					String value = line.split( ":", 2 )[ 1 ].trim();
					
					this.fields.put( field, value );
				}
			}
		}
		catch ( IOException e ) { e.printStackTrace(); }
		
		return null;
	}
	
	private boolean frontMatterStarted()
	{
		return this.started;
	}
	
	private void setStarted( boolean started )
	{
		this.started = started;
	}
}
