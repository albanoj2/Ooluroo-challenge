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

import com.albanoj2.ooluroo.data.SongDao;
import com.albanoj2.ooluroo.domain.Song;

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
	public List<Song> getSongs (@QueryParam("filter") Optional<String> filter) {
		return new ArrayList<Song>();
	}
	
	@GET
	@Path("/{id}")
	public Song getSong (@PathParam("id") long id) {
		return null;
	}
	
	@PUT
	@Path("/{id}")
	public void updateSong (@PathParam("id") long id, Song song) {
		
	}
	
	@DELETE
	@Path("/{id}")
	public void removeSong (long id) {
		
	}
}
