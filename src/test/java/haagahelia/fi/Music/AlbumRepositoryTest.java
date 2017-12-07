package haagahelia.fi.Music;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import haagahelia.fi.Music.domain.Album;
import haagahelia.fi.Music.domain.AlbumRepository;
import haagahelia.fi.Music.domain.GenreRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository repository;
    
    @Autowired
    private GenreRepository grepository;

    
    //testing findByTitle method
    @Test
    public void findByTitleShouldReturnAlbum() {
        List<Album> albums = repository.findByTitle("Oriental Beat");
        
        assertThat(albums).hasSize(1);
        assertThat(albums.get(0).getYear()).isEqualTo(1982);
    }
    
    //testing findById method
    @Test
    public void findByIdShouldReturnAlbum() {
        List<Album> albums = repository.findById((long)1);
        
        assertThat(albums).hasSize(1);
        assertThat(albums.get(0).getTitle()).isEqualTo("Silence Is Sexy");
    }
    
    //testing creating new album
    @Test
    public void createNewAlbum() {
    	Album album = new Album("https://upload.wikimedia.org/wikipedia/en/5/52/Ramones_-_Road_to_Ruin_cover.jpg","Road To Ruin", "Ramones", 1978, "USA",grepository.findByName("Punk").get(0));
    	repository.save(album);
    	assertThat(album.getId()).isNotNull();
    } 
    
    //testing deleting an album 
    @Test
    public void deleteAlbum() {
    	List<Album> albums = repository.findByTitle("Oriental Beat");
    	Long id = albums.get(0).getId();
    	
    	repository.delete(id);
    	assertThat(repository.findByTitle("Oriental Beat")).hasSize(0);
    	
    }

}