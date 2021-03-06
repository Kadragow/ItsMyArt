package com.company.art.service;

import com.company.art.dto.PostDTO;
import com.company.art.model.Post;
import com.company.art.model.User;
import com.company.art.repository.PostRepository;
import com.company.art.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    public Post findPostById(Integer id){
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<PostDTO> getAllEncodedPostsDTO(){
        List<PostDTO> dtos = new ArrayList<>();
        for(Post post : getAllPosts()){
            dtos.add(new PostDTO(post, Base64.getEncoder().encodeToString(post.getData())));
        }
        return dtos;
    }

    public List<Post> getUserAllPosts(User user){
        return postRepository.findByPostUser_Id(user.getId());
    }

    public List<PostDTO> getUserAllEncodedPostsDTO(User user){

        List<PostDTO> dtos = new ArrayList<>();
        for(Post post : getUserAllPosts(user)){
            dtos.add(new PostDTO(post, Base64.getEncoder().encodeToString(post.getData())));
        }
        return dtos;
    }

    public boolean deletePostById(Integer id){
        try{
            postRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public boolean updatePostTitle(Integer postId, String editedTitle) {
        try {
            postRepository.updatePostTitle(postId, editedTitle);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean updatePostDescription(Integer postId, String postDescription) {
        try {
            postRepository.updatePostDescription(postId, postDescription);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean updatePostImg(Integer postId, Post editedPost) {
        try {
            postRepository.updatePostImg(postId, editedPost.getFile().getBytes());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
