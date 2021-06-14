package domain.validators;

import domain.Categorie;

public class CategorieValidator implements Validator<Categorie> {
    @Override
    public void validate(Categorie categorie) throws ValidationException {
        String message = "";
        if (categorie.getIdCategorie() == null || categorie.getIdCategorie().equals("")) {
            message += "Id-ul categoriei este invalid.\n";
        }
        if (categorie.getDenumire() == null || categorie.getDenumire().equals("")) {
            message += "Denumire invalida.\n";
        }
        if (categorie.getDescriere() == null || categorie.getDescriere().equals("")) {
            message += "Descriere invalida.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
