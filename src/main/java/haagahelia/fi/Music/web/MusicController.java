package haagahelia.fi.Music.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

import java.security.Principal;

import haagahelia.fi.Music.domain.AlbumRepository;
import haagahelia.fi.Music.domain.Fav;
import haagahelia.fi.Music.domain.Genre;
import haagahelia.fi.Music.domain.GenreRepository;
import haagahelia.fi.Music.domain.Album;
import haagahelia.fi.Music.domain.PersonalRepository;
import haagahelia.fi.Music.domain.User;
import haagahelia.fi.Music.domain.UserRepository;

@Controller
public class MusicController {

	@Autowired
	private AlbumRepository repository;

	@Autowired
	private PersonalRepository persrepository;

	@Autowired
	private UserRepository urepository;

	@Autowired
	private GenreRepository grepository;


	@RequestMapping(value ={"/login", "/"})
	public String login() {
		return "login";
	}

	// list all music available
	@RequestMapping(value = "/allmusic")
	public String musicList(Model model) {
		model.addAttribute("albums", repository.findAll());
		return "allmusic";
	}

	// delete an album (for admin)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteAlbum(@PathVariable("id") Long albumId, Model model) {
		repository.delete(albumId);
		return "redirect:/allmusic";
	}

	// Edit the album (for admin)
	@RequestMapping(value = "/edit/{id}")
	public String editAlbum(@PathVariable("id") Long albumId, Model model) {
		model.addAttribute("album", repository.findOne(albumId));
		model.addAttribute("genres", grepository.findAll());
		return "editalbum";
	}

	// add new album (for admin)
	@RequestMapping(value = "/add")
	public String addBook(Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute("genres", grepository.findAll());
		return "addalbum";
	}

	// save new album (for admin)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Album album) {
		repository.save(album);
		return "redirect:/allmusic";
	}

	// RESTful service to get all albums
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public @ResponseBody List<Album> albumListRest() {
		return (List<Album>) repository.findAll();
	}

	// RESTful service to get album by id
	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public @ResponseBody Album findAlbumRest(@PathVariable("id") Long albumId) {
		return repository.findOne(albumId);
	}

	// RESTful service to delete album by id
	@RequestMapping(value = "/album/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteAlbumRest(@PathVariable("id") Long albumId) {
		repository.delete(albumId);
		return "Album with id " + albumId + " deleted succesfully";
	}

	// RESTful service to get all favs
	@RequestMapping(value = "/favs", method = RequestMethod.GET)
	public @ResponseBody List<Fav> favListRest() {
		return (List<Fav>) persrepository.findAll();
	}

	// RESTful service to get fav by id
	@RequestMapping(value = "/fav/{id}", method = RequestMethod.GET)
	public @ResponseBody Fav findFavRest(@PathVariable("id") Long id) {
		return persrepository.findOne(id);
	}

	// RESTful service to delete fav by id
	@RequestMapping(value = "/fav/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteFavRest(@PathVariable("id") Long id) {
		persrepository.delete(id);
		return "Favourite album with id " + id + " deleted succesfully";
	}

	// RESTful service to get all genres
	@RequestMapping(value = "/genres", method = RequestMethod.GET)
	public @ResponseBody List<Genre> genreListRest() {
		return (List<Genre>) grepository.findAll();
	}

	// RESTful service to get genre by id
	@RequestMapping(value = "/genre/{id}", method = RequestMethod.GET)
	public @ResponseBody Genre findGenreRest(@PathVariable("id") Long genreid) {
		return grepository.findOne(genreid);
	}

	// adding album to personal user list
	@RequestMapping(value = "/like/{id}", method = RequestMethod.GET)
	public String likeAlbum(@PathVariable("id") Long albumId, Model model, Principal principal) {

		// get User Id
		String currUserName = principal.getName();
		User currUser = urepository.findByUsername(currUserName);
		Long currUserId = currUser.getId();

		// new Fav object
		Fav fav = new Fav(currUserId, albumId);

		// restriction to duplicate albums in user list
		Boolean alreadyExists = true;

		for (int i = 0; i < persrepository.findByUserId(currUserId).size(); i++) {
			if ((persrepository.findByUserId(currUserId).get(i).getalbumId() == albumId)
					&& (persrepository.findByUserId(currUserId).get(i).getuserId() == currUserId)) {
				alreadyExists = false;
			}
		}

		if (alreadyExists) {
			persrepository.save(fav);
		}

		return "redirect:/allmusic";
	}

	// userpage
	@RequestMapping(value = "/mylist")
	public String myalbumList(Model model, Principal principal) {
		String currUserName = principal.getName();
		User currUser = urepository.findByUsername(currUserName);
		Long currUserId = currUser.getId();

		List<Album> albumList = new ArrayList<Album>();
		List<Fav> favList = persrepository.findByUserId(currUserId);
		System.out.println(favList);

		for (int i = 0; i < favList.size(); i++) {
			Long albumId = favList.get(i).getalbumId();
			albumList.add(repository.findOne(albumId));
		}
		model.addAttribute("albums", albumList);
		return "mylist";
	}

	// unlike album - remove it from user list
	@RequestMapping(value = "/deletelike/{id}", method = RequestMethod.GET)
	public String deleteLike(@PathVariable("id") Long albumId, Model model, Principal principal) {

		// getting curent user Id
		String currUserName = principal.getName();
		User currUser = urepository.findByUsername(currUserName);
		Long currUserId = currUser.getId();

		// loop to make sure we don't delete this Fav from all users' lists, and
		// actually deleting it
		for (int i = 0; i < persrepository.findByUserId(currUserId).size(); i++) {
			if ((persrepository.findByUserId(currUserId).get(i).getalbumId() == albumId)
					&& (persrepository.findByUserId(currUserId).get(i).getuserId() == currUserId)) {
				persrepository.delete(persrepository.findByUserId(currUserId).get(i));
			}
		}

		return "redirect:/mylist";
	}

	// choosing random album from personal user list
	@RequestMapping(value = "/random")
	public String rand(Model model, Principal principal) {

		// get user Id
		String currUserName = principal.getName();
		User currUser = urepository.findByUsername(currUserName);
		Long currUserId = currUser.getId();

		// creating albumList to put liked albums there
		List<Album> albumList = new ArrayList<Album>();
		List<Fav> favList = persrepository.findByUserId(currUserId);

		for (int i = 0; i < favList.size(); i++) {
			Long albumId = favList.get(i).getalbumId();
			albumList.add(repository.findOne(albumId));
		}

		// actual random
		int all = albumList.size();
		Random rand = new Random();
		int n = rand.nextInt(all);
		Long id = albumList.get(n).getId(); // get album id from object
											// number n
		model.addAttribute("albums", repository.findOne(id));
		return "choice";
	}

}
