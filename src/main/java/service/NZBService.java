package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dao.INZBDao;
import entity.NZBEntity;

@Service("nzbService")
public class NZBService implements INZBService {

	@Autowired
	private INZBDao nzbDAO;
	
	@Override
	public NZBEntity getNzbById(long nzbId) {
		NZBEntity obj = nzbDAO.getNzbById(nzbId);
		return obj;
	}	
	@Override
	public List<NZBEntity> getAllNzb(){
		return nzbDAO.getAllNzb();
	}
	@Override
	public synchronized boolean addNzb(NZBEntity nzbEntity){
                //if (nzbDAO.nzbExists(nzbEntity.getTitle(), nzbEntity.getName())) {
                boolean test = nzbDAO.nzbExists(nzbEntity.getTitle(), nzbEntity.getName());
    	            return false;
                /*} else {
    	            nzbDAO.addNzb(nzbEntity);
    	            return true;
                }*/
	}
	@Override
	public void updateNzb(NZBEntity nzbEntity) {
		nzbDAO.updateNzb(nzbEntity);
	}
	@Override
	public void deleteNzb(long nzbId) {
		nzbDAO.deleteNzb(nzbId);
	}
	
	@Override
	public List<NZBEntity> getNzbByName(String name){
		return nzbDAO.getNzbByName(name);
	}
	
	@Override
	public long getIdMax() {
		return nzbDAO.getIdMax();
	}

	@Override
	public List<NZBEntity> getNzbByNameAndTitle(String title, String name){
		return nzbDAO.findNzbByNameAndTitle(title, name);
	}

}