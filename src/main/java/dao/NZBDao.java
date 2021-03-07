package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.NZBEntity;

@Repository
public class NZBDao implements INZBDao {
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public NZBEntity getNzbById(long nzbId) {
		return entityManager.find(NZBEntity.class, nzbId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<NZBEntity> getAllNzb() {
		String hql = "FROM NZBEntity as atcl ORDER BY atcl.nzbId";
		return (List<NZBEntity>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void addNzb(NZBEntity nzbEntity) {
		entityManager.persist(nzbEntity);
		entityManager.flush();
		entityManager.refresh(nzbEntity);

	}
	@Override
	public void updateNzb(NZBEntity nzbEntity) {
		NZBEntity nzbTemp = getNzbById(nzbEntity.getId_nzb());
		nzbTemp.setTitle(nzbEntity.getTitle());
		nzbTemp.setName(nzbEntity.getName());
		entityManager.flush();
	}
	@Override
	public void deleteNzb(long nzbId) {
		entityManager.remove(getNzbById(nzbId));
	}
	
	@Override
	public long getIdMax() {
		TypedQuery<NZBEntity> query = entityManager.createQuery("Select Max(id_nzb) from articles",NZBEntity.class);
		return query.getSingleResult().getId_nzb();
	}
	
	@Override
	public boolean nzbExists(String title, String name) {
		/*String hql = "FROM NZBEntity as atcl WHERE atcl.title = ? and atcl.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, title)
				.setParameter(2, name).getResultList().size();
		return count > 0 ? true : false;*/
		TypedQuery<NZBEntity> query = entityManager.createQuery("select e from Articles e where e.title = :title and e.name = :name",
			    NZBEntity.class);
		
		query.setParameter("name", name);
		
		int count = query.setParameter("title", title).getResultList().size();
		return count > 0 ? true : false;
	}

	@Override
	public List<NZBEntity> getNzbByName(String name){
		
		
		return entityManager.createQuery("select e from Articles e where e.name = :name",
			    NZBEntity.class).setParameter("name", name).getResultList();
	}
	
	@Override
	public List<NZBEntity> findNzbByNameAndTitle(String title, String name){
		TypedQuery<NZBEntity> query = entityManager.createQuery("Select a from articles a where a.title = :title and a.name = :name",NZBEntity.class);
		query.setParameter("title", title);
		query.setParameter("name", name);
		return query.getResultList();
	}
} 
