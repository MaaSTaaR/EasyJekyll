// [MQH] 17 September 2016
package easyjekyll.frontmatter;

public class CustomFrontMatter
{
	private String key, value;
	
	public CustomFrontMatter( String key, String value )
	{
		this.key = key.trim();
		this.value = value.trim();
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public String getValue()
	{
		return this.value;
	}
}
