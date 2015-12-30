package com.tpns.article.inverted.test;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

public class InvertedIndex {

	private static final String DOCUMENT_LOCATION = "file:///home/sergouniotis/Downloads/lucene/documents";

	public static void main(final String[] args) {

		// key = word, value = set of filenames containing that word
		final ST<String, SET<String>> st = new ST<String, SET<String>>();

		// create inverted index of all files
		for (final String filename : Paths.get(URI.create(DOCUMENT_LOCATION)).toFile().list()) {
			// System.out.println(filename);
			final In in = new In(DOCUMENT_LOCATION + File.separator + filename);
			while (!in.isEmpty()) {
				final String word = in.readString();
				if (!st.contains(word)) {
					st.put(word, new SET<String>());
				}
				final SET<String> set = st.get(word);
				set.add(filename);
			}
		}

		// read queries from standard input, one per line
		while (!StdIn.isEmpty()) {
			final String query = StdIn.readString();
			if (!st.contains(query)) {
				System.out.println("NOT FOUND");
			} else {
				final SET<String> set = st.get(query);
				for (final String filename : set) {
					System.out.print(filename + " ");
				}
				System.out.println();
			}
			System.out.println();
		}

	}
}