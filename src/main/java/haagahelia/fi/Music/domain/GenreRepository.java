package haagahelia.fi.Music.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository <Genre, Long> {

	List<Genre> findByName(String name);
	List<Genre> findByGenreid(Long genreid);
   

}
