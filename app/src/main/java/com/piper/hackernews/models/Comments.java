package com.piper.hackernews.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by parthamurmu on 10/09/17.
 */

public class Comments extends RealmObject {

    @Required
    private String storyId;

    @Required
    private String commentId;

    @Required
    private String comment;

    @Required
    private String time;

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}