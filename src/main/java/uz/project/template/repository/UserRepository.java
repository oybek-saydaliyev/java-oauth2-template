package uz.project.template.repository;

import uz.project.template.base.BaseRepository;
import uz.project.template.entity.AuthUserEntity;

import java.util.Optional;


public interface UserRepository extends BaseRepository<AuthUserEntity> {
    Optional<AuthUserEntity> findByUsername(String username);
}