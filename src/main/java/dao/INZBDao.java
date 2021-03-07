package dao;

import java.util.List;

import entity.NZBEntity;

public interface INZBDao {
	List<NZBEntity> getAllNzb();
	NZBEntity getNzbById(long idNzb);
	void addNzb(NZBEntity nzbEntity);
	void updateNzb(NZBEntity nzbEntity);
	void deleteNzb(long idNzb);
	boolean nzbExists(String title, String name);
	List<NZBEntity> getNzbByName(String name);
	long getIdMax();
	List<NZBEntity> findNzbByNameAndTitle(String title, String name);
} 

