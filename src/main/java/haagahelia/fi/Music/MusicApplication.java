package haagahelia.fi.Music;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import haagahelia.fi.Music.domain.Album;
import haagahelia.fi.Music.domain.AlbumRepository;
import haagahelia.fi.Music.domain.Genre;
import haagahelia.fi.Music.domain.GenreRepository;
import haagahelia.fi.Music.domain.User;
import haagahelia.fi.Music.domain.Fav;
import haagahelia.fi.Music.domain.UserRepository;
import haagahelia.fi.Music.domain.PersonalRepository;

@SpringBootApplication
public class MusicApplication {
	private static final Logger log = LoggerFactory.getLogger(MusicApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner musicDemo(AlbumRepository repository, GenreRepository grepository, UserRepository urepository, PersonalRepository persrepository) {
		return (args) -> {
			
			//creating some genres
			grepository.save(new Genre("Blues"));
			grepository.save(new Genre("Glam rock"));
			grepository.save(new Genre("Punk"));
			grepository.save(new Genre("Industrial"));
			grepository.save(new Genre("Experimental rock"));
			grepository.save(new Genre("Psychedelic rock"));
			grepository.save(new Genre("Alternative rock"));
			grepository.save(new Genre("Soul"));
			grepository.save(new Genre("Rock"));
			grepository.save(new Genre("Rock and roll"));

			//creating some albums
			repository.save(new Album("https://s-media-cache-ak0.pinimg.com/originals/c6/27/c5/c627c53ca9604c58ea8ce938e6730f8e.jpg","Silence Is Sexy", "Einstürzende Neubauten", 2000, "Germany",grepository.findByName("Industrial").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/a/a7/Oriental_Beat.jpg", "Oriental Beat", "Hanoi Rocks", 1982, "Finand", grepository.findByName("Glam rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/ru/7/72/IggyPopLustForLife.jpg", "Lust For Life", "Iggy Pop", 1977, "Germany", grepository.findByName("Punk").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/3/38/Fromhertoeternity.jpg", "From Her To Eternity", "Nick Cave & The Bad Seeds", 1984, "England", grepository.findByName("Experimental rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/b/b5/Young_americans.jpg", "Young Americans", "David Bowie", 1975, "USA", grepository.findByName("Soul").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/2/24/SteelWheels89.jpg", "Steel Wheels", "The Rolling Stones", 1989, "England", grepository.findByName("Rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/c/cd/The_Doors_-_Morrison_Hotel.jpg", "Morrison Hotel", "The Doors", 1970, "USA", grepository.findByName("Psychedelic rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/f/fd/To_Bring_You_My_Love.jpg", "To Bring You My Love", "PJ Harvey", 1995, "England", grepository.findByName("Rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/1/13/PinkFloydWallCoverOriginalNoText.jpg", "The Wall", "Pink Floyd", 1979, "England", grepository.findByName("Experimental rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/9/9d/Placebo_album.jpg", "Placebo", "Placebo", 1996, "Ireland", grepository.findByName("Alternative rock").get(0)));
			repository.save(new Album("https://upload.wikimedia.org/wikipedia/en/3/3d/Chuck_Berry_Is_on_Top_cover.jpg", "Chuck Berry Is On Top", "Chuck Berry", 1959, "USA", grepository.findByName("Rock and roll").get(0)));

			// Create users: admin/admin user/user
			User user1 = new User("user", "$2a$06$ROH.6r8cM1IfWS6wa1rhQOWfndYCwFnFGNyKdUrjFXfpPWnnnFfsm", "USER");
			User user2 = new User("admin", "$2a$06$fClI3fmnqZPuWcB7Ac40bOwku33v124kdtl2wSwcUiguX.STIYpbG", "ADMIN");	
			urepository.save(user1);
			urepository.save(user2);
		
			
			persrepository.save(new Fav(2,1));
			
			//console checking
			log.info("fetch all albums");
			for (Album album : repository.findAll()) {
				log.info(album.toString());
			}

		};
	}
	
}
