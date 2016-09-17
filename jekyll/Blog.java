// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Blog
{
	private File path;
	private File postsDir;
	private File draftsDir;
	private File siteDir;
	
	public static enum ContentType { PUBLISHED, DRAFT };
	
	// deploy
	
	public Blog( String path )
	{
		this.path = new File( path );
		this.postsDir = new File( this.path.getPath() + "/_posts" );
		this.draftsDir = new File( this.path.getPath() + "/_drafts" );
		this.siteDir = new File( this.path.getPath() + "/_site" );
		
		if ( !this.draftsDir.exists() )
			this.draftsDir.mkdir();
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
	
	public File getSiteDir()
	{
		return this.siteDir;
	}
	
	public String getPath()
	{
		return this.path.getPath();
	}
	
	/*public ArrayList<String> getPostsTitles()
	{
		return this.getTitles( this.postsDir, false );
	}
	
	public ArrayList<String> getDraftsTitles()
	{
		return this.getTitles( this.draftsDir, true );
	}*/
	
	/*public ArrayList<PublishedPost> getPostsList()
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
	}*/
	
	public ArrayList<Post> getPosts( ContentType type )
	{
		ArrayList<Post> posts = new ArrayList<Post>();
		
		File source = ( type == ContentType.PUBLISHED ) ? this.postsDir : this.draftsDir;
		
		for ( File currPost: source.listFiles() )
			if ( !currPost.isDirectory() )
			{
				if ( type == ContentType.PUBLISHED )
					posts.add( new PublishedPost( currPost, this ) );
				else
					posts.add( new Draft( currPost, this ) );
			}
		
		// ... //
		
		// Sort the published post according to publish date
		if ( type == ContentType.PUBLISHED )
		{
			Collections.sort( posts, new Comparator<Post>()
			{
				@Override
				public int compare( Post firstItem, Post secondItem )
				{
					PublishedPost firstPost = (PublishedPost) firstItem;
					PublishedPost secondPost = (PublishedPost) secondItem;
					
					int comparisonResult = firstPost.getPostDate().compareTo( secondPost.getPostDate() );
					
					if ( comparisonResult == 0 )
						return 0;
					else if ( comparisonResult < 0 )
						return 1;
					else
						return -1;
				}	
			});
		}
		
		// ... //
		
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
