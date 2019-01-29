package com.silverscreen.borodin.service;

import com.silverscreen.borodin.model.Human;

import java.util.List;

public interface HumanService {
  List<Human> getAll();
  void save(Human human);
  void update(Human human);
  void delete(int id);

}
