package com.albanoj2.ooluroo;

import com.albanoj2.ooluroo.data.AlbumDao;
import com.albanoj2.ooluroo.data.SongDao;
import com.albanoj2.ooluroo.domain.Album;
import com.albanoj2.ooluroo.domain.Song;
import com.albanoj2.ooluroo.restapi.AlbumsResource;
import com.albanoj2.ooluroo.restapi.SongsResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OolurooChallengeApplication extends Application<OolurooChallengeConfiguration> {

	private final HibernateBundle<OolurooChallengeConfiguration> hibernate = new HibernateBundle<OolurooChallengeConfiguration>(Album.class, Song.class) {

		public DataSourceFactory getDataSourceFactory (OolurooChallengeConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}

	};

	public static void main (String[] args) throws Exception {
		new OolurooChallengeApplication().run(args);
	}

	@Override
	public void initialize (Bootstrap<OolurooChallengeConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run (OolurooChallengeConfiguration config, Environment environment) throws Exception {
		// Create the Data Access Objects (DAOs)
		SongDao songDao = new SongDao(hibernate.getSessionFactory());
		AlbumDao albumDao = new AlbumDao(hibernate.getSessionFactory(), songDao);
		
		// Create the resources
		AlbumsResource albumsResource = new AlbumsResource(albumDao);
		SongsResource songsResource = new SongsResource(songDao);

		// Register the resources
		environment.jersey().register(albumsResource);
		environment.jersey().register(songsResource);
	}

}
