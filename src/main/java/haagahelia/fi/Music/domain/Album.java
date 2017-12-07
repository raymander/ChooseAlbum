package haagahelia.fi.Music.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String band;
	private int year;
	private String country;
	private String pic;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "genreid")
	private Genre genre;


	public Album() {
	}
	
	public Album(String pic, String title, String band, int year, String country, Genre genre) {
		super();
		this.pic = pic;
		this.title = title;
		this.band = band;
		this.year = year;
		this.country = country;
		this.genre = genre;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}



	@Override
	public String toString() {
		if (this.genre != null)
			return "Album [id=" + id + ", picture=" + pic + ", title=" + title + ", band=" + band + ", year=" + year + ", country=" + country
					+ ",genre=" + this.getGenre() + "]";
		else
			return "Album [id=" + id + ", picture=" + pic + ", title=" + title + ", band=" + band + ", year=" + year + ", country=" + country
					+ "]";
	}

}
