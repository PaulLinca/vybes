package com.vybes.service.writer.repository;

import com.vybes.service.writer.entity.Post;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate template;

    public void save(Post post) {
        String sql = "insert into post (id, songName) values (?, ?)";

        template.update(sql, post.getId(), post.getSongName());
    }

    public List<Post> getAllPosts() {
        String sql = "select * from post";

        RowMapper<Post> mapper =
                (rs, rowNum) -> Post.builder().id(rs.getInt(1)).songName(rs.getString(2)).build();

        return template.query(sql, mapper);
    }
}
