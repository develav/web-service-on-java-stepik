package entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
public class AccountService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String password;

    public AccountService(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AccountService() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountService(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }


}
