package caches;

import utils.FileController;

public abstract class Cache {

	protected FileController fileController = new FileController();
	
	protected abstract void loadData();
	
}
