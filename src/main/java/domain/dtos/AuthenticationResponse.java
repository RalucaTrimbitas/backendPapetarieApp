package domain.dtos;

public class AuthenticationResponse {
    private String userType;
    private String name;
    private String jwt;

    public AuthenticationResponse() {
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
