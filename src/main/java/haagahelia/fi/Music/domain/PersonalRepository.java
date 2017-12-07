package haagahelia.fi.Music.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonalRepository extends CrudRepository<Fav, Long> {
		

	List<Fav> findByAlbumId(Long albumId);
	List<Fav> findByUserId(Long userId);
	
} 


