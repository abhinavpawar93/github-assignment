
package com.abhinav.demo.data.remote.model;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity(tableName = "issue")
@Getter
@Setter
@Data
public class Issue {

    public Issue(Integer id, Integer number, String state, String title, String body, User user, Assignee assignee, Milestone milestone, Boolean locked, String activeLockReason, Integer comments, String closedAt, String createdAt, String updatedAt) {
        this.id = id;
        this.number = number;
        this.state = state;
        this.title = title;
        this.body = body;
        this.user = user;
        this.assignee = assignee;
        this.milestone = milestone;
        this.locked = locked;
        this.activeLockReason = activeLockReason;
        this.comments = comments;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @PrimaryKey
    public Integer id;
    public Integer number;
    public String state;
    public String title;
    public String body;
    @Embedded(prefix = "user")
    public User user;
    @Embedded(prefix = "assignee")
    public Assignee assignee;

    @Embedded(prefix = "milestone")
    public Milestone milestone;
    public Boolean locked;
    public String activeLockReason;
    public Integer comments;
    public String closedAt;
    public String createdAt;
    public String updatedAt;

    public static class Milestone {
        public Milestone(String url, String htmlUrl, String labelsUrl, Integer id, String nodeId, Integer number, String state, String title, String description, Creator creator, Integer openIssues, Integer closedIssues, String createdAt, String updatedAt, String closedAt, String dueOn) {
            this.url = url;
            this.htmlUrl = htmlUrl;
            this.labelsUrl = labelsUrl;
            this.id = id;
            this.nodeId = nodeId;
            this.number = number;
            this.state = state;
            this.title = title;
            this.description = description;
            this.creator = creator;
            this.openIssues = openIssues;
            this.closedIssues = closedIssues;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.closedAt = closedAt;
            this.dueOn = dueOn;
        }

        public String url;
        public String htmlUrl;
        public String labelsUrl;
        public Integer id;
        public String nodeId;
        public Integer number;
        public String state;
        public String title;
        public String description;
        @Embedded(prefix = "creator")
        public Creator creator;
        public Integer openIssues;
        public Integer closedIssues;
        public String createdAt;
        public String updatedAt;
        public String closedAt;
        public String dueOn;

    }

    public static class Creator {
        public Creator(String login, Integer id, String nodeId, String avatarUrl, String gravatarId, String url, String htmlUrl, String followersUrl, String followingUrl, String gistsUrl, String starredUrl, String subscriptionsUrl, String organizationsUrl, String reposUrl, String eventsUrl, String receivedEventsUrl, String type, Boolean siteAdmin) {
            this.login = login;
            this.id = id;
            this.nodeId = nodeId;
            this.avatarUrl = avatarUrl;
            this.gravatarId = gravatarId;
            this.url = url;
            this.htmlUrl = htmlUrl;
            this.followersUrl = followersUrl;
            this.followingUrl = followingUrl;
            this.gistsUrl = gistsUrl;
            this.starredUrl = starredUrl;
            this.subscriptionsUrl = subscriptionsUrl;
            this.organizationsUrl = organizationsUrl;
            this.reposUrl = reposUrl;
            this.eventsUrl = eventsUrl;
            this.receivedEventsUrl = receivedEventsUrl;
            this.type = type;
            this.siteAdmin = siteAdmin;
        }



        public String login;
        public Integer id;
        public String nodeId;
        public String avatarUrl;
        public String gravatarId;
        public String url;
        public String htmlUrl;
        public String followersUrl;
        public String followingUrl;
        public String gistsUrl;
        public String starredUrl;
        public String subscriptionsUrl;
        public String organizationsUrl;
        public String reposUrl;
        public String eventsUrl;
        public String receivedEventsUrl;
        public String type;
        public Boolean siteAdmin;

    }

    public static class Assignee {
        public Assignee(String login, Integer id, String nodeId, String avatarUrl, String gravatarId, String url, String htmlUrl, String followersUrl, String followingUrl, String gistsUrl, String starredUrl, String subscriptionsUrl, String organizationsUrl, String reposUrl, String eventsUrl, String receivedEventsUrl, String type, Boolean siteAdmin) {
            this.login = login;
            this.id = id;
            this.nodeId = nodeId;
            this.avatarUrl = avatarUrl;
            this.gravatarId = gravatarId;
            this.url = url;
            this.htmlUrl = htmlUrl;
            this.followersUrl = followersUrl;
            this.followingUrl = followingUrl;
            this.gistsUrl = gistsUrl;
            this.starredUrl = starredUrl;
            this.subscriptionsUrl = subscriptionsUrl;
            this.organizationsUrl = organizationsUrl;
            this.reposUrl = reposUrl;
            this.eventsUrl = eventsUrl;
            this.receivedEventsUrl = receivedEventsUrl;
            this.type = type;
            this.siteAdmin = siteAdmin;
        }


        public String login;
        public Integer id;
        public String nodeId;
        public String avatarUrl;
        public String gravatarId;
        public String url;
        public String htmlUrl;
        public String followersUrl;
        public String followingUrl;
        public String gistsUrl;
        public String starredUrl;
        public String subscriptionsUrl;
        public String organizationsUrl;
        public String reposUrl;
        public String eventsUrl;
        public String receivedEventsUrl;
        public String type;
        public Boolean siteAdmin;

    }
}
