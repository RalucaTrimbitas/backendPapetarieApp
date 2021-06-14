package domain.dtos;

public class ResponseDTO {
    private String result;
    private String name;

    public ResponseDTO(String result, String name) {
        this.result = result;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
