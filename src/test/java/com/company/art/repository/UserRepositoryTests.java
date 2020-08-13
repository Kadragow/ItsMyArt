package com.company.art.repository;

import com.company.art.ArtApplication;
import com.company.art.FakeDataSet;
import com.company.art.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmail_Success(){

        //arrange
        User user = FakeDataSet.getFakeUser();
        entityManager.merge(user);
        entityManager.flush();
        //act
        User found = userRepository.findByEmail(user.getEmail());
        //assert
        assertThat(found.getUserName()).isEqualTo(user.getUserName());
    }

    @Test
    public void findByEmail_Fail(){

        //arrange
        User user = FakeDataSet.getFakeUser();
        entityManager.merge(user);
        entityManager.flush();
        //act
        User found = userRepository.findByEmail("fake@mail.com");
        //assert
        assertThat(found).isNull();
    }

    @Test
    public void findByUsername_Success(){

        //arrange
        User user = FakeDataSet.getFakeUser();
        entityManager.merge(user);
        entityManager.flush();
        //act
        User found = userRepository.findByUserName(user.getUserName());
        //assert
        assertThat(found.getUserName()).isEqualTo(user.getUserName());
    }

    @Test
    public void findByUsername_Fail(){

        //arrange
        User user = FakeDataSet.getFakeUser();
        entityManager.merge(user);
        entityManager.flush();
        //act
        User found = userRepository.findByUserName("Faked");
        //assert
        assertThat(found).isNull();
    }
}
