package com.company.art.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post_likes")
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_likes_id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_post_id")
    private Post likedPost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_that_like_id")
    private User userThatLike;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getLikedPost() {
        return likedPost;
    }

    public void setLikedPost(Post likedPost) {
        this.likedPost = likedPost;
    }

    public User getUserThatLike() {
        return userThatLike;
    }

    public void setUserThatLike(User userThatLike) {
        this.userThatLike = userThatLike;
    }
}
