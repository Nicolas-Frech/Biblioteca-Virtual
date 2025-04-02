package br.com.nicolasfrech.biblioteca_online.infra.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
}
