package com.albanoj2.ooluroo.data;

import java.util.List;

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

	public long create (Album album) {
		return this.persist(album).getId();
	}
	
	public Album find (long id) {
		return this.get(id);
	}
	
	public List<Album> findByPattern (String pattern) {
		return null;
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
}
