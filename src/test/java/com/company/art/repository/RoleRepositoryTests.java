package com.company.art.repository;

import com.company.art.ArtApplication;
import com.company.art.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArtApplication.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class RoleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByRole_Success(){
        //arrange
        Role role = new Role(2, "USER");
        entityManager.merge(role);
        entityManager.flush();
        //act
        Role found = roleRepository.findByRole(role.getRole());
        //assert
        assertThat(found.getId()).isInstanceOf(Integer.class);
    }

    @Test
    public void findByRole_Fail(){
        //arrange
        Role role = new Role(1, "USER");
        entityManager.merge(role);
        entityManager.flush();
        //act
        Role found = roleRepository.findByRole("NON_EXISTING_ROLE");
        //assert
        assertThat(found).isNull();
    }
}
