package uz.project.template.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
    // you can write your custom methods and implement
}
