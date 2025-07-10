package uz.project.template.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApiResponse<E> {
    private E object;
    private String message;
    private boolean success;


    public ApiResponse(boolean success, E object, String message) {
        this.success = success;
        this.object = object;
        this.message = message;
    }


    public ApiResponse(boolean success, E object) {
        this.success = success;
        this.object = object;
    }

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static <E> ResponseEntity<ApiResponse<E>> controller(ApiResponse<E> apiResponse) {
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
