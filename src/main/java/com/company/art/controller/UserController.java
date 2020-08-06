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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<PostDTO> postsToDisplay = postService.getUserAllEncodedPostsDTO(user);
        Collections.reverse(postsToDisplay);
        modelAndView.addObject("post", post);
        modelAndView.addObject("userMessage","Welcome " + user.getUserName() + "!");
        modelAndView.addObject("userNumberOfPosts", "Times you post something: " + user.getPosts().size());
        modelAndView.addObject("posts", postsToDisplay);
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
}
