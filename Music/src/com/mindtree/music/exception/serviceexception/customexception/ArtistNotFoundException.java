package com.mindtree.music.exception.serviceexception.customexception;

import com.mindtree.music.exception.serviceexception.MusicApplicationServiceException;

public class ArtistNotFoundException extends MusicApplicationServiceException {

	public ArtistNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public ArtistNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ArtistNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ArtistNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ArtistNotFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
