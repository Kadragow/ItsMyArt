package com.company.art.services;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Post;
import com.company.art.model.PostLikes;
import com.company.art.repository.PostLikesRepository;
import com.company.art.service.PostLikesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
public class PostLikesServiceTests {

    @TestConfiguration
    public class PostLikesTestContextConfiguration{
        @Bean
        public PostLikesService postLikesService(){
            return new PostLikesService();
        }
    }

    @Autowired
    private PostLikesService postLikesService;

    @MockBean
    private PostLikesRepository postLikesRepository;

    @Test
    public void save_Fail(){
        //arrange
        PostLikes postLikes = new PostLikes();
        //act
        PostLikes saved = postLikesService.save(postLikes);
        //assert
        assertThat(saved).isNull();
    }

    @Test
    public void save_Success(){
        //arrange
        PostLikes postLikes = new PostLikes();
        postLikes.setUserThatLike(FakeDataSet.getFakeUser());
        postLikes.setLikedPost(FakeDataSet.getFakePost());
        Mockito.when(postLikesRepository.save(anyObject()))
                .thenReturn(postLikes);
        //act
        PostLikes saved = postLikesService.save(postLikes);
        //assert
        assertThat(saved).isEqualTo(postLikes);
    }

    @Test
    public void deleteUserLike_Success(){
        //arrange
        long returned = 123L;
        Integer testId1 = 555, testId2 = 444;
        Mockito.when(postLikesRepository.deleteByLikedPost_IdAndUserThatLike_id(anyInt(), anyInt()))
                .thenReturn(returned);
        //act
        boolean success = postLikesService.deleteUserLike(testId1, testId2);
        //assert
        assertThat(success).isTrue();
    }


    @Test
    public void deleteUserLike_Fail(){
        //arrange
        long returned = 123L;
        Integer testId1 = 555, testId2 = 444;
        doThrow(EntityNotFoundException.class)
                .when(postLikesRepository).deleteByLikedPost_IdAndUserThatLike_id(anyInt(), anyInt());
        //act
        boolean success = postLikesService.deleteUserLike(testId1, testId2);
        //assert
        assertThat(success).isFalse();
    }

    @Test
    public void getPostAmountOfLikesTest(){
        //arrange
        Integer testId1 = 555, expectedSize = 1;
        List<PostLikes> postList = new ArrayList<>(Collections.singletonList(new PostLikes()));
        Mockito.when(postLikesRepository
                .findByLikedPost_Id(anyInt()))
                .thenReturn(postList);
        //act
        Integer isUserLiking = postLikesService.getPostAmountOfLikes(testId1);
        //assert
        assertThat(isUserLiking).isEqualTo(expectedSize);
    }

    @Test
    public void isUserLikingTest(){
        //arrange
        Integer testId1 = 555, testId2 = 444;
        List<PostLikes> postList = new ArrayList<>(Collections.singletonList(new PostLikes()));
        Mockito.when(postLikesRepository
                .findByLikedPost_IdAndUserThatLike_id(anyInt(),anyInt()))
                .thenReturn(postList);
        //act
        boolean isUserLiking = postLikesService.isUserLiking(testId1, testId2);
        //assert
        assertThat(isUserLiking).isTrue();
    }
}
