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
	private Font domineFont;
	
	public Fonts()
	{
		try
		{
			domineFont = Font.createFont( Font.TRUETYPE_FONT, new File( "assets/fonts/Domine-Bold.ttf" ) );
			
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( domineFont );
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
}
