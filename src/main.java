
import controllers.LoginController;
import model.Usuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author marlo
 */
public class main {
    
    public static void main(String[] arg) {
        
    LoginController login = new LoginController();
    
    Usuario u = login.validar("admin","1234");
    
    System.out.println(u.getUsuario());
    
    
    }
            
}
