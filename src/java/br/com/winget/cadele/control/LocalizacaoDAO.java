/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.winget.cadele.control;

import br.com.winget.cadele.model.Localizacao;
import br.com.winget.conexaoDB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author fmiranda
 */
public class LocalizacaoDAO {
    private Connection connection;
    
    public LocalizacaoDAO(){    
        this.connection = DBConnection.getConexao();
    }    
    
    public int insert(Localizacao localizacao){    
            String sql = "INSERT INTO cd_localizacao(id_usuario,latitude,longitude,altitude,data) VALUES(?,?,?,?,now())";    
            String sql2 = "SELECT max(id) as id FROM cd_localizacao";
            int id = 0;
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, localizacao.getIdUser());     
                stmt.setDouble(2, localizacao.getLatitude());
                stmt.setDouble(3, localizacao.getLongitude());
                stmt.setDouble(4, localizacao.getAltitude());
                stmt.execute();
                stmt.close();    
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                ResultSet rs = stmt2.executeQuery();
                while(rs.next()){
                    id = rs.getInt("id");
                }
                stmt2.close();
                return id;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    
    public int update(Localizacao localizacao){    
            String sql = "UPDATE cd_localizacao SET latitude = ?, longitude = ?, altitude = ?, data = now() WHERE id_usuario = ?";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setDouble(1, localizacao.getLatitude());
                stmt.setDouble(2, localizacao.getLongitude());
                stmt.setDouble(3, localizacao.getAltitude());
                stmt.setInt(4, localizacao.getIdUser());
                int retorno = stmt.executeUpdate();
                stmt.close();
                return retorno;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    
    public Localizacao getLocalByUser(int id){
        String sql = "SELECT * FROM cd_localizacao where id_usuario = ?";
        Localizacao location = new Localizacao();
        try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);     
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    location.setId(rs.getInt("id"));
                    location.setIdUser(rs.getInt("id_usuario"));
                    location.setLatitude(rs.getDouble("latitude"));
                    location.setLongitude(rs.getDouble("longitude"));
                    location.setAltitude(rs.getDouble("altitude"));
                    location.setData(rs.getString("data"));
                }
                stmt.close();    
                return location;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }
    }
}
