package haagahelia.fi.Music.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AlbumRepository extends CrudRepository<Album, Long> {

	List<Album> findByTitle(String title);
	List<Album> findById(Long id);
    
}