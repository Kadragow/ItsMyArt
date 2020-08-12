package com.company.art.services;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Post;
import com.company.art.repository.PostRepository;
import com.company.art.repository.UserRepository;
import com.company.art.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
public class PostServiceTests {
    @TestConfiguration
    static class PostServiceTestContextConfiguration{
        @Bean
        public PostService postService(){
            return new PostService();
        }
    }

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    @Test
    public void PostSaveTest() throws Exception {
        //arrange
        Post expected = FakeDataSet.getFakePost();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        expected.setFile(file);
        Mockito.when(userRepository.findByUserName(anyString()))
                .thenReturn(FakeDataSet.getFakeUser());
        Mockito.when(postRepository.save(anyObject()))
                .thenReturn(expected);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //act
        Post saved = postService.savePost(expected);
        //assert
        assertThat(saved).isEqualTo(expected);
    }

    @Test
    public void findPostById_Success(){
        //arrange
        Post expected = FakeDataSet.getFakePost();
        Mockito.when(postRepository.findById(anyInt()))
                .thenReturn(Optional.of(expected));
        //act
        Post saved = postService.findPostById(expected.getId());
        //assert
        assertThat(saved).isEqualTo(expected);
    }

    @Test
    public void findPostById_Fail(){
        //arrange
        Integer nonExistingId = 123456;
        Mockito.when(postRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        //act
        Post saved = postService.findPostById(nonExistingId);
        //assert
        assertThat(saved).isNull();
    }

    @Test
    public void getAllPostsTest(){
        //arrange
        List<Post> expected = new ArrayList<>(Collections.singletonList(FakeDataSet.getFakePost()));
        Mockito.when(postRepository.findAll())
                .thenReturn(expected);
        //act
        List<Post> saved = postService.getAllPosts();
        //assert
        assertThat(saved).isEqualTo(expected);
    }

    @Test
    public void deletePostById_Success(){
        //arrange
        doNothing().when(postRepository).deleteById(anyInt());
        //act
        boolean actual = postService.deletePostById(FakeDataSet.getFakePost().getId());
        //assert
        assertThat(actual).isTrue();
    }

    @Test
    public void deletePostById_Fail(){
        //arrange
        doThrow(EntityNotFoundException.class).when(postRepository).deleteById(anyInt());
        //act
        boolean actual = postService.deletePostById(FakeDataSet.getFakePost().getId());
        //assert
        assertThat(actual).isFalse();
    }

    @Test
    public void updatePostTitle_Success(){
        //arrange
        Integer testedId = 1;
        String newTitle = "new one";
        doNothing().when(postRepository).updatePostTitle(anyInt(), anyString());
        //act
        boolean result = postService.updatePostTitle(testedId, newTitle);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void updatePostTitle_Fail(){
        //arrange
        Integer testedId = 1;
        String newTitle = "new one";
        doThrow(EntityNotFoundException.class).when(postRepository).updatePostTitle(anyInt(), anyString());
        //act
        boolean result = postService.updatePostTitle(testedId, newTitle);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void updatePostDescription_Success(){
        //arrange
        Integer testedId = 1;
        String newDes = "new one";
        doNothing()
                .when(postRepository)
                .updatePostDescription(anyInt(), anyString());
        //act
        boolean result = postService.updatePostDescription(testedId, newDes);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void updatePostDescription_Fail(){
        //arrange
        Integer testedId = 1;
        String newDes = "new one";
        doThrow(EntityNotFoundException.class)
                .when(postRepository)
                .updatePostDescription(anyInt(), anyString());
        //act
        boolean result = postService.updatePostDescription(testedId, newDes);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void updatePostImg_Success(){
        //arrange
        Integer testedId = 1;
        Post post = FakeDataSet.getFakePost();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        post.setFile(file);
        doNothing()
                .when(postRepository)
                .updatePostImg(anyInt(), any(byte[].class));
        //act
        boolean result = postService.updatePostImg(testedId, post);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void updatePostImg_Fail(){
        //arrange
        Integer testedId = 1;
        Post post = FakeDataSet.getFakePost();
        doThrow(EntityNotFoundException.class)
                .when(postRepository)
                .updatePostImg(anyInt(), any(byte[].class));
        //act
        boolean result = postService.updatePostImg(testedId, post);
        //assert
        assertThat(result).isFalse();
    }
}
