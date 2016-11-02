package com.albanoj2.ooluroo.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.albanoj2.ooluroo.OolurooChallengeApplication;
import com.albanoj2.ooluroo.OolurooChallengeConfiguration;
import com.albanoj2.ooluroo.data.AlbumDao;
import com.albanoj2.ooluroo.domain.Album;
import com.albanoj2.ooluroo.restapi.AlbumsResource;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
public class AlbumsResourceTest {

	private static final AlbumDao dao = mock(AlbumDao.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new AlbumsResource(dao)).build();

	@ClassRule
	public static final DropwizardAppRule<OolurooChallengeConfiguration> RULE = new DropwizardAppRule<OolurooChallengeConfiguration>(
		OolurooChallengeApplication.class, ResourceHelpers.resourceFilePath("testing-configuration.yaml"));

	private final Album album = new Album("__test band__", "__test album title__");
	private Client client = new JerseyClientBuilder().build();

	@Before
	public void setup () {}

	@After
	public void tearDown () {}

	@Test
	public void testPutNewAlbumEnsureAlbumCanBeObtainedThroughGet () {

		// Make the PUT request
		Response response = this.makeRequest("/albums").put(Entity.json(album));
		
		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);
		
		// Extract the created album ID
		Long createdId = response.readEntity(Long.class);
		
		// Obtain the created album information through a GET
		Album createdAlbum = this.makeRequest("/albums/" + createdId).get(Album.class);

		// Ensure that the data for the created album is what is expected
		assertThat(createdAlbum.getId()).isEqualTo(createdId);
		assertThat(createdAlbum.getArtist()).isEqualTo(album.getArtist());
		assertThat(createdAlbum.getTitle()).isEqualTo(album.getTitle());
	}
	
	private Builder makeRequest (String uri) {
		return this.client.target(String.format("http://localhost:%d" + uri, RULE.getLocalPort())).request();
	}
}
