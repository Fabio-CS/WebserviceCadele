/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.winget.conexaoDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fmiranda
 */
public class DBConnection {

    public static String status = "Não conectou...";
    private static Connection instance;
    
    private DBConnection(){
    }
    
    public static java.sql.Connection getConexao() {    
        if (instance == null) {
            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName);
                String serverName = "mysql.winget.com.br";
                String mydatabase = "winget";
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
                String username = "winget";
                String password = "CartaoJA2013";
                instance = DriverManager.getConnection(url, username, password);
                if (instance != null) {
                    status = ("STATUS--->Conectado com sucesso!");
                } else {
                    status = ("STATUS--->Não foi possivel realizar conexão");
                }
                return instance;

            } catch (ClassNotFoundException e) { //Driver não encontrado
                System.out.println("O driver expecificado nao foi encontrado.");
                return null;
            } catch (SQLException e) {
                //Não conseguindo se conectar ao banco
                System.out.println("Nao foi possivel conectar ao Banco de Dados.");
                return null;
            }
        }else{
            return instance;
        }
    }
    //Método que retorna o status da sua conexão//
    public static String statusConection() {
        return status;
    }

    //Método que fecha sua conexão//
    public static boolean FecharConexao() {
        try {
            DBConnection.getConexao().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Método que reinicia sua conexão//
    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return DBConnection.getConexao();
    }

}
