package uz.project.template.dto;

import lombok.Getter;
import lombok.Setter;
import uz.project.template.base.BaseDto;
import uz.project.template.entity.AuthUserEntity;

@Getter
@Setter
public class UserResponseDto extends BaseDto {
    private Long id;
    private String username;

    public static UserResponseDto toDto(UserResponseDto dto, AuthUserEntity entity) {
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

}
