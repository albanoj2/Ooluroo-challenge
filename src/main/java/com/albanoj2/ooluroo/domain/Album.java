package com.albanoj2.ooluroo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
@Entity
@Table(name = "Albums")
public class Album {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
	private long id;
	
	@Column
	@NotNull
	private String artist;
	
	@Column
	@NotNull
	private String title;
	
//	@ElementCollection(targetClass=Song.class)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="albumId")
//	@OrderColumn(name = "idx")
//    @JoinTable(name = "Albums_Songs",
//    	joinColumns = { @JoinColumn(name = "albumId") },
//    	inverseJoinColumns = { @JoinColumn(name = "songId") }
//    )
	private List<Song> songs;
	
	public long getId () {
		return id;
	}

	public String getArtist () {
		return artist;
	}

	public void setArtist (String artist) {
		this.artist = artist;
	}

	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public List<Song> getSongs () {
		return songs;
	}

	public void setSongs (List<Song> songs) {
		this.songs = songs;
	}
	
	
}
