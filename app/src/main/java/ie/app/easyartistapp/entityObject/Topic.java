package ie.app.easyartistapp.entityObject;

public class Topic {
    private String topic_id;
    private String name_topic;
    private String topic_info;
    private String type;

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getName_topic() {
        return name_topic;
    }

    public void setName_topic(String name_topic) {
        this.name_topic = name_topic;
    }

    public String getTopic_info() {
        return topic_info;
    }

    public void setTopic_info(String topic_info) {
        this.topic_info = topic_info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void print_info(){
        System.out.println("topic ID: " + topic_id);
        System.out.println("topic name: " + name_topic);
        System.out.println("topic info: " + topic_info);
        System.out.println("type: " + type);
    }
}
