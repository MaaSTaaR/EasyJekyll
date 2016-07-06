// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.util.ArrayList;

public class Blog
{
	private File path;
	private File postsDir;
	private File draftsDir;
	
	public static enum ContentType { PUBLISHED, DRAFT };
	
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
	
	public Draft createDraft( String title )
	{
		return new Draft( title, this );
	}
	
	public File getPostsDir()
	{
		return this.postsDir;
	}
	
	public File getDraftsDir()
	{
		return this.draftsDir;
	}
	
	/*public ArrayList<String> getPostsTitles()
	{
		return this.getTitles( this.postsDir, false );
	}
	
	public ArrayList<String> getDraftsTitles()
	{
		return this.getTitles( this.draftsDir, true );
	}*/
	
	public ArrayList<PublishedPost> getPostsList()
	{
		ArrayList<PublishedPost> posts = new ArrayList<PublishedPost>();
		
		for ( File currPost: this.postsDir.listFiles() )
			if ( !currPost.isDirectory() )
				posts.add( new PublishedPost( currPost, this ) );
		
		return posts;
	}
	
	public ArrayList<Draft> getDraftsList()
	{
		ArrayList<Draft> drafts = new ArrayList<Draft>();
		
		for ( File currDraft: this.draftsDir.listFiles() )
			if ( !currDraft.isDirectory() )
				drafts.add( new Draft( currDraft, this ) );
		
		return drafts;
	}
	
	public ArrayList<Post> getPosts( ContentType type )
	{
		ArrayList<Post> posts = new ArrayList<Post>();
		
		File source = ( type == ContentType.PUBLISHED ) ? this.postsDir : this.draftsDir;
		
		for ( File currPost: source.listFiles() )
			if ( !currPost.isDirectory() )
				posts.add( new PublishedPost( currPost, this ) );
		
		return posts;
	}
	
	/*private ArrayList<String> getTitles( File dir, boolean isDraft )
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
	}*/
	
	
}
