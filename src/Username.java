import java.io.Serializable;

@SuppressWarnings("serial")
public class Username implements Serializable {
    public String username;

    public Username(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}