package com.albanoj2.ooluroo.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.albanoj2.ooluroo.domain.Album;
import com.albanoj2.ooluroo.domain.Song;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
public class AlbumDao extends AbstractDAO<Album> {
	
	private SongDao songDao;

	/**
	 * @param sessionFactory
	 */
	public AlbumDao (SessionFactory sessionFactory, SongDao songDao) {
		super(sessionFactory);
		this.songDao = songDao;
	}

	public long create (Album album) {
		return this.persist(album).getId();
	}
	
	public Album find (long id) {
		return this.currentSession().get(Album.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findByPattern (String pattern) {
		
		// Create criterion for the "like" query
		Criteria criteria = this.currentSession().createCriteria(Album.class);
		
		// Append the "like" to match strings in the title of the album
		criteria.add(Restrictions.like("title", pattern, MatchMode.ANYWHERE));
		
		return (List<Album>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findAll () {
		return (List<Album>) this.currentSession().createCriteria(Album.class).list();
	}
	
	public void update (Album album) {
		this.persist(album);
	}
	
	public void deleteById (long id) {
		this.delete(this.find(id));
	}
	
	public void delete (Album album) {
		this.currentSession().delete(album);
	}
	
	public long addSong (long albumId, Song song) {
		
		// Retrieve the album
		Album album = this.find(albumId);
		
		// Set the album ID of the song
		song.setAlbumId(album.getId());
		
		// Persist the song
		long createdSongId = this.songDao.create(song);
		
		// Add the song to the retrieved album
		album.addSong(song);
		
		// Update the album to include the new song
		this.update(album);
		
		return createdSongId;
	}
}
