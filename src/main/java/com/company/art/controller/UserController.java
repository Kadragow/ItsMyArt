package com.company.art.controller;

import com.company.art.dto.PostDTO;
import com.company.art.model.Post;
import com.company.art.model.User;
import com.company.art.service.PostService;
import com.company.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Post post = new Post();
        String editedTittle = "";
        List<PostDTO> postsToDisplay = postService.getUserAllEncodedPostsDTO(user);
        Collections.reverse(postsToDisplay);
        modelAndView.addObject("post", post);
        modelAndView.addObject("userMessage","Welcome " + user.getUserName() + "!");
        modelAndView.addObject("userNumberOfPosts", "Times you posted your art: " + user.getPosts().size());
        modelAndView.addObject("posts", postsToDisplay);
        modelAndView.addObject("editedTittle",editedTittle);
        modelAndView.addObject("editedFile");
        modelAndView.addObject("editedDescription","");

        modelAndView.setViewName("user/home");
        return modelAndView;
    }

//    @RequestMapping(value = "/home", method = RequestMethod.POST)
//    public ModelAndView addingPost(){
//        ModelAndView modelAndView = new ModelAndView();
//        Post post = new Post();
//        modelAndView.addObject("post", post);
//        modelAndView.setViewName("/home/addingPost");
//        return modelAndView;
//    }

    @PostMapping(value="/home/addPost")
    public String addPost(Post post, RedirectAttributes redirectAttributes) throws Exception {
        postService.savePost(post);

        return "redirect:/user/home";
    }

    @RequestMapping(value="/delete")
    public String deletePost(@RequestParam("id") Integer postId) {
        postService.deletePostById(postId);
        return "redirect:/user/home";
    }

    @PostMapping(value="/editTitle")
    public String editTitle(Post post, @RequestParam("id") Integer postId) throws Exception {
        postService.updatePostTitle(postId, post.getTittle());
        return "redirect:/user/home";
    }

    @PostMapping(value="/editImg")
    public String editImg(Post post, @RequestParam("id") Integer postId) {
        postService.updatePostImg(postId, post);
        return "redirect:/user/home";
    }

    @PostMapping(value="/editDescription")
    public String editDescription(Post post, @RequestParam("id") Integer postId) {
        postService.updatePostDescription(postId, post.getDescription());
        return "redirect:/user/home";
    }
}
