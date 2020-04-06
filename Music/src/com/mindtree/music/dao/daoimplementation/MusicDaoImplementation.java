package com.mindtree.music.dao.daoimplementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mindtree.music.dao.MusicDao;
import com.mindtree.music.entity.Album;
import com.mindtree.music.entity.Artist;
import com.mindtree.music.entity.Song;
import com.mindtree.music.exception.daoexception.MusicApplicationDaoException;
import com.mindtree.music.util.MusicJdbcConnectionUtility;

public class MusicDaoImplementation implements MusicDao {
	MusicJdbcConnectionUtility connect = new MusicJdbcConnectionUtility();

	@Override
	public int insertArtist(Artist artist) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		try {
			PreparedStatement call = con.prepareCall("{call add_artist(?,?,?)}");
			call.setString(1, artist.getArtistname());
			call.setString(2, artist.getGender());
			call.setInt(3, artist.getAge());
			call.executeUpdate();
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
		}
		return 1;
	}

	@Override
	public int insertSong(Song song, int resultid) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		try {
			call = con.prepareCall("{call add_song(?,?,?,?)}");
			call.setString(1, song.getSongname());
			call.setInt(2, song.getLikes());
			call.setString(3, song.getLanguage());
			call.setInt(4, resultid);
			call.executeUpdate();
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(call);
		}
		return 1;
	}

	@Override
	public int getArtistId(String artname) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		ResultSet rst = null;
		int id = 0;
		try {
			call = con.prepareCall("{call get_artistid(?)}");
			call.setString(1, artname);
			rst = call.executeQuery();
			while (rst.next()) {
				id = rst.getInt(1);
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call);
		}
		return id;
	}

	@Override
	public boolean validateArtist(String artname) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		ResultSet rst = null;
		try {
			call = con.prepareCall("{call validate_Artist(?)}");
			call.setString(1, artname);
			rst = call.executeQuery();
			while (rst.next()) {
				if (artname.equalsIgnoreCase(rst.getString(1)))
					;
				return true;
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call);
		}

		return false;
	}

	@Override
	public boolean validateSong(String songnam) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		ResultSet rst = null;
		try {
			call = con.prepareCall("{call validate_song(?)}");
			call.setString(1, songnam);
			rst = call.executeQuery();
			while (rst.next()) {
				if (songnam.equalsIgnoreCase(rst.getString(1)))
					;
				return true;
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call);
		}
		return false;
	}

	@Override
	public int getSongId(String songnam) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		ResultSet rst = null;
		int id = 0;
		try {
			call = con.prepareCall("{call getsong_id(?)}");
			call.setString(1, songnam);
			rst = call.executeQuery();
			while (rst.next()) {
				id = rst.getInt(1);
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call);
		}
		return id;

	}

	@Override
	public int insertAlbum(Album album, int resultid) throws MusicApplicationDaoException {

		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call = null;
		try {
			call = con.prepareCall("{call add_album(?,?,?,?)}");
			call.setString(1, album.getAlbumname());
			call.setDouble(2, album.getAlbumprice());
			call.setDouble(3, album.getRating());
			call.setInt(4, resultid);
			call.executeUpdate();
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		} finally {
			connect.closeResource(con);
			connect.closeResource(call);
		}

		return 1;
	}

	@Override
	public Set<Song> getSongs(int resultid) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call1 = null;
		ResultSet rst = null;
		Set<Song> result = new HashSet<Song>();
		try {

			call1 = con.prepareCall("{call get_allsongs(?)}");
			call1.setInt(1, resultid);
			rst = call1.executeQuery();
			while (rst.next()) {
				Song son = new Song(rst.getInt(1), rst.getString(2), rst.getInt(3), rst.getString(4));
				result.add(son);
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e.getMessage());
		}
		finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call1);
		}
		return result;
	}

	@Override
	public List<Integer> getSongId2(int artid) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call1 = null;
		ResultSet rst = null;
		List<Integer> id = new ArrayList<Integer>();
		try {
			call1 = con.prepareCall("{call getsong_id2(?)}");
			call1.setInt(1, artid);
			rst = call1.executeQuery();
			while (rst.next()) {
				id.add(rst.getInt(1));
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		}
		finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call1);
		}
		return id;

	}

	@Override
	public Set<Album> getAlbum() throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call1 = null;
		ResultSet rst = null;
		Set<Album> albums = new TreeSet<Album>();
		try {
			call1 = con.prepareCall("{call get_album()}");
			rst = call1.executeQuery();
			while (rst.next()) {
				Album album = new Album(rst.getInt(1), rst.getString(2), rst.getDouble(3), rst.getDouble(4));
				albums.add(album);
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		}
		return albums;
	}

	@Override
	public int checkSong(int resultid) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call1 = null;
		ResultSet rst = null;
		int temp = 0;
		try {
			call1 = con.prepareCall("{call check_album}");
			rst = call1.executeQuery();
			while (rst.next()) {
				temp = rst.getInt(1);
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		}finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call1);
		}

		return temp;
	}

	@Override
	public Album getAlbumHighestPrice(Integer id) throws MusicApplicationDaoException {
		Connection con = MusicJdbcConnectionUtility.getConnection();
		CallableStatement call1 = null;
		ResultSet rst = null;
		Album result = new Album();
		try {
			call1 = con.prepareCall("{call get_highestalbum(?)}");
			call1.setInt(1, id);
			rst = call1.executeQuery();
			while (rst.next()) {
				result = new Album(rst.getInt(6), rst.getString(3), rst.getDouble(4), rst.getDouble(5));
			}
		} catch (SQLException e) {
			throw new MusicApplicationDaoException(e);
		}
		finally {
			connect.closeResource(con);
			connect.closeResource(rst);
			connect.closeResource(call1);
		}
		return result;
	}

}
