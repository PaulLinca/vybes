package com.vybes.repository;

import com.vybes.model.Post;
import com.vybes.model.VybesUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByPostedDateDesc(Pageable pageable);

    Page<Post> findByUserOrderByPostedDateDesc(VybesUser user, Pageable pageable);

    Page<Post> findByUserInOrderByPostedDateDesc(List<VybesUser> users, Pageable pageable);
}
