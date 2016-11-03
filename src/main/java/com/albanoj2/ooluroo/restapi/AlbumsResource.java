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

import com.albanoj2.ooluroo.data.AlbumDao;
import com.albanoj2.ooluroo.domain.Album;
import com.albanoj2.ooluroo.domain.Song;

import io.dropwizard.hibernate.UnitOfWork;

/**
 * Resource class representing the RESTful API endpoints pertaining to an album
 * resource.
 *
 * @author Justin Albano
 */
@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
public class AlbumsResource {

	/**
	 * Data Access Object (DAO) that interfaces with persistent storage on
	 * behalf of this resource.
	 */
	private AlbumDao dao;

	/**
	 * @param dao
	 *            Data Access Object (DAO) that interfaces with persistent
	 *            storage on behalf of this resource.
	 */
	public AlbumsResource (AlbumDao dao) {
		this.dao = dao;
	}

	/**
	 * Obtains a list of all existing albums.
	 * 
	 * @param pattern
	 *            Optional pattern that filters the list of albums by the title
	 *            of the album.
	 * @return
	 * 		The list of existing albums, filtered by the supplied pattern if
	 *         one is provided.
	 */
	@GET
	@UnitOfWork
	public List<Album> getAlbums (@QueryParam("filter") Optional<String> pattern) {

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
	 * Adds a new album.
	 * 
	 * @param album
	 *            The album to add.
	 * @return
	 * 		The ID of the newly created album.
	 */
	@PUT
	@UnitOfWork
	public long addAlbum (Album album) {
		return dao.create(album);
	}

	/**
	 * Obtains the album corresponding to the supplied album ID.
	 * 
	 * @param id
	 *            The ID of the album to obtain.
	 * @return
	 * 		The album corresponding to the supplied album ID; if no album is
	 *         found, null is returned.
	 */
	@GET
	@Path("/{id}")
	@UnitOfWork
	public Album getAlbum (@PathParam("id") long id) {
		return this.dao.find(id);
	}

	/**
	 * Updates an album.
	 * 
	 * @param id
	 *            The ID of the album to update.
	 * @param album
	 *            The updated album data.
	 */
	@PUT
	@Path("/{id}")
	@UnitOfWork
	public void updateAlbum (@PathParam("id") long id, Album album) {
		this.dao.update(album);
	}

	/**
	 * Removes an album.
	 * 
	 * @param id
	 *            The ID of the album to remove.
	 */
	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public void removeAlbum (@PathParam("id") long id) {
		this.dao.deleteById(id);
	}

	/**
	 * Obtains a list of the songs associated with the album corresponding to
	 * the supplied album ID.
	 * 
	 * @param id
	 *            The ID of the album.
	 * @return
	 * 		A list of songs associated with the album corresponding to the
	 *         supplied album ID.
	 */
	@GET
	@Path("/{id}/songs")
	@UnitOfWork
	public List<Song> getSongs (@PathParam("id") long id) {
		return this.dao.find(id).getSongs();
	}

	/**
	 * Adds a song to the album corresponding to the supplied album ID.
	 * 
	 * @param albumId
	 *            The ID of the album to which the supplied song is added.
	 * @param song
	 *            The song to add.
	 * @return
	 * 		The ID of the newly created song.
	 */
	@PUT
	@Path("/{id}/songs")
	@UnitOfWork
	public long addSong (@PathParam("id") long albumId, Song song) {
		return this.dao.addSong(albumId, song);
	}
}