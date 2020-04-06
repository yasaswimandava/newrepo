package com.mindtree.music.client;

import com.mindtree.music.entity.Album;
import com.mindtree.music.entity.Song;
import com.mindtree.music.exception.serviceexception.MusicApplicationServiceException;
import com.mindtree.music.service.MusicService;
import com.mindtree.music.service.serviceimplementation.MusicServiceImplementation;
import com.mindtree.music.util.MusicInputUtility;
import java.util.*;

public class MusicApp {
	public static void main(String[] args) {
		boolean flag = true;
		MusicService service = new MusicServiceImplementation();
		do {
			System.out.println("****WELCOME TO MUSIC SYSTEM****");
			System.out.println("PRESS 1: TO ADD ARTIST");
			System.out.println("PRESS 2: ADD SONG AND ASIGN TO THE ARTIST");
			System.out.println("PRESS 3: ADD ALBUM AND ASSIGN SONGS TO IT");
			System.out.println("PRESS 4: GET ALL SONGS NAME GIVEN BY ARTIST NAME");
			System.out.println("PRESS 5: GET MOST EXPENSIVE ALBUM NAME BY GIVEN ARTIST NAME");
			System.out.println("PRESS 6: SORT ALL THE ALBUMS IN THE DESCENDING ORDER OF THEIR NAMES");
			System.out.println("PRESS 7: SORT ALL THE ALBUMS IN ASCENDING ORDER ACCORDING TO PRICE");
			System.out.println("PRESS 8: EXIT");
			System.out.println("**********************************************************");
			System.out.println("ENTER OPTIONS TO PERFORM");
			String option = MusicInputUtility.acceptString();
			switch (option) {
			case "1": {
				System.out.println("enter artistname");
				String artistname = MusicInputUtility.acceptString();
				System.out.println("enter gender");
				String gender = MusicInputUtility.acceptString();
				System.out.println("enter artist age");
				int age = MusicInputUtility.acceptInt();
				try {
					int result = service.addArtist(artistname, gender, age);
					if (result == 1) {
						System.out.println("successfully added");
					}
				} catch (MusicApplicationServiceException e) {
					System.out.println(e.getMessage());
				}
			}
				break;
			case "2": {
				System.out.println("enter artist name to add songs");
				String artname = MusicInputUtility.acceptString();
				System.out.println("enter song name");
				String songname = MusicInputUtility.acceptString();
				System.out.println("enter likes for the songs");
				int likes = MusicInputUtility.acceptInt();
				System.out.println("enter song language");
				String language = MusicInputUtility.acceptString();
				try {
					int result = service.addSong(artname, songname, likes, language);
					if (result == 1) {
						System.out.println("song successfully added");
					}
				} catch (MusicApplicationServiceException e) {
					System.out.println(e);
				}
			}
				break;
			case "3": {
				System.out.println("enter album name");
				String albumname = MusicInputUtility.acceptString();
				System.out.println("enter album price");
				double albumprice = MusicInputUtility.acceptDouble();
				System.out.println("enter album rating");
				double albumrating = MusicInputUtility.acceptDouble();
				System.out.println("songname");
				String songnam = MusicInputUtility.acceptString();
				try {
					int result = service.addAlbum(songnam, albumname, albumprice, albumrating);
					if (result == 1) {
						System.out.println("album added successfully");
					}
				} catch (MusicApplicationServiceException e) {
					System.out.println(e);
				}
			}
				break;
			case "4": {
				System.out.println("enter artist name to get songs");
				String artistname = MusicInputUtility.acceptString();
				try {
					Map<Song, String> result = service.getAllSongs(artistname);
					result.entrySet().forEach(map -> {
						System.out.println(map.getValue() + "\t" + map.getKey());
					});

				} catch (MusicApplicationServiceException e) {
					System.out.println(e.getMessage());
				}
			}
				break;
			case "5":
				System.out.println("enter artist name to get expensive album");
				String artistname = MusicInputUtility.acceptString();
				Set<Album> album;
				try {
					album = service.getExpensiveAlbum(artistname);
					for (Album album2 : album) {
						System.out.println("expensive album from this artist  : " + album2);
						System.out.println("*********************************************");
						break;
					}
				} catch (MusicApplicationServiceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "6":
				try {
					Set<Album> ab=service.sortAccordingToName();
					ab.forEach(System.out::println);
				} catch (MusicApplicationServiceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "7":
				try {
					Set<Album> tempalbum=service.sortAccordingToPrice();
					tempalbum.forEach(System.out::println);
				} catch (MusicApplicationServiceException e) {
				System.out.println(e.getMessage());
				}
				break;
			case"8":
				flag=false;
				System.out.println("****THANK YOU VISIT AGAIN*****");
				default:
					System.out.println("ENTER VALID INPUT TO PERFORM OPTIONS");
				
			}
		} while (flag);
	}
}
