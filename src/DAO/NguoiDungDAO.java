package DAO;

import Database.JDCBCUtil;
import Model.TKAdmin;
import Model.TKNgDung;
import View.DangNhap;
import View.NguoiDung;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NguoiDungDAO {
    public static NguoiDungDAO getInstance(){
        return new NguoiDungDAO();
    }

    public int dangKyTaiKhoan(TKNgDung ngDung){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "INSERT INTO dstknguoidung (userName, password) VALUES (?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, ngDung.getTaiKhoanNG());
            pst.setString(2, ngDung.getMatKhauNG());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int doiMatKhau(TKNgDung ngDung){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "UPDATE dstknguoidung " +
                    "SET password = ? " +
                    "WHERE userName = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, ngDung.getMatKhauNG());
            pst.setString(2, ngDung.getTaiKhoanNG());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public int xoaTaiKhoan(TKNgDung tkNgDung){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "DELETE FROM dstknguoidung WHERE userName = ? AND password = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, tkNgDung.getTaiKhoanNG());
            pst.setString(2, tkNgDung.getMatKhauNG());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public int quenMatKhau(TKNgDung ngDung){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "UPDATE dstknguoidung " +
                    "SET password = ? " +
                    "WHERE userName = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, ngDung.getMatKhauNG());
            pst.setString(2, ngDung.getTaiKhoanNG());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<TKNgDung> dsTkNguoiDung(){
        ArrayList<TKNgDung> dsTraVe = new ArrayList<TKNgDung>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT * FROM dstknguoidung";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                String taiKhoan = resultSet.getString("userName");
                String matKhau = resultSet.getString("password");
                TKNgDung ngDung = new TKNgDung(taiKhoan, matKhau);
                dsTraVe.add(ngDung);
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsTraVe;
    }
    public boolean kiemTraTaiKhoanMatKhauNguoiDung(String taiKhoan, String matKhau) {
        Connection connection = null;
        try {
            connection = JDCBCUtil.getConnection();
            String sql= "SELECT * FROM dstknguoidung WHERE userName = ? AND password = ?";
            PreparedStatement pts = connection.prepareStatement(sql);
            pts.setString(1, taiKhoan);
            pts.setString(2, matKhau);

            ResultSet resultSet = pts.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }finally {
            JDCBCUtil.closeConnection(connection);
        }
    }
    public boolean kiemTraTaiKhoanTonTai(String taiKhoan) {
        Connection connection = null;
        try {
            connection = JDCBCUtil.getConnection();
            String sql= "SELECT * FROM dstknguoidung WHERE userName = ?";
            PreparedStatement pts = connection.prepareStatement(sql);
            pts.setString(1, taiKhoan);

            ResultSet resultSet2 = pts.executeQuery();

            if (resultSet2.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }finally {
            JDCBCUtil.closeConnection(connection);
        }
    }
    public void doanhthu2023(int tien, String thang){
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();
            String sql = "INSERT INTO doanhthu2023 (thang" + thang + ") VALUES (?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, tien);
            pst.executeUpdate();

            System.out.println("2023 - Thêm tiền thành công!");

            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void doanhthu2024(int tien, String thang){
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();
            String sql = "INSERT INTO doanhthu2024 (thang" + thang + ") VALUES (?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, tien);
            pst.executeUpdate();

            System.out.println("2024 - Thêm tiền thành công!");

            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
