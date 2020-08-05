package com.company.art.controller;

import com.company.art.dto.PostDTO;
import com.company.art.model.Post;
import com.company.art.model.PostLikes;
import com.company.art.model.User;
import com.company.art.service.PostLikesService;
import com.company.art.service.PostService;
import com.company.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    PostLikesService postLikesService;

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = -1;
        if(auth!=null){
            User user = userService.findUserByUserName(auth.getName());
            if(user!=null)
                userId=user.getId();
        }
        List<PostDTO> postDTOList = postService.getAllEncodedPostsDTO();
        Collections.reverse(postDTOList);
        modelAndView.addObject("posts",postDTOList);
        modelAndView.addObject("postLikesService",postLikesService);
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/guestView", method = RequestMethod.GET)
    public ModelAndView guestView(@RequestParam("username") String username){
        User user = userService.findUserByUserName(username);
        List<PostDTO> postToDisplay = postService.getUserAllEncodedPostsDTO(user);
        Collections.reverse(postToDisplay);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = -1;
        if(auth!=null){
            User logged = userService.findUserByUserName(auth.getName());
            if(logged!=null)
                userId=logged.getId();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("posts", postToDisplay);
        modelAndView.addObject("postLikesService",postLikesService);
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("guestView");

        return modelAndView;
    }

    @RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
    public void showImage(@RequestParam("id") Integer postId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Post post = postService.findPostById(postId);
        System.out.println(post.getAuthor().getUserName());
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(post.getData());
        response.getOutputStream().close();
    }

    @PostMapping(value = "/likePost")
    public String likeThisPost(@RequestParam("id") Integer postId, HttpServletRequest request){
        String re = "redirect:" + request.getHeader("Referer");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Post post = postService.findPostById(postId);
        if (postLikesService.isUserLiking(user.getId(),postId))
            return re;
        PostLikes postLikes = new PostLikes();
        postLikes.setUserThatLike(user);
        postLikes.setLikedPost(post);
        postLikesService.save(postLikes);
        return re;
    }

    @PostMapping(value = "/unlikePost")
    public String unlikeThisPost(@RequestParam("postId") Integer postId, @RequestParam("userId") Integer userId, HttpServletRequest request){
        postLikesService.deleteUserLike(postId,userId);
        return "redirect:" + request.getHeader("Referer");
    }
}
