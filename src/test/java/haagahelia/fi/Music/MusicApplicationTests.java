package haagahelia.fi.Music;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import haagahelia.fi.Music.web.MusicController;
import haagahelia.fi.Music.web.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicApplicationTests {

	@Autowired 
	private MusicController mcontroller;
	
	@Autowired
	private UserController ucontroller;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(mcontroller).isNotNull();
		assertThat(ucontroller).isNotNull();
	}
	

	
	

}
