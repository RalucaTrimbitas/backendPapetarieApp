package domain.validators;

import domain.dtos.ResetPassword;

public class ResetPasswordValidator implements Validator<ResetPassword> {
    @Override
    public void validate(ResetPassword entity) throws ValidationException {
        String message = "";
        if (entity.getToken() == null || entity.getToken().equals(""))
            message += "Token invalid!";
        if (entity.getParola() == null || entity.getParola().length() < 8)
            message += "Parola este prea scurta!";
        if (entity.getParola() != null && !entity.getParola().equals(entity.getConfirmare_parola()))
            message += "Parolele nu coincid!";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
