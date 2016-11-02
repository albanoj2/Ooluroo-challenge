package com.albanoj2.ooluroo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
@Entity
@Table(name = "Songs")
public class Song {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
	private long id;
	
	@Column
	@NotNull
	private String title;
	
	@Column
	@NotNull
	private int lengthInSeconds;
	
	@Column
	@NotNull
	private long albumId;
	
	public Song (String title, int lengthInSeconds, long albumId) {
		this.title = title;
		this.lengthInSeconds = lengthInSeconds;
		this.albumId = albumId;
	}
	
	public Song (String title, int lengthInSeconds) {
		this(title, lengthInSeconds, 0);
	}

	public long getId () {
		return id;
	}

	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public int getLengthInSeconds () {
		return lengthInSeconds;
	}

	public void setLengthInSeconds (int lengthInSeconds) {
		this.lengthInSeconds = lengthInSeconds;
	}

	public long getAlbumId () {
		return albumId;
	}

	public void setAlbumId (long albumId) {
		this.albumId = albumId;
	}

}
