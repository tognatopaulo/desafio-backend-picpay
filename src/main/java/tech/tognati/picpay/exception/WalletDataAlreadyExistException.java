package tech.tognati.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletDataAlreadyExistException extends PicPayException {

    private String detail;

    public WalletDataAlreadyExistException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Wallet data already exist.");
        pb.setDetail(detail);
        return pb;
    }
}
