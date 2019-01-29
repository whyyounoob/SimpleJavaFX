package com.silverscreen.borodin.service;

import com.silverscreen.borodin.dao.CrudDao;
import com.silverscreen.borodin.dao.HumanDaoImpl;
import com.silverscreen.borodin.model.Human;

import java.util.List;

public class HumanServiceImpl implements HumanService {

  private CrudDao<Human> humanDao = HumanDaoImpl.getInstance();

  @Override
  public List<Human> getAll() {
    return humanDao.getAll();
  }

  @Override
  public void save(Human human) {
    humanDao.save(human);
  }

  @Override
  public void update(Human human) {
    humanDao.update(human);
  }

  @Override
  public void delete(int id) {
    humanDao.delete(id);
  }
}
