package com.company.art.controller;

import com.company.art.dto.PostDTO;
import com.company.art.model.Post;
import com.company.art.model.User;
import com.company.art.service.PostService;
import com.company.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();

        List<PostDTO> postDTOList = postService.getAllEncodedPostsDTO();
        Collections.reverse(postDTOList);
        modelAndView.addObject("posts",postDTOList);

        modelAndView.setViewName("index");
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
}
