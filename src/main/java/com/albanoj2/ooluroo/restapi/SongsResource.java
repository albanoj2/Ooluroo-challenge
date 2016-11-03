package com.albanoj2.ooluroo.restapi;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.albanoj2.ooluroo.data.SongDao;
import com.albanoj2.ooluroo.domain.Song;

import io.dropwizard.hibernate.UnitOfWork;

/**
 * Resource class representing the RESTful API endpoints pertaining to a song
 * resource.
 *
 * @author Justin Albano
 */
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
public class SongsResource {

	/**
	 * Data Access Object (DAO) that interfaces with persistent storage on
	 * behalf of this resource.
	 */
	private SongDao dao;

	/**
	 * @param dao
	 *            Data Access Object (DAO) that interfaces with persistent
	 *            storage on behalf of this resource.
	 */
	public SongsResource (SongDao dao) {
		this.dao = dao;
	}

	/**
	 * Obtains a list of all existing songs that match the supplied title
	 * pattern, if one is provided.
	 * 
	 * @param pattern
	 *            A pattern that filters the list of songs by titles similar to
	 *            the pattern.
	 * @return
	 * 		A list of all existing songs that match the supplied title
	 *         pattern, if one is provided.
	 */
	@GET
	@UnitOfWork
	public List<Song> getSongs (@QueryParam("filter") Optional<String> pattern) {

		if (pattern.isPresent()) {
			// A pattern is provided
			return this.dao.findByPattern(pattern.get());
		}
		else {
			// No pattern is present
			return this.dao.findAll();
		}
	}

	/**
	 * Obtains the song corresponding to the supplied ID.
	 * 
	 * @param id
	 *            The ID of the song to obtain.
	 * @return
	 * 		The song corresponding to the supplied ID; if no song is found,
	 *         null is returned.
	 */
	@GET
	@Path("/{id}")
	@UnitOfWork
	public Song getSong (@PathParam("id") long id) {
		return this.dao.find(id);
	}

	/**
	 * Updates a song.
	 * 
	 * @param id
	 *            The ID of the song to update.
	 * @param song
	 *            The updated song.
	 */
	@PUT
	@Path("/{id}")
	@UnitOfWork
	public void updateSong (@PathParam("id") long id, Song song) {
		this.dao.update(song);
	}

	/**
	 * Removes the song corresponding to the supplied song ID.
	 * 
	 * @param id
	 *            The ID of the song to remove.
	 */
	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public void removeSong (@PathParam("id") long id) {
		this.dao.deleteById(id);
	}
}
