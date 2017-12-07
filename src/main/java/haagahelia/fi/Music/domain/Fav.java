package haagahelia.fi.Music.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Fav {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private long userId;
	private long albumId;

	public Fav() {
	}

	public Fav(long userId, long albumId) {
		super();
		this.userId = userId;
		this.albumId = albumId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId = userId;
	}

	public long getalbumId() {
		return albumId;
	}

	public void setalbumId(long albumId) {
		this.albumId = albumId;
	}

	@Override
	public String toString() {
		return "Fav [id=" + id + ", userId=" + userId + ", albumId=" + albumId + "]";
	}

}