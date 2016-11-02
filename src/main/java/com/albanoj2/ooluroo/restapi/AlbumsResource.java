package com.albanoj2.ooluroo.restapi;

import java.util.ArrayList;
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
 * TODO Class documentation
 *
 * @author Justin Albano
 */
@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
public class AlbumsResource {
	
	private AlbumDao dao;
	
	public AlbumsResource (AlbumDao dao) {
		this.dao = dao;
	}

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
	
	@PUT
	@UnitOfWork
	public long addAlbum (Album album) {
		return dao.create(album);
	}
	
	@GET
	@Path("/{id}")
	@UnitOfWork
	public Album getAlbum (@PathParam("id") long id) {
		return this.dao.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@UnitOfWork
	public void updateAlbum (@PathParam("id")long id, Album album) {
		this.dao.update(album);
	}
	
	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public void removeAlbum (@PathParam("id") long id) {
		this.dao.deleteById(id);
	}
	
	@GET
	@Path("/{id}/songs")
	@UnitOfWork
	public List<Song> getSongs (@PathParam("id") long id) {
		return new ArrayList<Song>();
	}
	
	@PUT
	@Path("/{id}/songs")
	@UnitOfWork
	public long addSong (@PathParam("id") long id, Song song) {
		return -1;
	}
}