package com.albanoj2.ooluroo;

import com.albanoj2.ooluroo.restapi.AlbumsResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OolurooChallengeApplication extends Application<OolurooChallengeConfiguration> {

	public static void main (String[] args) throws Exception {
		new OolurooChallengeApplication().run(args);
	}

	@Override
	public String getName () {
		return "hello-world";
	}

	@Override
	public void initialize (Bootstrap<OolurooChallengeConfiguration> bootstrap) {}

	@Override
	public void run (OolurooChallengeConfiguration config, Environment environment) throws Exception {
		// Create the resources
		AlbumsResource albumsResource = new AlbumsResource();
		
		// Register the resources
		environment.jersey().register(albumsResource);
	}

}
