package com.company.art.repository;

import com.company.art.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends  JpaRepository<Post, Integer> {
    //Post findById(Integer id);
    List<Post> findByPostUser_Id(Integer id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update post p set p.tittle=:editedTitle where p.post_id=:postId", nativeQuery=true)
    void updatePostTitle(@Param("postId") Integer postId,@Param("editedTitle") String editedTitle);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update post p set p.data=:file where p.post_id=:postId", nativeQuery=true)
    void updatePostImg(@Param("postId") Integer postId,@Param("file") byte[] file);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update post p set p.description=:editedDescription where p.post_id=:postId", nativeQuery=true)
    void updatePostDescription(@Param("postId") Integer postId,@Param("editedDescription") String editedDescription);
}
