package model;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class user {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String created_at;
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getRole(){
        return role;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getcreatedAt(){
        return created_at;
    }
    
    public void setcreatedAt(String createdAt){
        this.created_at = createdAt;
    }
    
}
