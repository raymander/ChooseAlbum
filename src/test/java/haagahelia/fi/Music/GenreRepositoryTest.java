package haagahelia.fi.Music;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import haagahelia.fi.Music.domain.Genre;
import haagahelia.fi.Music.domain.GenreRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {

	@Autowired
	private GenreRepository repository;

	// tesing findByName method
	@Test
	public void findByNameShouldReturnId() {
		List<Genre> genres = repository.findByName("Punk");

		assertThat(genres).hasSize(1);
		assertThat(genres.get(0).getGenreid()).isEqualTo(3);
	}

	// tesing findByName method
	@Test
	public void findByIdShouldReturnName() {
		List<Genre> genres = repository.findByGenreid((long) 3);

		assertThat(genres).hasSize(1);
		assertThat(genres.get(0).getName()).isEqualTo("Punk");
	}

	// testing creating new genre object
	@Test
	public void createNewGenre() {
		Genre genre = new Genre("Jazz");
		repository.save(genre);
		assertThat(genre.getGenreid()).isNotNull();
	}

}