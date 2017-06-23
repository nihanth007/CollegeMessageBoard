package solutions.rollers.collegemessageboard.Models;

import android.content.Context;

/**
 * Created by nihan on 22-06-2017.
 */

public class AdminNewsItem {
    String title;
    String message;
    String date_time;
    int sent;
    int opened;
    float progress;
    String year;
    String branch;
    int msg_id;
    public Context context;


    public AdminNewsItem(String title, String message, String date_time, int sent, int opened, String year, String branch, int msg_id, Context context) {
        this.title = title;
        this.message = message;
        this.date_time = date_time;
        this.sent = sent;
        this.opened = opened;
        this.year = year;
        this.branch = branch;
        this.msg_id = msg_id;
        this.progress = (opened/(float)sent) * 100;
        this.context = context;
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

    public int getSent() {
        return sent;
    }

    public int getOpened() {
        return opened;
    }

    public String getYear() {
        return year;
    }

    public String getBranch() {
        return branch;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    public void setSent(int sent) {
        this.sent = sent;
    }

    public void setOpened(int opened) {
        this.opened = opened;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }
}

