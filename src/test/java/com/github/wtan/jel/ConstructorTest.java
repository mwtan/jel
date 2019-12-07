package com.github.wtan.jel;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.lang.reflect.Constructor;

import org.apache.commons.lang.reflect.ConstructorUtils;

public class ConstructorTest {

	public static void main(String[] mainargs) {
		try {
			Class<?> newclass = null;
			String classname = "java.io.BufferedReader";
			try {
				newclass = Class.forName(classname);
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Class<?>[] parmtypes = new Class[1];
			Object[] args = new Object[1];

			java.io.FileReader filereader = new java.io.FileReader("swl_events.xml");
			Class filereaderclass = filereader.getClass();
			parmtypes[0] = filereaderclass;
			args[0] = filereader;
			
			Class<?> readerclass = Class.forName("java.io.Reader");

			Constructor<?> cons = null;
			try {
				//cons = ConstructorUtils.getAccessibleConstructor(newclass, readerclass);
				cons = ConstructorUtils.getMatchingAccessibleConstructor(newclass, parmtypes);
				cons = newclass.getConstructor(parmtypes);
			}
			catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

//			Object reader = readerclass.cast(filereader);
//			System.out.println("Class:" + reader.getClass());
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
