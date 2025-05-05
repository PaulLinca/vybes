package com.vybes.model;

import java.time.ZonedDateTime;
import java.util.List;

public interface Post {
    Long getId();

    ZonedDateTime getPostedDate();

    VybesUser getUser();

    List<Like> getLikes();

    List<Comment> getComments();

    String getImageUrl();

    String getDescription();
}
