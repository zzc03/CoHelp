package Entity;

/**
 * Created by 22857 on 2021/3/11.
 */

public class User {

    private Integer userId;

    private String name;

    private String account;

    private String password;

    private String description;

    private Integer money;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public User() {
    }

    public User(Integer userId, String name, String account, String password, String description, Integer money) {
        this.userId = userId;
        this.name = name;
        this.account = account;
        this.password = password;
        this.description = description;
        this.money = money;
    }

    public User(Integer userId, String name, String account, String password, String description, Integer money, String icon) {
        this.userId = userId;
        this.name = name;
        this.account = account;
        this.password = password;
        this.description = description;
        this.money = money;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", money=" + money +
                '}';
    }
}
