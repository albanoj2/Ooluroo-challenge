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
 * TODO Class documentation
 *
 * @author Justin Albano
 */
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
public class SongsResource {
	
	private SongDao dao;
	
	public SongsResource (SongDao dao) {
		this.dao = dao;
	}

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
	
	@GET
	@Path("/{id}")
	@UnitOfWork
	public Song getSong (@PathParam("id") long id) {
		return this.dao.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@UnitOfWork
	public void updateSong (@PathParam("id") long id, Song song) {
		this.dao.update(song);
	}
	
	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public void removeSong (@PathParam("id") long id) {
		this.dao.deleteById(id);
	}
}
