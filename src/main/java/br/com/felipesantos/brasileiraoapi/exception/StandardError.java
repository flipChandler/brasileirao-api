package br.com.felipesantos.brasileiraoapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(HttpStatus httpStatus, String message, String path) {
        super();
        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.timestamp = new Date().getTime();
        this.message = message;
        this.path = path;
    }
}
