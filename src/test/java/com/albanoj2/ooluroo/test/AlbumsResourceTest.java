package com.albanoj2.ooluroo.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import com.albanoj2.ooluroo.OolurooChallengeApplication;
import com.albanoj2.ooluroo.OolurooChallengeConfiguration;
import com.albanoj2.ooluroo.domain.Album;
import com.albanoj2.ooluroo.domain.Song;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * TODO Class documentation
 *
 * @author Justin Albano
 */
public class AlbumsResourceTest {

	private Client client = new JerseyClientBuilder().build();

	@ClassRule
	public static final DropwizardAppRule<OolurooChallengeConfiguration> RULE = new DropwizardAppRule<OolurooChallengeConfiguration>(
		OolurooChallengeApplication.class, ResourceHelpers.resourceFilePath("testing-configuration.yaml"));

	@Test
	public void testPutNewAlbumEnsureAlbumCanBeObtainedThroughGet () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response response = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdId = response.readEntity(Long.class);

		// Obtain the created album information through a GET
		Album createdAlbum = this.get("/albums/" + createdId, Album.class);

		// Ensure that the data for the created album is what is expected
		assertThat(createdAlbum.getId()).isEqualTo(createdId);
		assertThat(createdAlbum.getArtist()).isEqualTo(newAlbum.getArtist());
		assertThat(createdAlbum.getTitle()).isEqualTo(newAlbum.getTitle());
	}

	@Test
	public void testPutNewAlbumThenDeleteEnsureAlbumCannotBeObtainedThroughGet () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response response = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdId = response.readEntity(Long.class);

		// Delete the newly created album
		this.delete("/albums/" + createdId);

		// Obtain the created album information through a GET
		Album createdAlbum = this.get("/albums/" + createdId, Album.class);

		// Ensure that the newly created album cannot be found
		assertThat(createdAlbum).isNull();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSearchWithMatchingPatternEnsureAlbumObtainedThroughGet () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response response = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdId = response.readEntity(Long.class);

		// Obtain the created album information through a GET (filter with a
		// part of the title to ensure that a match is found)
		List<Map<String, Object>> foundAlbums = (List<Map<String, Object>>) this.get("/albums?filter=" + newAlbum.getTitle().substring(5, 10),
			List.class);

		// Ensure a matching album was found
		assertThat(foundAlbums.size()).isEqualTo(1);

		// Ensure that the data for the created album is what is expected
		assertThat(this.getId(foundAlbums.get(0))).isEqualTo(createdId);
		assertThat(foundAlbums.get(0).get("artist")).isEqualTo(newAlbum.getArtist());
		assertThat(foundAlbums.get(0).get("title")).isEqualTo(newAlbum.getTitle());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSearchWithNotMatchingPatternEnsureAlbumNotObtainedThroughGet () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response response = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);

		// Obtain the created album information through a GET (filter with some
		// random UUID)
		List<Map<String, Object>> foundAlbums = (List<Map<String, Object>>) this.get("/albums?filter=" + UUID.randomUUID(), List.class);

		// Ensure a matching album was found
		assertThat(foundAlbums.size()).isEqualTo(0);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumEnsureAlbumNotObtainedThroughGetOfAllAlbums () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response response = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(response.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdId = response.readEntity(Long.class);

		// Obtain the created album information through a GET
		List<Map<String, Object>> foundAlbums = (List<Map<String, Object>>) this.get("/albums", List.class);

		System.out.println(foundAlbums);

		// Filter the list, looking for an album entry with matching ID, and
		// ensure that only one such entry is found
		assertThat(foundAlbums.stream().filter(a -> this.getId(a).equals(createdId)).collect(Collectors.toList()).size()).isEqualTo(1);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewSongInAlbumEnsureSongObtainedThroughGetOfAllSongsInAlbum () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response createAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(createAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = createAlbumResponse.readEntity(Long.class);

		// Add a song to the album through a PUT
		Response createSongResponse = this.put("/albums/" + createdAlbumId + "/songs", this.generateNewSong());

		// Ensure song was successfully added
		assertThat(createSongResponse.getStatus()).isEqualTo(200);

		// Extract the created song ID
		Long createdSongId = createSongResponse.readEntity(Long.class);

		// Obtain a list of the songs in the album
		List<Map<String, Object>> foundAlbums = (List<Map<String, Object>>) this.get("/albums/" + createdAlbumId + "/songs", List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that only one such entry is found
		assertThat(foundAlbums.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(1);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewSongInAlbumThenDeleteEnsureSongNotObtainedThroughGetOfAllSongsInAlbum () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response createAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(createAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = createAlbumResponse.readEntity(Long.class);

		// Add a song to the album through a PUT
		Response createSongResponse = this.put("/albums/" + createdAlbumId + "/songs", this.generateNewSong());

		// Ensure song was successfully added
		assertThat(createSongResponse.getStatus()).isEqualTo(200);

		// Extract the created song ID
		Long createdSongId = createSongResponse.readEntity(Long.class);

		// Delete the song
		this.delete("/songs/" + createdSongId);

		// Obtain a list of the songs in the album
		List<Map<String, Object>> foundAlbums = (List<Map<String, Object>>) this.get("/albums/" + createdAlbumId + "/songs", List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that only one such entry is found
		assertThat(foundAlbums.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(0);
	}

	@Test
	public void testPutUpdateAlbumEnsureValueChangedWithGet () {

		// Generate a new album to create through API call
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdId = creationResponse.readEntity(Long.class);

		// Obtain the created album information through a GET
		Album createdAlbum = (Album) this.get("/albums/" + createdId, Album.class);

		// Alter the title of the album
		createdAlbum.setTitle("__test__");

		// Persist the change
		Response updateResponse = this.put("/albums/" + createdId, createdAlbum);

		// Ensure the update was successful
		assertThat(updateResponse.getStatus()).isEqualTo(204);

		// Obtain the updated album information through a GET
		Album updatedAlbum = (Album) this.get("/albums/" + createdId, Album.class);

		// Ensure that the data for the updated album is what is expected
		assertThat(updatedAlbum.getId()).isEqualTo(createdId);
		assertThat(updatedAlbum.getTitle()).isEqualTo("__test__");
		assertThat(updatedAlbum.getArtist()).isEqualTo(createdAlbum.getArtist());
	}

	@Test
	public void testPutUpdateSongEnsureValueChangedWithGet () {

		// Generate a new song and album to create through API call
		Song newSong = this.generateNewSong();
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = creationAlbumResponse.readEntity(Long.class);

		// Create the new song
		Response creationSongResponse = this.put("/albums/" + createdAlbumId + "/songs", newSong);

		// Extract the created album ID
		Long createdSongId = creationSongResponse.readEntity(Long.class);

		// Ensure the PUT was successful
		assertThat(creationSongResponse.getStatus()).isEqualTo(200);

		// Obtain the created song information through a GET
		Song createdSong = (Song) this.get("/songs/" + createdSongId, Song.class);

		// Alter the title of the song
		createdSong.setTitle("__test__");

		// Persist the change
		Response updateResponse = this.put("/songs/" + createdSongId, createdSong);

		// Ensure the update was successful
		assertThat(updateResponse.getStatus()).isEqualTo(204);

		// Obtain the updated song information through a GET
		Song updatedSong = (Song) this.get("/songs/" + createdSongId, Song.class);

		// Ensure that the data for the updated song is what is expected
		assertThat(updatedSong.getId()).isEqualTo(createdSongId);
		assertThat(updatedSong.getTitle()).isEqualTo("__test__");
		assertThat(updatedSong.getLengthInSeconds()).isEqualTo(createdSong.getLengthInSeconds());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSongEnsureSongObtainedThroughGetOfAllSongs () {

		// Generate a new song and album to create through API call
		Song newSong = this.generateNewSong();
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = creationAlbumResponse.readEntity(Long.class);

		// Create the new song
		Response creationSongResponse = this.put("/albums/" + createdAlbumId + "/songs", newSong);

		// Extract the created album ID
		Long createdSongId = creationSongResponse.readEntity(Long.class);

		// Ensure the PUT was successful
		assertThat(creationSongResponse.getStatus()).isEqualTo(200);

		// Obtain the created song information through a GET
		List<Map<String, Object>> foundSongs = (List<Map<String, Object>>) this.get("/songs", List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that only one such entry is found
		assertThat(foundSongs.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(1);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSongDeleteSongEnsureSongNotObtainedThroughGetOfAllSongs () {

		// Generate a new song and album to create through API call
		Song newSong = this.generateNewSong();
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = creationAlbumResponse.readEntity(Long.class);

		// Create the new song
		Response creationSongResponse = this.put("/albums/" + createdAlbumId + "/songs", newSong);

		// Extract the created album ID
		Long createdSongId = creationSongResponse.readEntity(Long.class);

		// Ensure the PUT was successful
		assertThat(creationSongResponse.getStatus()).isEqualTo(200);
		
		// Delete the song
		this.delete("/songs/" + createdSongId);

		// Obtain the created song information through a GET
		List<Map<String, Object>> foundSongs = (List<Map<String, Object>>) this.get("/songs", List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that not such entry is found
		assertThat(foundSongs.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(0);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSongEnsureSongObtainedThroughGetOfAllSongsWithMatchingPattern () {

		// Generate a new song and album to create through API call
		Song newSong = this.generateNewSong();
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = creationAlbumResponse.readEntity(Long.class);

		// Create the new song
		Response creationSongResponse = this.put("/albums/" + createdAlbumId + "/songs", newSong);

		// Extract the created album ID
		Long createdSongId = creationSongResponse.readEntity(Long.class);

		// Ensure the PUT was successful
		assertThat(creationSongResponse.getStatus()).isEqualTo(200);

		// Obtain the created song information through a GET
		List<Map<String, Object>> foundSongs = (List<Map<String, Object>>) this.get("/songs?filter=" + newSong.getTitle().substring(5, 10), List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that only one such entry is found
		assertThat(foundSongs.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(1);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPutNewAlbumSongEnsureSongNotObtainedThroughGetOfAllSongsWithNonMatchingPattern () {

		// Generate a new song and album to create through API call
		Song newSong = this.generateNewSong();
		Album newAlbum = this.generateNewAlbum();

		// Make the PUT request
		Response creationAlbumResponse = this.put("/albums", newAlbum);

		// Ensure the PUT was successful
		assertThat(creationAlbumResponse.getStatus()).isEqualTo(200);

		// Extract the created album ID
		Long createdAlbumId = creationAlbumResponse.readEntity(Long.class);

		// Create the new song
		Response creationSongResponse = this.put("/albums/" + createdAlbumId + "/songs", newSong);

		// Extract the created album ID
		Long createdSongId = creationSongResponse.readEntity(Long.class);

		// Ensure the PUT was successful
		assertThat(creationSongResponse.getStatus()).isEqualTo(200);

		// Obtain the created song information through a GET
		List<Map<String, Object>> foundSongs = (List<Map<String, Object>>) this.get("/songs?filter=" + UUID.randomUUID(), List.class);

		// Filter the list, looking for an song entry with matching ID, and
		// ensure that no such entry is found
		assertThat(foundSongs.stream().filter(a -> this.getId(a).equals(createdSongId)).collect(Collectors.toList()).size()).isEqualTo(0);
	}

	private Builder makeRequest (String uri) {
		return this.client.target(String.format("http://localhost:%d" + uri, RULE.getLocalPort())).request();
	}

	private Album generateNewAlbum () {
		return new Album(UUID.randomUUID().toString(), UUID.randomUUID().toString());
	}

	private Song generateNewSong () {
		return new Song(UUID.randomUUID().toString(), 100);
	}

	private <T> T get (String uri, Class<T> clazz) {
		return this.makeRequest(uri).get(clazz);
	}

	private <T> Response put (String uri, T entity) {
		return this.makeRequest(uri).put(Entity.json(entity));
	}

	private <T> void delete (String uri) {
		this.makeRequest(uri).delete();
	}

	private Long getId (Map<String, Object> jsonAlbum) {
		return new Long((int) jsonAlbum.get("id"));
	}
}
