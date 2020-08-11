package com.company.art.repository;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.Post;
import com.company.art.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
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

    @Test
    @Ignore
    public void findByPostUser_Id_One(){
        //arrange
        Post post = FakeDataSet.getFakePost();
        Integer expected = 1;
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();
        entityManager.merge(post);
        entityManager.flush();

        //act
        List<Post> found = postRepository.findByPostUser_Id(FakeDataSet.getFakeUser().getId());
        //assert
        assertThat(found.size()).isEqualTo(expected);
    }

    @Test
    @Ignore
    public void findByPostUser_Id_Many(){
        //arrange
        Post post1 = FakeDataSet.getFakePost();
        Post post2 = FakeDataSet.getFakePost();
        post2.setId(2);
        Post post3 = FakeDataSet.getFakePost();
        post3.setId(3);
        Integer expected = 3;
        entityManager.merge(FakeDataSet.getFakeUser());
        entityManager.flush();

        entityManager.merge(post1);

        entityManager.merge(post2);

        entityManager.merge(post3);
        entityManager.flush();
        //act
        List<Post> found = postRepository.findByPostUser_Id(FakeDataSet.getFakeUser().getId());
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
}
