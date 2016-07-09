// [MQH] 5 July 2016
// Quick and Dirty!
package ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts
{
	// TODO: There is something called hash-table! :-/
	private Font domineFont;
	private Font alexBrushFont;
	private Font cookieFont;
	private Font fredokaOneFont;
	private Font poiretOneFont;
	private Font shareFont;
	private Font badScriptFont;
	private Font robotoFont;
	
	public Fonts()
	{
		try
		{
			this.domineFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Domine-Bold.ttf" ) );
			this.alexBrushFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Alex_Brush/AlexBrush-Regular.ttf" ) );
			this.cookieFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Cookie/Cookie-Regular.ttf" ) );
			this.fredokaOneFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Fredoka_One/FredokaOne-Regular.ttf" ) );
			this.poiretOneFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Poiret_One/PoiretOne-Regular.ttf" ) );
			this.shareFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Share/Share-Regular.ttf" ) );
			this.badScriptFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Bad_Script/BadScript-Regular.ttf" ) );
			this.robotoFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Roboto/Roboto-Regular.ttf" ) );
			
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( domineFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( alexBrushFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( cookieFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( fredokaOneFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( poiretOneFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( shareFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( badScriptFont );
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( robotoFont );
		}
		catch ( FontFormatException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	public Font getDomineBold()
	{
		return this.domineFont;
	}
	
	public Font getAlexBrush()
	{
		return this.alexBrushFont;
	}
	
	public Font getCookie()
	{
		return this.cookieFont;
	}
	
	public Font getFredokaOne()
	{
		return this.fredokaOneFont;
	}
	
	public Font getPoiretOne()
	{
		return this.poiretOneFont;
	}
	
	public Font getShare()
	{
		return this.shareFont;
	}
	
	public Font getBadScript()
	{
		return this.badScriptFont;
	}
	
	public Font getRoboto()
	{
		return this.robotoFont;
	}
}
