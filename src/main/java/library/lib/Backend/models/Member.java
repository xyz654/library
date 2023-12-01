package library.lib.Backend.models;

import com.google.gson.JsonObject;

import javax.persistence.*;
@Entity
public class Member implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private String password;

    public Member() {
    }

    public Member(String name, String email, String password) {
        this.name = name;
        if(verifyEmail(email)) {
            this.email = email;
        }
        if(verifyPassword(password)) {
            this.password = password;
        }
    }

    public int getId() {
        return id;
    }

    public boolean verifyEmail(String email) {
        if(email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }

    public boolean verifyPassword(String password) {
        if(password.length() >= 8) {
            return true;
        }
        return false;
    }

    public void setEmail(String email) {
        if(verifyEmail(email)) {
            this.email = email;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        if(verifyPassword(password)) {
            this.password = password;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }

    public String toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("name", name);
        json.addProperty("email", email);
        return json.toString();
    }
}
