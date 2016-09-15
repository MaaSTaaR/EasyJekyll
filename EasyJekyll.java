import easyjekyll.Environment;
import ui.MainWindow;

public class EasyJekyll
{
	public static void main( String[] args )
	{
		new MainWindow();
		
		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() 
		{
			@Override
			public void run()
			{
				Environment.getInstance().getJekyll().stopServer();
			}
		} ) );
	}
}
