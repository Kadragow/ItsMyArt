package com.company.art.dto;

import com.company.art.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String lastName;
    private String userName;

    public UserDTO(User user){
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.name = user.getName();
        this.userName = user.getUserName();
    }

    public String toString(){
        return this.name + " " + this.lastName;
    }
}
