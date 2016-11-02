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
public class SongDao extends AbstractDAO<Song> {

	/**
	 * @param sessionFactory
	 */
	public SongDao (SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public long create (Song song) {
		return this.persist(song).getId();
	}
	
	public Song find (long id) {
		return this.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Song> findByPattern (String pattern) {
		
		// Create criterion for the "like" query
		Criteria criteria = this.currentSession().createCriteria(Album.class);
		
		// Append the "like" to match strings in the title of the song
		criteria.add(Restrictions.like("title", pattern, MatchMode.ANYWHERE));
		
		return (List<Song>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Song> findAll () {
		return (List<Song>) this.currentSession().createCriteria(Song.class).list();
	}
	
	public void update (Song song) {
		this.persist(song);
	}
	
	public void deleteById (long id) {
		this.delete(this.find(id));
	}
	
	public void delete (Song song) {
		this.currentSession().delete(song);
	}

}
