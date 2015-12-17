package com.tpns.article.inverted.test;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

public class InvertedIndex {

	private static final String DOCUMENT_LOCATION = "file:///home/sergouniotis/Downloads/lucene/documents";

	public static void main(String[] args) {

		// key = word, value = set of filenames containing that word
		ST<String, SET<String>> st = new ST<String, SET<String>>();

		// create inverted index of all files
		for (String filename : Paths.get(URI.create(DOCUMENT_LOCATION)).toFile().list()) {
			// System.out.println(filename);
			In in = new In(DOCUMENT_LOCATION + File.separator + filename);
			while (!in.isEmpty()) {
				String word = in.readString();
				if (!st.contains(word))
					st.put(word, new SET<String>());
				SET<String> set = st.get(word);
				set.add(filename);
			}
		}

		// read queries from standard input, one per line
		while (!StdIn.isEmpty()) {
			String query = StdIn.readString();
			if (!st.contains(query))
				System.out.println("NOT FOUND");
			else {
				SET<String> set = st.get(query);
				for (String filename : set) {
					System.out.print(filename + " ");
				}
				System.out.println();
			}
			System.out.println();
		}

	}
}