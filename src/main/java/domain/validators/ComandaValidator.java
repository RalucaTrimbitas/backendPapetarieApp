package domain.validators;

import domain.Comanda;

public class ComandaValidator implements Validator<Comanda> {
    @Override
    public void validate(Comanda entity) throws ValidationException {
        String message = "";
        if (entity.getNumarComanda() < 0)
            message += "Numar de comanda invalid.";
        if (entity.getNumeUtilizatorClient() == null || entity.getNumeUtilizatorClient().equals(""))
            message += "Numele de utilizator al clientului este invalid.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
