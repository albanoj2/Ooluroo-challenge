package com.albanoj2.ooluroo.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.albanoj2.ooluroo.domain.Song;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * Data Access Object (DAO) that manages the interactions with the data store
 * for songs on behalf of clients.
 *
 * @author Justin Albano
 */
public class SongDao extends AbstractDAO<Song> {

	/**
	 * @param sessionFactory
	 *            The session factory used to manage persistent songs.
	 */
	public SongDao (SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Creates the supplied song.
	 * 
	 * @param song
	 *            The song to create.
	 * @return
	 * 		The ID of the newly created song.
	 */
	public long create (Song song) {
		return this.persist(song).getId();
	}

	/**
	 * Obtains the song corresponding to the supplied ID.
	 * 
	 * @param id
	 *            The ID of the song to find.
	 * @return
	 * 		The song corresponding to the supplied ID; if a song cannot be
	 *         found, null is returned.
	 */
	public Song find (long id) {
		return this.currentSession().get(Song.class, id);
	}

	/**
	 * Obtains a list of songs that match the title pattern provided.
	 * 
	 * @param pattern
	 *            A pattern used to search for similar titles.
	 * @return
	 * 		A list of songs that match the title pattern provided.
	 */
	@SuppressWarnings("unchecked")
	public List<Song> findByPattern (String pattern) {

		// Create criterion for the "like" query
		Criteria criteria = this.currentSession().createCriteria(Song.class);

		// Append the "like" to match strings in the title of the song
		criteria.add(Restrictions.like("title", pattern, MatchMode.ANYWHERE));

		return (List<Song>) criteria.list();
	}

	/**
	 * Obtains a list of all existing songs.
	 * 
	 * @return
	 * 		A list of all existing songs.
	 */
	@SuppressWarnings("unchecked")
	public List<Song> findAll () {
		return (List<Song>) this.currentSession().createCriteria(Song.class).list();
	}

	/**
	 * Updates the supplied song.
	 * 
	 * @param album
	 *            The song to update in persistent storage.
	 */
	public void update (Song song) {
		this.persist(song);
	}

	/**
	 * Deletes the song corresponding to the supplied ID.
	 * 
	 * @param id
	 *            The ID of the song to delete.
	 */
	public void deleteById (long id) {
		this.delete(this.find(id));
	}

	/**
	 * Deletes the supplied song.
	 * 
	 * @param album
	 *            The song to delete.
	 */
	public void delete (Song song) {
		this.currentSession().delete(song);
	}

}
