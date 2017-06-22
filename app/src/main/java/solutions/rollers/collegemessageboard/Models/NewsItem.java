package solutions.rollers.collegemessageboard.Models;

/**
 * Created by nihan on 22-06-2017.
 */

public class NewsItem {
    String title;
    String message;
    String date_time;
    int msg_id;

    public NewsItem(String title, String message, String date_time, int msg_id) {
        this.title = title;
        this.message = message;
        this.date_time = date_time;
        this.msg_id = msg_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {

        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate_time() {
        return date_time;
    }

    public int getMsg_id() {
        return msg_id;
    }
}
