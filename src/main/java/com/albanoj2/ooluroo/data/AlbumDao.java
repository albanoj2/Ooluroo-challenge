package com.albanoj2.ooluroo.data;

import org.hibernate.SessionFactory;

import com.albanoj2.ooluroo.domain.Album;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
public class AlbumDao extends AbstractDAO<Album> {

	/**
	 * @param sessionFactory
	 */
	public AlbumDao (SessionFactory sessionFactory) {
		super(sessionFactory);
		System.out.println(sessionFactory);
	}

}
