
package com.abhinav.demo.data.remote.model;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "comment")
@Getter
@Setter
@Data
public class Comment {

    public Comment(Integer id, String nodeId, String url, String htmlUrl, String body, User user, String createdAt, String updatedAt) {
        this.id = id;
        this.nodeId = nodeId;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.body = body;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrimaryKey
    public Integer id;
    public String nodeId;
    public String url;
    public String htmlUrl;
    public String body;
    @Embedded(prefix = "user")
    public User user;
    public String createdAt;
    public String updatedAt;

}
