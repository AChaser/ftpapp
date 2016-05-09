package org.weiwei.dao;

import org.weiwei.model.Task;

public interface TaskDao {

	public void insertTask(Task task);
	
	public void deleteTask(Task task);
	
	public void updateTask(Task task);
	
	public Task getTask();
	
	public boolean isExist(Task task);
	
}
