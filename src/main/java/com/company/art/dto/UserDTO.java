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

    public UserDTO(User user){
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.name = user.getName();
    }

    public String toString(){
        return this.name + " " + this.lastName;
    }
}
