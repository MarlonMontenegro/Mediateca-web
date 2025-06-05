/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import dao.TipoMaterialDAO;
import java.util.List;
/**
 *
 * @author marlo
 */
public class TipoMaterialController {
     private final TipoMaterialDAO dao = new TipoMaterialDAO();

    public List<String> listarCategorias() {
        return dao.listarCategorias();
    }
}
