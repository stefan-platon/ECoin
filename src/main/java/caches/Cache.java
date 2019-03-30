package caches;

import utils.FileController;

public abstract class Cache {

	protected static FileController fileController = new FileController();
	
	protected abstract void loadData();
	
}
