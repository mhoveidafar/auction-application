package com.mhoveidafar.demo.repository;

import com.mhoveidafar.demo.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
}
