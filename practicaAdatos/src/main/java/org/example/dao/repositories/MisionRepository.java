package org.example.dao.repositories;

import org.example.dao.model.Mision;


import java.util.Optional;

public interface MisionRepository {
    Optional<Mision> findByName(String name);
}
