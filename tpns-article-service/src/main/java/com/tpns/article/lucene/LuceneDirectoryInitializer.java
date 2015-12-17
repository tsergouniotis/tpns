package com.tpns.article.lucene;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NativeFSLockFactory;
import org.apache.lucene.store.SimpleFSDirectory;

import com.tpns.article.conf.ApplicationParameter;

@Named
@ApplicationScoped
public class LuceneDirectoryInitializer {

	@Inject
	@ApplicationParameter(key = "article.lucene.directory")
	private String indexLocation;

	@Produces
	@ApplicationScoped
	public Directory create() throws IOException {
		File directoryAsFileObject = new File(indexLocation);
		return new SimpleFSDirectory(directoryAsFileObject.toPath(), NativeFSLockFactory.INSTANCE);
	}

}
