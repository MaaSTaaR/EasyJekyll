import jekyll.Blog;
import jekyll.PublishedPost;

public class Main
{
	public static void main( String[] args )
	{
		Blog devBlog = new Blog( "/home/maastaar/jekyll/EasyJekyllDev" );
		
		PublishedPost firstPost = devBlog.createPost( "First Post From EasyJekyll!" );
		firstPost.save();
	}
}
