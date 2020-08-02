package com.company.art.repository;

import com.company.art.model.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    PostLikes findById(Integer id);
    List<PostLikes> findByLikedPost_Id(Integer id);
    List<PostLikes> findByUserThatLike_Id(Integer id);
}
