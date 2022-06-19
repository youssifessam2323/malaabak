package com.joework.Malaabak.Jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joework.Malaabak.models.ROLE;
import com.joework.Malaabak.models.User;
import com.joework.Malaabak.repositories.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase
@Rollback
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testCreatedRoles(){

        User user = new User();

        user.setEmail("test@test.com");
        user.setPassword("1234");
        user.setRole(ROLE.RENTER);
        user.setEnabled(true);

        User save = userRepository.saveAndFlush(user);

        assertTrue(save.getAuthorities().size()  == ROLE.RENTER.getAuthorites().size(), "the the two sizes are not equal");
        
    }

}
