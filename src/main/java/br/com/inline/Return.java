package br.com.inline;

public class Return {

    private String value;

    public Return(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Return{" +
                "value='" + value + '\'' +
                '}';
    }
}
