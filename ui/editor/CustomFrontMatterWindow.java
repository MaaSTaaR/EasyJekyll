package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import easyjekyll.frontmatter.CustomFrontMatter;
import easyjekyll.frontmatter.CustomFrontMatters;
import jekyll.Post;

class CustomFrontMatterWindow
{
	private JFrame mainWin;
	private JPanel mainPane;
	
	private Post currPost;
	private Editor editor;
	private JTable dataTable;
	private JButton removeBtn;
	
	private String data[][];
	
	public CustomFrontMatterWindow( Editor editor )
	{
		this.editor = editor;
		this.currPost = this.editor.getPost();
		
		this.mainWin = new JFrame();
		
		this.setNativeLookAndFeel();
		this.createWindow();
	}
	
	private void setNativeLookAndFeel()
	{
		try 
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch ( ClassNotFoundException e ) {} 
		catch ( InstantiationException e ) {} 
		catch ( IllegalAccessException e ) {} 
		catch ( UnsupportedLookAndFeelException e ) {}
	}
	
	private void createWindow()
	{
		this.mainPane = new JPanel( new BorderLayout() );
		
		// ... //
		
		this.mainWin.setSize( 200, 400 );
		this.mainWin.setContentPane( this.mainPane );
		
		this.mainWin.addWindowListener( new WindowListener() 
		{
			@Override
			public void windowClosing( WindowEvent e )
			{
				editor.getOperations().save();
			}
			
			@Override
			public void windowActivated( WindowEvent e ) { }

			@Override
			public void windowClosed( WindowEvent e ) { }

			@Override
			public void windowDeactivated( WindowEvent e ) { }

			@Override
			public void windowDeiconified( WindowEvent e ) { }

			@Override
			public void windowIconified( WindowEvent e ) { }

			@Override
			public void windowOpened( WindowEvent e ) { }
		});
		
		this.mainWin.setTitle( "Custom Frontmatter" );
		
		// ... //
		
		this.createWidgets();
		
		// ... //
		
		this.mainWin.setVisible( true );
	}
	
	private void createWidgets()
	{
		this.createAddPane();
		this.createTable();
		this.createRemovePane();
	}
	
	private void createAddPane()
	{
		JPanel addPane = new JPanel();
		
		final JTextField catField = new JTextField( 10 );
		final JButton addBtn = new JButton( "Add" );
		
		// ... //
		
		addBtn.setEnabled( false );
		
		// ... //
		
		catField.getDocument().addDocumentListener( new DocumentListener() 
		{
			@Override
			public void changedUpdate( DocumentEvent e ) { }

			@Override
			public void insertUpdate( DocumentEvent e )
			{
				this.enableAddBtn( e );
			}

			@Override
			public void removeUpdate( DocumentEvent e )
			{
				this.enableAddBtn( e );
			}
			
			private void enableAddBtn( DocumentEvent e )
			{
				addBtn.setEnabled( e.getDocument().getLength() > 0 );
			}
			
		});
		
		// ... //
		
		addBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				if ( !catField.getText().isEmpty() )
				{
					//addData( catField.getText() );
					
					loadData();
					
					catField.setText( null );
					
					currPost.save();
				}
			}
		});
		
		// ... //
		
		addPane.add( catField );
		addPane.add( addBtn );
		
		this.mainPane.add( addPane, BorderLayout.PAGE_START );
	}
	
	private void createTable()
	{
		this.dataTable = new JTable();
		
		this.dataTable.getSelectionModel().addListSelectionListener( new ListSelectionListener() 
		{
			@Override
			public void valueChanged( ListSelectionEvent e )
			{
				removeBtn.setEnabled( true );
			}
		});
		
		this.loadData();
		
		JScrollPane scrolledTable = new JScrollPane( dataTable );
		
		this.mainPane.add( scrolledTable, BorderLayout.CENTER );
	}
	
	protected void setData( String[][] data )
	{
		this.data = data;
	}
	
	/*protected void setColumnName( String columnName )
	{
		this.columnName = columnName;
	}*/
	
	private void loadData()
	{
		CustomFrontMatters customFrontMatters = new CustomFrontMatters();
		
		this.data = new String[ customFrontMatters.getFrontMatters().size() ][ 2 ];
		
		int s = 0;
		
		for ( CustomFrontMatter currFrontMatter: customFrontMatters.getFrontMatters() )
		{
			this.data[ s ][ 0 ] = currFrontMatter.getKey();
			this.data[ s ][ 1 ] = ( currPost.getFrontMatter().get( currFrontMatter.getKey() ) != null ) ? currPost.getFrontMatter().get( currFrontMatter.getKey() ) : currFrontMatter.getValue();
			
			s++;
		}
		
		this.dataTable.setModel( new DefaultTableModel( this.data, new String[] { "Frontmatter", "Value" } ) );
		
		this.dataTable.getModel().addTableModelListener( new TableModelListener()
		{
			@Override
			public void tableChanged( TableModelEvent e )
			{
				if ( e.getColumn() == 1 )
				{
					String changedFrontMatterKey = data[ e.getFirstRow() ][ 0 ];
					String changedFrontMatterValue = (String) dataTable.getModel().getValueAt( e.getFirstRow(), 1 ) ;
					
					if ( currPost.getFrontMatter().containsKey( changedFrontMatterKey ) )
						currPost.getFrontMatter().remove( changedFrontMatterKey );
					
					currPost.getFrontMatter().put( changedFrontMatterKey, changedFrontMatterValue );
					
					currPost.save();
				}
			}	
		});
	}
	
	private void createRemovePane()
	{
		this.removeBtn = new JButton( "Remove" );
		
		this.removeBtn.setEnabled( false );
		
		this.removeBtn.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				//removeData( data[ dataTable.getSelectedRow() ][ 0 ] );
				loadData();
				currPost.save();
			}
		});
		
		this.mainPane.add( removeBtn, BorderLayout.PAGE_END );
	}
	
	/*abstract void addData( String currData );
	abstract void removeData( String currData );
	abstract protected void initData();*/
}
