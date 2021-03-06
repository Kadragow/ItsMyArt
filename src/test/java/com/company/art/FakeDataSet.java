package com.company.art;

import com.company.art.model.Post;
import com.company.art.model.User;

import java.util.Calendar;

public class FakeDataSet {
    public static User getFakeUser(){
        User user = new User();
//        user.setId(11);
        user.setActive(true);
        user.setUserName("TheTest");
        user.setPassword("Password");
        user.setEmail("test@test.com");
        user.setName("Test");
        user.setLastName("Testing");
        return user;
    }

    public static Post getFakePost(){
        Post post = new Post();
        post.setTittle("Title");
        post.setId(1);
        post.setImagineType("image/png");
        post.setData(new byte[5]);
        post.setImagineName("Image");
        post.setDate(Calendar.getInstance());
        post.setDescription("Description");

        return post;
    }
}
