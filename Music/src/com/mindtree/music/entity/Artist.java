package com.mindtree.music.entity;

public class Artist {
	private int artistid;
	private String artistname;
	private String gender;
	private int age;
	
	public Artist() {
	}

	public Artist(String artistname, String gender, int age) {
		this.artistname = artistname;
		this.gender = gender;
		this.age = age;
	}

	public Artist(int artistid, String artistname, String gender, int age) {
		this.artistid = artistid;
		this.artistname = artistname;
		this.gender = gender;
		this.age = age;
	}

	public int getArtistid() {
		return artistid;
	}

	public void setArtistid(int artistid) {
		this.artistid = artistid;
	}

	public String getArtistname() {
		return artistname;
	}

	public void setArtistname(String artistname) {
		this.artistname = artistname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + artistid;
		result = prime * result + ((artistname == null) ? 0 : artistname.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		if (age != other.age)
			return false;
		if (artistid != other.artistid)
			return false;
		if (artistname == null) {
			if (other.artistname != null)
				return false;
		} else if (!artistname.equals(other.artistname))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artist [artistid=" + artistid + ", artistname=" + artistname + ", gender=" + gender + ", age=" + age
				+ "]";
	}

}
