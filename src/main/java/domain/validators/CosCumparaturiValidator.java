package domain.validators;

import domain.CosCumparaturi;

public class CosCumparaturiValidator implements Validator<CosCumparaturi> {
    @Override
    public void validate(CosCumparaturi entity) throws ValidationException {
        String message = "";
        if (entity.getIdCosCumparaturi() < 0)
            message += "Id invalid.";
        if (entity.getNumeUtilizatorClient() == null || entity.getNumeUtilizatorClient().equals(""))
            message += "Numele de utilizator al clientului este invalid.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
