package com.company.art.repository;

import com.company.art.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Post findById(Integer id);
    List<Post> findByPostUser_Id(Integer id);
}
