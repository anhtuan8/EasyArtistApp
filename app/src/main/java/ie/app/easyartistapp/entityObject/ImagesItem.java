package ie.app.easyartistapp.entityObject;

import com.google.gson.annotations.SerializedName;

public class ImagesItem{

	@SerializedName("author")
	private String author;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("image_url")
	private String image_url;

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}