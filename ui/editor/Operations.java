package ui.editor;

import jekyll.Draft;
import jekyll.Post;
import jekyll.Post.PostType;

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
	
	// Change the post from draft to published post
	public void publish()
	{
		if ( this.currPost.getType() != PostType.DRAFT )
			return;
		
		this.save();
		
		Draft currDraft = (Draft) this.currPost;
		
		this.editor.publishNotification( currDraft.publish() );
	}
}
