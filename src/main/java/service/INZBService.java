package service;

import java.util.List;

import entity.NZBEntity;

public interface INZBService {
	     List<NZBEntity> getAllNzb();
	     NZBEntity getNzbById(long articleId);
	     boolean addNzb(NZBEntity article);
	     void updateNzb(NZBEntity article);
	     void deleteNzb(long articleId);
	     List<NZBEntity> getNzbByName(String name);
	     List<NZBEntity> getNzbByNameAndTitle(String title, String name);
	     long getIdMax();
}
