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
	public List<Album> getAlbums (@QueryParam("filter") Optional<String> filter) {
		return new ArrayList<Album>();
	}
	
	@PUT
	public long addAlbum (Album album) {
		return -1;
	}
	
	@GET
	@Path("/{id}")
	public Album getAlbum (@PathParam("id") long id) {
		return new Album();
	}
	
	@PUT
	@Path("/{id}")
	public void updateAlbum (@PathParam("id")long id, Album album) {
	}
	
	@DELETE
	@Path("/{id}")
	public void removeAlbum (@PathParam("id") long id) {
		
	}
	
	@GET
	@Path("/{id}/songs")
	public List<Song> getSongs (@PathParam("id") long id) {
		return new ArrayList<Song>();
	}
	
	@PUT
	@Path("/{id}/songs")
	public long addSong (@PathParam("id") long id, Song song) {
		return -1;
	}
}