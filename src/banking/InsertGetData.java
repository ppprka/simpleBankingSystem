package banking;

import java.sql.*;

public class InsertGetData {

    private static Connection connect(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String fileName, String number, String pin) {
        String sql = "INSERT INTO card (number,pin) VALUES(?,?)";
        try (Connection conn = connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,number);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int get(String fileName, String number, String pin){
        String sql = "SELECT id FROM card WHERE number=? AND pin=?";
        int id=0;
        try (Connection conn = connect(fileName)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,number);
            pstmt.setString(2,pin);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id=rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public String existCard(String fileName,String number){
        String sql = "SELECT number FROM card WHERE number = ?";
        String existNumber="";
        try(Connection conn = connect(fileName)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,number);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                existNumber=rs.getString("number");
            }
        }   catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return existNumber;
    }

    public int balance(String fileName, String number, String pin){
        String sql = "SELECT balance FROM card WHERE number=? AND pin=?";
        int balance = 0;
        try (Connection conn = connect(fileName)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    public void addIncome(String fileName,int balance, String number){
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        changeBalance(fileName, sql, balance, number);
    }

    public void doTransfer(String fileName, int balance,String fromNumber,String toNumber){
        addIncome(fileName,balance,toNumber);
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?";
        changeBalance(fileName, sql,balance, fromNumber);
    }

    public void changeBalance(String fileName,String sql, int balance,String number){
        try (Connection conn = connect(fileName)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, balance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeAccount(String fileName, String cardNum){
        String sql = "DELETE FROM card WHERE number = ?";
        try (Connection conn = connect(fileName)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,cardNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
