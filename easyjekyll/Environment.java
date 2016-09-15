package easyjekyll;

import java.util.prefs.Preferences;

import ui.Fonts;
import jekyll.Blog;
import jekyll.Jekyll;

public class Environment
{
	private static Environment instance = null;
	private Blog currBlog;
	private Fonts fonts;
	private Preferences prefs;
	private Jekyll jekyll;
	
	private Environment()
	{
		this.currBlog = new Blog( "/home/maastaar/jekyll/mqh_EasyJekyll_Test" );
		this.fonts = new Fonts();
		this.prefs = Preferences.userRoot().node( "net.maastaar.easyjekyll" );
		this.jekyll = new Jekyll( this.prefs.get( "jekyll_command", "jekyll" ), this.currBlog.getPath() );
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
	
	public Jekyll getJekyll()
	{
		return this.jekyll;
	}
}
