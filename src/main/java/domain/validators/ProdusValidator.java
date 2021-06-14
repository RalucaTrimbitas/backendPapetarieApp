package domain.validators;

import domain.Produs;

public class ProdusValidator implements Validator<Produs> {

    @Override
    public void validate(Produs produs) throws ValidationException {
        String message = "";
        if (produs.getCodDeBare() == null || produs.getCodDeBare().equals("")) {
            message += "Cod de bare invalid.\n";
        }
        if (produs.getNumeUtilizatorAdministrator() == null || produs.getNumeUtilizatorAdministrator().equals("")) {
            message += "Numele de utilizator al administratorului este invalid!\n";
        }
        if (produs.getIdCategorieProdus() == null || produs.getIdCategorieProdus().equals("")) {
            message += "Id-ul categoriei nu poate fi vid.\n";
        }
        if (produs.getDenumire() == null || produs.getDenumire().equals("")) {
            message += "Denumirea pruduslui nu poate fi vida.\n";
        }
        if (produs.getPret() == null || produs.getPret() == 0 || produs.getPret() < 0) {
            message += "Pretul produsului este incorect.\n";
        }

        if (produs.getDescriere() == null || produs.getDescriere().equals("")) {
            message += "Descrierea nu poate fi vida.\n";
        }

        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
