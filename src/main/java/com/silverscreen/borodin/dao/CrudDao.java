package com.silverscreen.borodin.dao;

import java.util.List;

public interface CrudDao<T> {
  List<T> getAll();

  void save(T object);

  void update(T object);

  void delete(int id);
}
