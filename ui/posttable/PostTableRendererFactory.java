// [MQH] 6 July 2016
package ui.posttable;

import jekyll.Blog.ContentType;

public class PostTableRendererFactory
{
	public static PostTableRenderer getRenderer( ContentType contentType )
	{
		if ( contentType == ContentType.PUBLISHED )
			return new PublishedPostTableRenderer();
		else if ( contentType == ContentType.DRAFT )
			return new DraftPostTableRenderer();
		else
			return null;
	}
}
