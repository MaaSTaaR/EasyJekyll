//[MQH] 6 July 2016
package ui;

import java.awt.Component;
import javax.swing.JTable;

public class DraftPostTableRenderer extends PostTableRenderer
{
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
	{
		return super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
	}
}
