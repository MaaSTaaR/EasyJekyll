package ui.editor;

import jekyll.Post;

public class Operations
{
	private Editor editor;
	private Post currPost;
	
	public Operations( Editor editor )
	{
		this.editor = editor;
		this.currPost = this.editor.getPost();
	}
	
	public void save()
	{
		this.currPost.setTitle( this.editor.getTitle() );
		this.currPost.setContent( this.editor.getContent() );
		
		this.editor.saveNotification( this.currPost.save() );
	}
}
