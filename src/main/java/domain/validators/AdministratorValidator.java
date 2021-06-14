package domain.validators;

import domain.Administrator;

public class AdministratorValidator implements Validator<Administrator> {

    @Override
    public void validate(Administrator administrator) throws ValidationException {
        String message = "";
        if (administrator.getNumeUtilizator() == null || administrator.getNumeUtilizator().equals("")) {
            message += "Nume de utilizator invalid.\n";
        }
        if (administrator.getParola() == null || administrator.getParola().equals("")) {
            message += "Parola invalida.\n";
        }
        if (administrator.getPrenume() == null || administrator.getPrenume().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (administrator.getNume() == null || administrator.getNume().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }

    }
}
