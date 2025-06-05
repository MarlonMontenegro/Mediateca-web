/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author marlo
 */
public class TipoMaterialDAO {
    
     public List<String> listarCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre FROM TipoMaterial";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
    
}
