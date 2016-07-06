package easyjekyll;

import ui.Fonts;
import jekyll.Blog;

public class Environment
{
	private static Environment instance = null;
	private Blog currBlog;
	private Fonts fonts;
	
	private Environment()
	{
		this.currBlog = new Blog( "/home/maastaar/jekyll/mqh_EasyJekyll_Test" );
		this.fonts = new Fonts();
	}
	
	public static Environment getInstance()
	{
		if ( instance == null )
			instance = new Environment();
		
		return instance;
	}
	
	public Blog getBlog()
	{
		return this.currBlog;
	}
	
	public Fonts getFonts()
	{
		return this.fonts;
	}
}
