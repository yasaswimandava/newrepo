package com.mindtree.music.service.serviceimplementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.mindtree.music.dao.MusicDao;
import com.mindtree.music.dao.daoimplementation.MusicDaoImplementation;
import com.mindtree.music.entity.Album;
import com.mindtree.music.entity.Artist;
import com.mindtree.music.entity.Song;
import com.mindtree.music.exception.daoexception.MusicApplicationDaoException;
import com.mindtree.music.exception.serviceexception.MusicApplicationServiceException;
import com.mindtree.music.exception.serviceexception.customexception.AlbumIsEmptyException;
import com.mindtree.music.exception.serviceexception.customexception.ArtistNotFoundException;
import com.mindtree.music.exception.serviceexception.customexception.SongAlreadyExistInAlbum;
import com.mindtree.music.exception.serviceexception.customexception.SongDoesnotFoundException;
import com.mindtree.music.service.MusicService;

public class MusicServiceImplementation implements MusicService {
	MusicDao database = new MusicDaoImplementation();

	@Override
	public int addArtist(String artistname, String gender, int age) throws MusicApplicationServiceException {
		int result = 0;
		try {
			Artist artist = new Artist(artistname, gender, age);
			result = database.insertArtist(artist);
		} catch (MusicApplicationDaoException e) {
			throw new MusicApplicationServiceException(e);
		}
		return result;
	}

	@Override
	public int addSong(String artname, String songname, int likes, String language)
			throws MusicApplicationServiceException {
		int result = 0;
		boolean flag = false;
		try {
			flag = database.validateArtist(artname);
			if (flag == true) {
				int resultid = database.getArtistId(artname);
				Song song = new Song(songname, likes, language);
				result = database.insertSong(song, resultid);
			} else {
				throw new ArtistNotFoundException("artist doesnt found to add");
			}

		} catch (MusicApplicationDaoException e) {
			throw new MusicApplicationServiceException(e);
		}
		return result;
	}

	@Override
	public int addAlbum(String songnam, String albumname, double albumprice, double albumrating)
			throws MusicApplicationServiceException {
		int result = 0;
		boolean flag = false;
		try {
			flag = database.validateSong(songnam);
			if (flag == true) {
				int check = 1;
				int resultid = database.getSongId(songnam);
				check = database.checkSong(resultid);
				if (check < 1) {
					Album album = new Album(albumname, albumprice, albumrating);
					result = database.insertAlbum(album, resultid);
				} else {
					throw new SongAlreadyExistInAlbum("song already exists in this album");
				}
			} else {
				throw new SongDoesnotFoundException("song not found");
			}
		} catch (MusicApplicationDaoException e) {
			throw new MusicApplicationServiceException(e);
		}
		return result;
	}

	@Override
	public Map<Song, String> getAllSongs(String artistname) throws MusicApplicationServiceException {
		int resultid;
		boolean flag = false;
		try {
			flag = database.validateArtist(artistname);
			if (flag = true) {
				resultid = database.getArtistId(artistname);
				Map<Song, String> mapresult = new HashMap<Song, String>();
				Set<Song> results = database.getSongs(resultid);
				results.forEach(songs -> {
					mapresult.put(songs, artistname);
				});
				if (mapresult.isEmpty()) {
					throw new ArtistNotFoundException("artist not found");
			}
				return mapresult;
			} else {
				throw new ArtistNotFoundException("artist not found");
			}
		} catch (MusicApplicationDaoException  | ArtistNotFoundException e) {
			throw new MusicApplicationServiceException(e.getMessage());
		}

	}

	@Override
	public Set<Album> getExpensiveAlbum(String artistname) throws MusicApplicationServiceException {
		int artid;
		List<Integer> songid;
		Album album = null;
		Set<Album> album1 = new TreeSet<Album>();
		boolean flag = false;
		try {
			flag = database.validateArtist(artistname);
			if (flag = true) {
				artid = database.getArtistId(artistname);
				songid = database.getSongId2(artid);

				for (Integer i : songid) {
					album = database.getAlbumHighestPrice(i);
					album1.add(album);
				}
			} else {
				throw new ArtistNotFoundException("artist not found");
			}
		} catch (MusicApplicationDaoException e) {
			throw new MusicApplicationServiceException(e);

		}
		return album1;
	}

	@Override
	public Set<Album> sortAccordingToName() throws MusicApplicationServiceException{
		Set<Album> alb;
		try {
			alb = new TreeSet<Album>();
			alb=database.getAlbum();
			if(alb.isEmpty())
			{
				throw new AlbumIsEmptyException("album is empty");
			}
		List<Album> templist=new ArrayList<Album>(alb);
		Collections.sort(templist);
		alb=new LinkedHashSet<>(templist);
		} catch (Exception e) {
			throw new MusicApplicationServiceException(e);
		}
		
		return alb;
	}

	@Override
	public Set<Album> sortAccordingToPrice() throws MusicApplicationServiceException {
		Set<Album> tempresult=new TreeSet<Album>();
		try {
			tempresult=database.getAlbum();
			if(tempresult.isEmpty())
			{
				throw new AlbumIsEmptyException("album is empty");
			}
		} catch (MusicApplicationDaoException e) {
			throw new MusicApplicationServiceException(e);
		}
		return tempresult;
	}
}
