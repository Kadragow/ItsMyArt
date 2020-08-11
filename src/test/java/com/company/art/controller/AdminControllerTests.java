package com.company.art.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.company.art.FakeDataSet;
import com.company.art.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
public class AdminControllerTests {
    @Autowired
    private UserService userService;
//    @Autowired
//    private MockMvc mockMvc;
    @Test
    public void contextLoads(){
        assertThat(userService).isNotNull();
    }

    @Test
    public void homeTest(){
        //TODO
//        userService = mock(UserService.class);

//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/home");
//        when(userService.findUserByUserName(anyString())).thenReturn(FakeDataSet.getFakeUser());

    }
}
