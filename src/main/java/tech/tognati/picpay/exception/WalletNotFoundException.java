package tech.tognati.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.UUID;

public class WalletNotFoundException extends PicPayException {

    private Long id;

    public WalletNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Wallet not found");
        pb.setDetail("There is no wallet with the given id: " + id + ".");
        return pb;
    }
}
