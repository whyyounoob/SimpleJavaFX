package com.silverscreen.borodin.service;

import com.silverscreen.borodin.model.Human;

import java.util.List;

public interface HumanService {

  /**
   * This method for retrieving all humans from DB
   *
   * @return list of human
   */
  List<Human> getAll();

  /**
   * This method for adding new human to DB
   *
   * @param human - new human
   */
  void save(Human human);

  /**
   * This method for updating human in DB
   *
   * @param human - updated human
   */
  void update(Human human);

  /**
   * This method delete user by ID
   *
   * @param id - human ID to delete
   */
  void delete(int id);
}
