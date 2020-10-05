package com.nix.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextFromResourceReader {

	public String readTextFromResource(String pathToResource) {
		if (pathToResource == null) {
			throw new NullPointerException();
		}
		String query = "";
		try (InputStream in = TextFromResourceReader.class.getClassLoader().getResourceAsStream(pathToResource)) {
			assert in != null;
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
				String s;
				while ((s = reader.readLine()) != null) {
					query = query.concat(s + "\n");
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return query;

	}

}
