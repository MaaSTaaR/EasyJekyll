package ui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import easyjekyll.Environment;

public class ActionButton extends JButton
{
	private static final long serialVersionUID = 2407469976339587896L;

	public ActionButton( String label )
	{
		super( label );
		
		this.setFont( new Font( Environment.getInstance().getFonts().getFredokaOne().getName(), Font.PLAIN, 15 ) );
		this.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
	}
}
