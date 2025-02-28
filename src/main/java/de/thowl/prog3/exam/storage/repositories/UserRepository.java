package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findUserById(long id);

    public Optional<User> findUserByName(String username);

    public Optional<User> findUserByEmail(String email);

}
