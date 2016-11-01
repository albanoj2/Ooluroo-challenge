package com.albanoj2.ooluroo.domain;

import java.util.List;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
public class Album {

	private String artist;
	private String title;
	private List<Song> songs;

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
