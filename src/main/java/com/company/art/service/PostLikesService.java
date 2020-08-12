package com.company.art.service;

import com.company.art.model.PostLikes;
import com.company.art.repository.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikesService {
    @Autowired
    PostLikesRepository postLikesRepository;

    public PostLikes save(PostLikes postLikes){
        if(postLikes.getUserThatLike() == null) return null;
        return postLikesRepository.save(postLikes);

    }

    public boolean deleteUserLike(Integer postId, Integer userId){
        try {
            postLikesRepository.deleteByLikedPost_IdAndUserThatLike_id(postId, userId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Integer getPostAmountOfLikes(Integer postId){
        return postLikesRepository.findByLikedPost_Id(postId).size();
    }

    public boolean isUserLiking(Integer userId, Integer postId){
        return !postLikesRepository.findByLikedPost_IdAndUserThatLike_id(postId, userId).isEmpty();
    }
}
