package com.company.art.repository;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Post;
import com.company.art.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class PostRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
//    @Ignore
    public void findByPostUser_Id_One(){
        //arrange
        Post post = FakeDataSet.getFakePost();
        Integer expected = 1;
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        User savedUser = userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName());
        Integer generatedId = savedUser.getId();
        post.setPostUser(savedUser);
        entityManager.merge(post);
        entityManager.flush();

        //act
        List<Post> found = postRepository.findByPostUser_Id(generatedId);
        //assert
        assertThat(found.size()).isEqualTo(expected);
    }

    @Test
//    @Ignore
    public void findByPostUser_Id_Many(){
        //arrange
        Post post1 = FakeDataSet.getFakePost();
        Post post2 = FakeDataSet.getFakePost();
        Post post3 = FakeDataSet.getFakePost();
        post2.setId(2);
        post3.setId(3);
        Integer expected = 3;
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        User savedUser = userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName());
        Integer generatedId = savedUser.getId();
        post1.setPostUser(savedUser);
        post2.setPostUser(savedUser);
        post3.setPostUser(savedUser);
        entityManager.merge(post2);
        entityManager.merge(post3);
        entityManager.merge(post1);
        entityManager.flush();
        //act
        List<Post> found = postRepository.findByPostUser_Id(generatedId);
        //assert
        assertThat(found.size()).isEqualTo(expected);
    }
    @Test
    public void findByPostUser_Id_Empty(){
        //arrange
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        Integer expected = 0;
        //act
        List<Post> found = postRepository.findByPostUser_Id(FakeDataSet.getFakeUser().getId());
        //assert
        assertThat(found.size()).isEqualTo(expected);
    }

    @Test
    public void updatePostTitleTest(){
        //arrange
        String newTitle = "Totally new title";
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        Post post = FakeDataSet.getFakePost();
        User savedUser = userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName());
        post.setPostUser(savedUser);
        entityManager.merge(post);
        entityManager.flush();
        Integer generatedId = postRepository.findByPostUser_Id(savedUser.getId()).get(0).getId();
        //act
        postRepository.updatePostTitle(generatedId, newTitle);
        String postTitle = postRepository.findById(generatedId).get().getTittle();
        //assert
        assertThat(postTitle).isEqualTo(newTitle);
    }

    @Test
    public void updatePostImgTest(){
        //arrange
        byte[] newImg = new byte[111];
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        Post post = FakeDataSet.getFakePost();
        User savedUser = userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName());
        post.setPostUser(savedUser);
        entityManager.merge(post);
        entityManager.flush();
        Integer generatedId = postRepository.findByPostUser_Id(savedUser.getId()).get(0).getId();
        //act
        postRepository.updatePostImg(generatedId, newImg);
        byte[] postImg = postRepository.findById(generatedId).get().getData();
        //assert
        assertThat(postImg).isEqualTo(newImg);
    }

    @Test
    public void updatePostDescriptionTest(){
        //arrange
        String newDescription = "Totally new description";
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        Post post = FakeDataSet.getFakePost();
        User savedUser = userRepository.findByUserName(FakeDataSet.getFakeUser().getUserName());
        post.setPostUser(savedUser);
        entityManager.merge(post);
        entityManager.flush();
        Integer generatedId = postRepository.findByPostUser_Id(savedUser.getId()).get(0).getId();
        //act
        postRepository.updatePostDescription(generatedId, newDescription);
        String postDescription = postRepository.findById(generatedId).get().getDescription();
        //assert
        assertThat(postDescription).isEqualTo(newDescription);
    }
}
