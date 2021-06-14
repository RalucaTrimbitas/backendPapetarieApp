package domain.validators;

import domain.Client;

import java.util.regex.Pattern;

public class ClientValidator implements Validator<Client> {
    public static boolean mailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    public void validate(Client client) throws ValidationException {
        String message = "";
        if (client.getNumeUtilizator() == null || client.getNumeUtilizator().equals("")) {
            message += "Nume de utilizator invalid.\n";
        }
        if (client.getParola() == null || client.getParola().equals("")) {
            message += "Parola invalida.\n";
        }
        if (client.getPrenume() == null || client.getPrenume().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (client.getNume() == null || client.getNume().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (client.getEmail() != null && !mailValidation(client.getEmail())) {
            message += "E-mail invalid.\n";
        }
        if (client.getNumarTelefon() != null && (client.getNumarTelefon().length() != 10)) {
            message += "Numar de telefon invalid.\n";
        }
        if (client.getTip() != null && !client.getTip().toString().equals("PERSOANA_FIZICA") && !client.getTip().toString().equals("PERSOANA_JURIDICA"))
            message += "Tipul trebuie sa fie PERSOANA_FIZICA sau PERSOANA_JURIDICA.";
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
