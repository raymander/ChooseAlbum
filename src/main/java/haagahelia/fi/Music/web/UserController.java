package haagahelia.fi.Music.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.validation.Valid;

import haagahelia.fi.Music.domain.SignupForm;
import haagahelia.fi.Music.domain.User;
import haagahelia.fi.Music.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository repository;

	@RequestMapping(value = "signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}

	/**
	 * Create new user Check if user already exists & form validation
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */

	// RESTful service to get all users
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> userListRest() {
		return (List<User>) repository.findAll();
	}

	// RESTful service to get user by id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody User findGenreRest(@PathVariable("id") Long userid) {
		return repository.findOne(userid);
	}

	// RESTful service to delete user by id
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteUserRest(@PathVariable("id") Long userid) {
		repository.delete(userid);
		return "User with id " + userid + " deleted succesfully";
	}

	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) { // validation errors
			if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check
																					// password
																					// match
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername());
				newUser.setRole("USER");
				if (repository.findByUsername(signupForm.getUsername()) == null) { // Check
																					// if
																					// user
																					// exists
					repository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login";
	}

}
