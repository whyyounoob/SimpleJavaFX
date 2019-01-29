package com.silverscreen.borodin.dao;

import com.silverscreen.borodin.model.Human;

import java.util.List;

public interface HumanDao {
  List<Human> getAll();

  void save(Human human);

  void update(Human human);

  void delete(int id);
}
