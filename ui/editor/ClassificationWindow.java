package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
import javax.swing.table.DefaultTableModel;

import jekyll.Post;

abstract class ClassificationWindow
{
	private JFrame mainWin;
	private JPanel mainPane;
	
	protected Post currPost;
	private JTable dataTable;
	private JButton removeBtn;
	
	private String data[][];
	private String windowTitle;
	private String columnName;
	
	public ClassificationWindow( Post currPost, String windowTitle )
	{
		this.currPost = currPost;
		this.windowTitle = windowTitle;
		
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
		
		this.mainWin.setTitle( this.windowTitle );
		
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
					addData( catField.getText() );
					
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
	
	protected void setColumnName( String columnName )
	{
		this.columnName = columnName;
	}
	
	private void loadData()
	{
		initData();
		
		this.dataTable.setModel( new DefaultTableModel( this.data, new String[] { this.columnName } ) );
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
				removeData( data[ dataTable.getSelectedRow() ][ 0 ] );
				loadData();
				currPost.save();
			}
		});
		
		this.mainPane.add( removeBtn, BorderLayout.PAGE_END );
	}
	
	abstract void addData( String currData );
	abstract void removeData( String currData );
	abstract protected void initData();
}
