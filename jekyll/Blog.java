// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.util.ArrayList;

public class Blog
{
	private File path;
	private File postsDir;
	private File draftsDir;
	
	// deploy
	
	public Blog( String path )
	{
		this.path = new File( path );
		this.postsDir = new File( this.path.getPath() + "/_posts" );
		this.draftsDir = new File( this.path.getPath() + "/_drafts" );
	}
	
	public PublishedPost createPost( String title )
	{
		return new PublishedPost( title, this );
	}
	
	public File getPostsDir()
	{
		return this.postsDir;
	}
	
	public File getDraftsDir()
	{
		return this.draftsDir;
	}
	
	public ArrayList<String> getPostsTitles()
	{
		return this.getTitles( this.postsDir, false );
	}
	
	public ArrayList<String> getDraftsTitles()
	{
		return this.getTitles( this.draftsDir, true );
	}
	
	public ArrayList<Post> getPostsList()
	{
		ArrayList<Post> posts = new ArrayList<Post>();
		
		for ( File currPost: this.postsDir.listFiles() )
			if ( !currPost.isDirectory() )
				posts.add( new PublishedPost( currPost, this ) );
		
		return posts;
	}
	
	private ArrayList<String> getTitles( File dir, boolean isDraft )
	{
		ArrayList<String> titles = new ArrayList<String>();
		
		for ( File currPost: dir.listFiles() )
			if ( !currPost.isDirectory() )
			{
				// Yuck! Thanks Java for not supporting abstract static methods :-/ ! Quick and Dirty
				String title = ( !isDraft ) ? ( PublishedPost.parseTitleFromFilename( currPost.getName() ) ) : Draft.parseTitleFromFilename( currPost.getName() );
				
				titles.add( title );
			}
		
		return titles;
	}
	
	
}
