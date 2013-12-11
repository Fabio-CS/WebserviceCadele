/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.winget.cadele.control;

import br.com.winget.cadele.model.Usuario;
import br.com.winget.conexaoDB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author fmiranda
 */
public class UsuarioDAO {
    private Connection connection;
    
    public UsuarioDAO(){    
        this.connection = DBConnection.getConexao();
    }    
    
    public int insert(Usuario usuario){    
            String sql = "INSERT INTO cd_usuario(nome,email,senha) VALUES(?,?,?)";
            String sql2 = "SELECT max(id) as id from cd_usuario";
            int id = 0;
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, usuario.getNome());     
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getSenha());
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
    
    public boolean insertFriendship(int idUser, int idUserFriend){    
            String sql = "INSERT INTO cd_amizade (id_usuario, id_usuario_amigo) VALUES(?, ?)";
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, idUser);     
                stmt.setInt(2, idUserFriend);
                stmt.execute();
                stmt.close();
                return true;
            } catch (SQLException u) {
                throw new RuntimeException(u);    
        }    
    }
    
    public Usuario getUserById(int id){    
            Usuario user = new Usuario();
            String sql = "SELECT * FROM cd_usuario Where id = ?";
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    user.setId(rs.getInt("id"));
                    user.setNome(rs.getString("nome"));
                    user.setEmail(rs.getString("email"));
                    user.setSenha(rs.getString("senha"));
                    user.setAtivo(rs.getInt("ativo"));
                }
                stmt.close();
                return user;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    //
    
    public ArrayList<Usuario> getUserFriends(int id){    
            ArrayList amigos = new ArrayList();
            String sql = "SELECT u.id, u.nome, u.email FROM cd_usuario u inner join cd_amizade a on u.id = a.id_usuario_amigo WHERE a.id_usuario = ?";
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("id"));
                    user.setNome(rs.getString("nome"));
                    user.setEmail(rs.getString("email"));
                    amigos.add(user);
                }
                stmt.close();
                return amigos;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    
    public Usuario getUserSearch(String email){    
            String sql = "SELECT u.id, u.nome, u.email FROM cd_usuario u WHERE u.email = ?";
            Usuario user = new Usuario();
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    user.setId(rs.getInt("id"));
                    user.setNome(rs.getString("nome"));
                    user.setEmail(rs.getString("email"));
                }
                stmt.close();
                return user;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    
    public int getUserLogin(String email, String senha){    
            String sql = "SELECT u.id FROM cd_usuario u WHERE u.email = ? and u.senha = ?";
            int id = 0;
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, senha);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    id = rs.getInt("id");
                }
                stmt.close();
                return id;
            } catch (SQLException u) {    
                throw new RuntimeException(u);    
        }    
    }
    
    public void update(Usuario usuario){    
            String sql = "UPDATE cd_usuario SET nome = ?, email = ?, senha = ?, ativo = ? WHERE id = ?";    
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, usuario.getNome());     
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getSenha());
                stmt.setInt(4, usuario.getAtivo());
                stmt.setInt(5, usuario.getId());
                stmt.execute();    
                stmt.close();    
            } catch (SQLException u) {    
                throw new RuntimeException(u);  
        }    
    }
    
    public void delete(int id){  
            String sql = "UPDATE cd_usuario SET ativo = 0 WHERE id = ?";    
            try {    
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.execute();    
                stmt.close();    
            } catch (SQLException u) {    
                throw new RuntimeException(u);  
        }    
    }
    
}
