package domain.validators;

import domain.dtos.ResetareParolaClient;

public class ResetareParolaClientValidator implements Validator<ResetareParolaClient> {
    @Override
    public void validate(ResetareParolaClient entity) throws ValidationException {
        String message = "";
        if (entity.getParola_actuala() == null || entity.getParola_actuala().equals(""))
            message += "Token invalid!";
        if (entity.getParola_noua() == null || entity.getParola_noua().length() < 8)
            message += "Parola este prea scurta!";
        if (!entity.getParola_noua().equals(entity.getConfirmare_parola()))
            message += "Parolele nu coincid!";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
