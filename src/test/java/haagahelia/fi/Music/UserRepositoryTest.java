package haagahelia.fi.Music;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import haagahelia.fi.Music.domain.User;
import haagahelia.fi.Music.domain.UserRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    //testing findByUsername method
    @Test
    public void findByUsernameShouldReturnRole() {
        User user = repository.findByUsername("user");
        
        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo("USER");
    }
    
    //testing creating new user object
    @Test
    public void createNewUser() {
    	User user = new User("user2", "password", "ADMIN");
    	repository.save(user);
    	assertThat(user.getUsername()).isNotNull();
    }
    


}