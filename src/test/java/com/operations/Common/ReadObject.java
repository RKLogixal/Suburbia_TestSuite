package com.operations.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObject {

	Properties p1 = new Properties();
	Properties p2 = new Properties();
	Properties p3 = new Properties();

	public Properties getObjectRepository() throws IOException{
		//Read object repository file
		InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"\\resources\\Suburbia.properties"));
		//load all objects
		p1.load(stream);
		return p1;
	}
	public Properties getObjectRepositoryBBBCA() throws IOException{
		//Read object repository file
		InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"\\resources\\BBBCAObjects.properties"));
		//load all objects
		p2.load(stream);
		return p2;
	}
	public Properties getObjectRepositoryBABY() throws IOException{
		//Read object repository file
		InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"\\resources\\BABYObjects.properties"));
		//load all objects
		p3.load(stream);
		return p3;
	}

}
