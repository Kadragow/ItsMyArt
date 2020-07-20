package com.company.art.service;

import com.company.art.model.Post;
import com.company.art.repository.PostRepository;
import com.company.art.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Post savePost(Post post) throws Exception {
        String fileName = StringUtils.cleanPath(post.getFile().getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new Exception("Invalid path!");
            }

            Post newPost = new Post();
            newPost.setTittle(post.getTittle());
            newPost.setDescription(post.getDescription());
            newPost.setImagineName(fileName);
            newPost.setImagineType(post.getFile().getContentType());
            newPost.setData(post.getFile().getBytes());
            newPost.setPostUser(userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));

            return postRepository.save(newPost);

        }catch (IOException e){
            throw new Exception("Could not save the post!");
        }

    }
}
