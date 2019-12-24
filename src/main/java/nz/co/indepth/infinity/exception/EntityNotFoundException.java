package nz.co.indepth.infinity.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException {
    private Long id;
    private String errMeg;
    private HttpStatus status;

    public EntityNotFoundException() {
        super();
    }
}
