package com.company.art.dto;

import com.company.art.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private UserDTO userDTO;
    private Calendar calendar;
    private String description;
    private String tittle;
    private String convertedImg;

    public PostDTO(Post post){
        this.id = post.getId();
        this.userDTO = new UserDTO();
        this.calendar = post.getDate();
        this.description = post.getDescription();
        this.tittle = post.getTittle();
    }

    public PostDTO(Post post, String convertedImg){
        this.id = post.getId();
        this.userDTO = new UserDTO(post.getAuthor());
        this.calendar = post.getDate();
        this.description = post.getDescription();
        this.tittle = post.getTittle();
        this.convertedImg = convertedImg;
    }
}
