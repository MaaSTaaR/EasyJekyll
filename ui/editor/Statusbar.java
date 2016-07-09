// [MQH] 7 July 2016
package ui.editor;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import easyjekyll.Environment;

public class Statusbar extends JPanel
{
	private static final long serialVersionUID = 6578027820467604480L;
	private JLabel statusLabel;
	private Editor editor;
	
	public enum StatusType
	{ 
		NORMAL( Color.BLACK ), SUCCESS( Color.BLUE ), ERROR( Color.RED ), NOTE( Color.RED );
		
		private Color color;
		
		StatusType( Color color )
		{
			this.color = color;
		}
		
		public Color getColor()
		{
			return this.color;
		}
	};
	
	public Statusbar( Editor editor )
	{
		this.editor = editor;
		
		this.setLayout( new BoxLayout( this, BoxLayout.LINE_AXIS ) );
		this.setBorder( BorderFactory.createEmptyBorder( 5, 10, 5, 10 ) );
		
		this.statusLabel = new JLabel( "Ready" );
		
		this.statusLabel.setFont( new Font( Environment.getInstance().getFonts().getRoboto().getName(), Font.PLAIN, 15 ) );
		
		this.add( this.statusLabel );
	}
	
	public void setStatusMessage( String message, StatusType type )
	{
		this.statusLabel.setText( message );
		this.statusLabel.setForeground( type.getColor() );
	}
}
