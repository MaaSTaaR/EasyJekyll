package ui.editor;

import java.util.HashMap;

import easyjekyll.frontmatter.CustomFrontMatter;
import easyjekyll.frontmatter.CustomFrontMatters;
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
		
		// ... //
		
		CustomFrontMatters customFrontMatters = new CustomFrontMatters();
		HashMap<String, String> postFrontMatters = this.currPost.getFrontMatter();
		
		for ( CustomFrontMatter currFrontMatter: customFrontMatters.getFrontMatters() )
			if ( !postFrontMatters.containsKey( currFrontMatter.getKey() ) )
				postFrontMatters.put( currFrontMatter.getKey(), currFrontMatter.getValue() );
		
		// ... //
		
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
