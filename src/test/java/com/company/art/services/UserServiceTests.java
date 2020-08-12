package com.company.art.services;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Role;
import com.company.art.model.User;
import com.company.art.repository.RoleRepository;
import com.company.art.repository.UserRepository;
import com.company.art.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
public class UserServiceTests {

    @TestConfiguration
    static class UserServiceTestContextConfiguration{
        @Autowired
        private RoleRepository roleRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Bean
        public UserService userService() {
            return new UserService(userRepository,roleRepository,bCryptPasswordEncoder);
        }
    }

    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void findUserByEmailTest(){
        //arrange
        Mockito.when(userRepository.findByEmail(FakeDataSet.getFakeUser().getEmail()))
                .thenReturn(FakeDataSet.getFakeUser());
        User expected = FakeDataSet.getFakeUser();
        //act
        User found = userService.findUserByEmail(expected.getEmail());
        //assert
        assertThat(found).isEqualTo(expected);
    }

    @Test
    public void findUserByUserNameTest(){
        //arrange
        Mockito.when(userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName()))
                .thenReturn(FakeDataSet.getFakeUser());
        User expected = FakeDataSet.getFakeUser();
        //act
        User found = userService.findUserByUserName(expected.getUserName());
        //assert
        assertThat(found).isEqualTo(expected);
    }

    @Test
    public void saveTest(){
        //arrange
        User expected = FakeDataSet.getFakeUser();
        Mockito.when(roleRepository.findByRole(anyString()))
                .thenReturn(new Role(2,"USER"));
        Mockito.when(userRepository.save(expected))
                .thenReturn(expected);
        //act
        User found = userService.saveUser(expected);
        //assert
        assertThat(found).isEqualTo(expected);
    }

    @Test
    public void getAllUsersTest(){
        //arrange
        List<User> expected = new ArrayList<>(Collections.singletonList(FakeDataSet.getFakeUser()));
        Mockito.when(userRepository.findAll())
                .thenReturn(expected);
        //act
        List<User> found = userService.getAllUsers();
        //assert
        assertThat(found).isEqualTo(expected);
    }

}
