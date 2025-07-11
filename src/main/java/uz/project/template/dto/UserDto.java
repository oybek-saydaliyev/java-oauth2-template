package uz.project.template.dto;

import lombok.Getter;
import lombok.Setter;
import uz.project.template.base.BaseDto;
import uz.project.template.entity.AuthUserEntity;

/**
 * DTO for {@link uz.project.template.entity.AuthUserEntity}
 */
@Getter
@Setter
public class UserDto extends BaseDto{
    private Long id;
    private String email;
    private String fullName;
    private String password;


    public static AuthUserEntity toEntity(UserDto dto, AuthUserEntity entity) {
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setPassword(dto.getPassword());
        entity.setRole("USER");
        return entity;
    }
}