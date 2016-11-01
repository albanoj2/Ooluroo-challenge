# Ooluroo Challenge
This repository contains the submission for Justin Albano to the Ooluroo programming challenge presented by Corey Wendling on Nov. 1, 2016. This submission contains all source code, artifacts and documents, and schemas required to comprehensively solve the presented problem.

## RESTful Endpoints
| Method        | URI           | Description  |
|:-------------|:-------------|:-----|
| `GET` | `/albums` | Returns a list of all existing albums |
| `GET` | `/albums?filter={pattern}` | Returns a list of all existing albums whose title conform to the supplied pattern |
| `PUT` | `/albums` | Creates a new album with the supplied characteristics; returns the ID of the newly created album |
| `GET` | `/albums/{id}` | Returns a representation of the album corresponding to the supplied ID |
| `PUT` | `/albums/{id}` | Updates the data associated with the album corresponding to the supplied ID |
| `DELETE` | `/albums/{id}` | Deletes the album corresponding to the supplied ID |
| `GET` | `/albums/{album_id}/songs` | Returns a list of all songs associated with the album corresponding to the supplied album ID |
| `PUT` | `/albums/{album_id}/songs` | Creates a new song with the supplied characteristics and adds it to the album correspond to the supplied album ID; returns the ID of the newly created album; the album ID of the supplied song is ignored (the `{album_id}` is used instead) |
| `GET` | `/songs` | Returns a list of all existing songs |
| `GET` | `/songs?filter={pattern}` | Returns a list of all existing songs whose title conforms to the supplied pattern |
| `GET` | `/songs/{id}` | Returns a representation of the song corresponding to the supplied ID |
| `PUT` | `/songs/{id}` | Updates the data associated with the song corresponding to the supplied ID |
| `DELETE` | `/songs/{song_id}` | Deletes the song corresponding to the supplied ID |

## Resources
 - **Albums**
   - `GET`: `getAlbums (): List<Albums>`
   - `GET`: `getAlbums (pattern: String): List<Albums>`
   - `PUT`: `addAlbum (album: Album): long`
   - `GET`: `getAlbum (id: long): Album`
   - `PUT`: `updateAlbum (id: long, album: Album): void`
   - `DELETE`: `removeAlbum (id: long): void`
   - `GET`: `getSongs (albumId: long): List<Songs>`
   - `PUT`: `addSong (albumId: long, song: Song): void`
 - **Songs**
   - `GET`: `getSongs (): List<Song>`
   - `GET`: `getSongs (pattern: String): List<Song>`
   - `GET`: `getSong (id: long): Song`
   - `PUT`: `updateSong (id: long, song: Song): void`
   - `DELETE`: `removeSong (id: long): void`
