import jekyll.Blog;
import jekyll.Draft;
import jekyll.PublishedPost;

public class Main
{
	public static void main( String[] args )
	{
		Blog devBlog = new Blog( "/home/maastaar/jekyll/EasyJekyllDev" );
		
		/*
		PublishedPost firstPost = devBlog.createPost( "First Post From EasyJekyll!" );
		firstPost.addCategory( "Jekyll" );
		firstPost.addCategory( "EasyJekyll" );
		firstPost.addTag( "jekyll" );
		firstPost.addTag( "easyjekyll" );
		firstPost.setContent( "Welcome to the first EasyJekyll post! :-)" );
		firstPost.save();*/
		
		Draft firstDraft = devBlog.createDraft( "First Draft From EasyJekyll!" );
		firstDraft.addCategory( "Jekyll" );
		firstDraft.addCategory( "EasyJekyll" );
		firstDraft.addTag( "jekyll" );
		firstDraft.addTag( "easyjekyll" );
		firstDraft.setContent( "Welcome to the first EasyJekyll Draft! :-)" );
		firstDraft.save();
	}
}
