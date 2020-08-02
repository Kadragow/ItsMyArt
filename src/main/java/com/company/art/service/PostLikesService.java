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
        postLikesRepository.save(postLikes);
        return postLikes;

    }

    public Integer getPostAmountOfLikes(Integer postId){
        return postLikesRepository.findByLikedPost_Id(postId).size();
    }

}
