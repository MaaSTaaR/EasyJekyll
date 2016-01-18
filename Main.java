import java.util.ArrayList;
import java.util.Iterator;

import jekyll.Blog;

public class Main
{
	public static void main( String[] args )
	{
		Blog devBlog = new Blog( "/home/maastaar/jekyll/EasyJekyllDev" );
		
		System.out.println( devBlog.getPostsList().get( 0 ).getCategories().get( 0 ) );
		System.out.println( devBlog.getPostsList().get( 1 ).getTitle() );
	}
}
