package com.vybes.service;

import java.time.ZonedDateTime;
import java.util.List;
import com.vybes.service.user.model.VybesUser;
import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;

public interface Post {
    Long getId();

    ZonedDateTime getPostedDate();

    VybesUser getUser();

    List<Like> getLikes();

    List<Comment> getComments();

    String getImageUrl();

    String getDescription();
}
