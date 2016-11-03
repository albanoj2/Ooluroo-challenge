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
 * Data Access Object (DAO) that manages the interactions with the data store
 * for albums on behalf of clients.
 *
 * @author Justin Albano
 */
public class AlbumDao extends AbstractDAO<Album> {

	/**
	 * The song DAO used to interface with persisted songs.
	 */
	private SongDao songDao;

	/**
	 * @param sessionFactory
	 *            The session factory used to manage persistent albums.
	 * @param songDao
	 *            The DAO used to manage songs.
	 */
	public AlbumDao (SessionFactory sessionFactory, SongDao songDao) {
		super(sessionFactory);
		this.songDao = songDao;
	}

	/**
	 * Creates a new album.
	 * 
	 * @param album
	 *            The album to create.
	 * @return
	 * 		The ID of the newly created album.
	 */
	public long create (Album album) {
		return this.persist(album).getId();
	}

	/**
	 * Obtains the album that corresponds to the supplied ID.
	 * 
	 * @param id
	 *            The ID of the album to find.
	 * @return
	 * 		The album that corresponds to the supplied ID; if no album is
	 *         found, null is returned.
	 */
	public Album find (long id) {
		return this.currentSession().get(Album.class, id);
	}

	/**
	 * Obtains a list of albums that match the title pattern provided.
	 * 
	 * @param pattern
	 *            A pattern used to search for similar titles.
	 * @return
	 * 		A list of albums that match the title pattern provided.
	 */
	@SuppressWarnings("unchecked")
	public List<Album> findByPattern (String pattern) {

		// Create criterion for the "like" query
		Criteria criteria = this.currentSession().createCriteria(Album.class);

		// Append the "like" to match strings in the title of the album
		criteria.add(Restrictions.like("title", pattern, MatchMode.ANYWHERE));

		return (List<Album>) criteria.list();
	}

	/**
	 * Obtains a list of all existing albums.
	 * 
	 * @return
	 * 		A list of all existing albums.
	 */
	@SuppressWarnings("unchecked")
	public List<Album> findAll () {
		return (List<Album>) this.currentSession().createCriteria(Album.class).list();
	}

	/**
	 * Updates the supplied album.
	 * 
	 * @param album
	 *            The album to update in persistent storage.
	 */
	public void update (Album album) {
		this.persist(album);
	}

	/**
	 * Deletes the album corresponding to the supplied ID.
	 * 
	 * @param id
	 *            The ID of the album to delete.
	 */
	public void deleteById (long id) {
		this.delete(this.find(id));
	}

	/**
	 * Deletes the supplied album.
	 * 
	 * @param album
	 *            The album to delete.
	 */
	public void delete (Album album) {
		this.currentSession().delete(album);
	}

	/**
	 * Adds a song to the album corresponding to the supplied album ID.
	 * 
	 * @param albumId
	 *            The ID corresponding to the album to add the song to.
	 * @param song
	 *            The song to add.
	 * @return
	 * 		The ID of the newly created song.
	 */
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
