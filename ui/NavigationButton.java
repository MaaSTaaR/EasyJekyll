package ui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import easyjekyll.Environment;

public class NavigationButton extends JButton
{
	private static final long serialVersionUID = 5622747821601576924L;
	
	public NavigationButton( String label )
	{
		super( label );
		
		this.setBorder( BorderFactory.createEmptyBorder( 2, 170, 2, 170 ) );
		this.setFont( new Font( Environment.getInstance().getFonts().getBadScript().getName(), Font.PLAIN, 30 ));
	}
}
