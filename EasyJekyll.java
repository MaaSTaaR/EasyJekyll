import java.util.ArrayList;

import easyjekyll.Environment;
import ui.MainWindow;
import jekyll.Blog;
import jekyll.Draft;
import jekyll.PublishedPost;

public class EasyJekyll
{
	public static void main( String[] args )
	{
		new MainWindow();
		
		/*ArrayList<PublishedPost> posts = devBlog.getPostsList();
		
		for ( int s = 0; s < posts.size(); s++ )
		{
			PublishedPost currPost = posts.get( s );
			
			System.out.println( currPost.getTitle() );
			System.out.println( currPost.getDate() );
			System.out.println( currPost.getPostDate() );
			System.out.println( currPost.getTime() );
			System.out.println( "--------" );
		}*/
		
		/*
		PublishedPost firstPost = devBlog.createPost( "First Post From EasyJekyll!" );
		firstPost.addCategory( "Jekyll" );
		firstPost.addCategory( "EasyJekyll" );
		firstPost.addTag( "jekyll" );
		firstPost.addTag( "easyjekyll" );
		firstPost.setContent( "Welcome to the first EasyJekyll post! :-)" );
		firstPost.save();*/
		
		/*Draft firstDraft = devBlog.createDraft( "First Draft From EasyJekyll!" );
		firstDraft.addCategory( "Jekyll" );
		firstDraft.addCategory( "EasyJekyll" );
		firstDraft.addTag( "jekyll" );
		firstDraft.addTag( "easyjekyll" );
		firstDraft.setContent( "Welcome to the first EasyJekyll Draft! :-)" );
		firstDraft.save();*/
		
		/*PublishedPost toBeModifiedPost = devBlog.getPostsList().get( 2 );
		toBeModifiedPost.setContent( toBeModifiedPost.getContent() + "\nAdded From EasyJekyll." );
		toBeModifiedPost.save();*/
		
		/*Draft toBeModifiedDraft = devBlog.getDraftsList().get( 1 );
		toBeModifiedDraft.setContent( toBeModifiedDraft.getContent() + "\nAdded From EasyJekyll." );
		toBeModifiedDraft.save();*/
		
		/*PublishedPost secondPost = devBlog.createPost( "Second Post From EasyJekyll!" );
		secondPost.addCategory( "Jekyll" );
		secondPost.addCategory( "EasyJekyll" );
		secondPost.addTag( "jekyll" );
		secondPost.addTag( "easyjekyll" );
		secondPost.setContent( "Welcome to the second EasyJekyll post! :-)" );
		secondPost.save();*/
	}
}
