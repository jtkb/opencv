package org.opencv.osgi;

import java.util.logging.Logger;
import java.util.logging.Level;
/**
* This class is intended to provide a convenient way to load OpenCV's native library
* from the Java bundle.
* If blueprint is enabled in the OSGi container this class will be instantiated automatically
* as a bean an the static initialiser load the library.
*/
public class OpenCVNativeLoader implements OpenCVInterface {

	public void init()
	{
		System.loadLibrary("opencv_java320");
		Logger.getLogger("org.opencv.osgi").log(Level.INFO,"Successfully loaded OpenCV native library.");
	}
}
