package ie.app.easyartistapp.entityObject;

import androidx.annotation.Nullable;

public class Article {
    private String article_id;
    private String description;
    private String detail;
    private String image_link;
    private boolean isFavorite;

    public Article(String article_id, String description, String detail, String image_link, String name, String topic_id, String topic_name, boolean isFavorite){
        this.article_id = article_id;
        this.description = description;
        this.detail = detail;
        this.image_link = image_link;
        this.name = name;
        this.topic_id = topic_id;
        this.topic_name = topic_name;
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Article aObj = (Article)obj;
        return this.article_id.equalsIgnoreCase(aObj.article_id);
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = standardizedString(name);
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private String name;
    private String topic_id;
    private String topic_name;

    public Article(){
    }

    public void print_info(){
        System.out.println("ID: " + article_id);
        System.out.println("Description: " + description);
        System.out.println("Detail: " + detail);
        System.out.println("Image Link: " + image_link);
        System.out.println("Name: " + name);
        System.out.println("Topic id: " + topic_id);
        System.out.println("Topic name: " + topic_name);
    }

    private String standardizedString(String s){
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(s.charAt(0)));
        for(int i=1; i<s.length();i++){
            if(s.charAt(i) == '-'){
                result.append(' ');
            }
            else result.append(s.charAt(i));
        }
        return result.toString();
    }
}
