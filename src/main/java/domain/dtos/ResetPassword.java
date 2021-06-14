package domain.dtos;

public class ResetPassword {
    private String token;
    private String parola;
    private String confirmare_parola;

    public ResetPassword() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getConfirmare_parola() {
        return confirmare_parola;
    }

    public void setConfirmare_parola(String confirmare_parola) {
        this.confirmare_parola = confirmare_parola;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "token='" + token + '\'' +
                ", parola='" + parola + '\'' +
                ", confirmare_parola='" + confirmare_parola + '\'' +
                '}';
    }
}
