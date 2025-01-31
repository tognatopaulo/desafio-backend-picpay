package tech.tognati.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.math.BigDecimal;

public class InsufficientBalanceException extends PicPayException {

    private BigDecimal value;

    public InsufficientBalanceException(BigDecimal value) {
        this.value = value;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Insufficient balance");
        pb.setDetail("You cannot transfer a value bigger than the balance of the wallet. Current balance: " + value + ".");

        return pb;
    }
}
