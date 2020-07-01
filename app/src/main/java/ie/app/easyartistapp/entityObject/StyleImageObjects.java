package ie.app.easyartistapp.entityObject;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StyleImageObjects{

	@SerializedName("images")
	private List<ImagesItem> images;

	public void setImages(List<ImagesItem> images){
		this.images = images;
	}

	public List<ImagesItem> getImages(){
		return images;
	}
}