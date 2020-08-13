package com.company.art.services;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Role;
import com.company.art.model.User;
import com.company.art.service.MyUserDetailsService;
import com.company.art.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
public class MyUserDetailsServiceTests {
    @TestConfiguration
    static class UserServiceTestContextConfiguration{
        @Bean
        public MyUserDetailsService myUserDetailsService() {
            return new MyUserDetailsService();
        }
    }

    @MockBean
    private UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Test
    public void loadUserByUserNameTest(){
        //arrange
        User testUser = FakeDataSet.getFakeUser();
        testUser.setRoles(new HashSet<Role>(Collections.singletonList(new Role(1, "USER"))));
        Mockito.when(userService.findUserByUserName(anyString()))
                .thenReturn(testUser);
        //act
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(testUser.getUserName());
        //assert
        assertThat(userDetails).isNotNull();
    }

}
