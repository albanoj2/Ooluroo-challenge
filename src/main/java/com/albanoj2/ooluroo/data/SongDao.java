package com.albanoj2.ooluroo.data;

import org.hibernate.SessionFactory;

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

}
