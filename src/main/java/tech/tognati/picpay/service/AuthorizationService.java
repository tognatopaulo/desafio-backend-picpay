package tech.tognati.picpay.service;

import org.springframework.stereotype.Service;
import tech.tognati.picpay.client.AuthorizationClient;
import tech.tognati.picpay.controller.TransferDTO;
import tech.tognati.picpay.entity.Transfer;
import tech.tognati.picpay.exception.PicPayException;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDTO transfer) {
        var response = authorizationClient.isAuthorized();
        if(response.getStatusCode().isError()) {
            throw new PicPayException();
        }
        return response.getBody().authorized();
    }
}
