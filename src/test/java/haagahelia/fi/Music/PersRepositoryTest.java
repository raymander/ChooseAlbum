package haagahelia.fi.Music;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import haagahelia.fi.Music.domain.Fav;
import haagahelia.fi.Music.domain.PersonalRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
public class PersRepositoryTest {
    
    @Autowired
    private PersonalRepository repository;

     //testing findByUserId method
    @Test
    public void findByUserIdShouldReturnAlbumId() {
        List<Fav> favs = repository.findByUserId((long) 2);
        
        assertThat(favs).hasSize(1);
        assertThat(favs.get(0).getalbumId()).isEqualTo(1);
    }
    
    //testing findByAlbumId method
    @Test
    public void findByAlbumIdShouldReturnUserId() {
        List<Fav> favs = repository.findByAlbumId((long) 1);
        
        assertThat(favs).hasSize(1);
        assertThat(favs.get(0).getuserId()).isEqualTo(2);
    }
    
    //testing creating new Fav object
    @Test
    public void createNewFav() {
    	Fav fav = new Fav(1,3);
    	repository.save(fav);
    	assertThat(fav.getId()).isNotNull();
    } 

    //testing deleting Fav object
    @Test
    public void deleteFav() {
    	List<Fav> favs = repository.findByUserId((long)2);
    	Long id = favs.get(0).getId();
    	
    	repository.delete(id);
    	assertThat(repository.findByUserId((long)2)).hasSize(0);
    	
    }

}