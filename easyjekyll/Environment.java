package easyjekyll;

import java.util.prefs.Preferences;

import ui.Fonts;
import jekyll.Blog;

public class Environment
{
	private static Environment instance = null;
	private Blog currBlog;
	private Fonts fonts;
	private Preferences prefs;
	
	private Environment()
	{
		this.currBlog = new Blog( "/home/maastaar/jekyll/mqh_EasyJekyll_Test" );
		this.fonts = new Fonts();
		this.prefs = Preferences.userRoot().node( "net.maastaar.easyjekyll" );
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
	
	public Preferences getPreferences()
	{
		return this.prefs;
	}
}
