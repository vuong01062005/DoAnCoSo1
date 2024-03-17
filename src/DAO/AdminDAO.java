package DAO;

import Database.JDCBCUtil;
import Model.TKAdmin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    public static AdminDAO getInstance(){
        return new AdminDAO();
    }

    public int dangKyTaiKhoan(TKAdmin tkAdmin){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "INSERT INTO dstkadmin (userName, password) VALUES (?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, tkAdmin.getTaiKhoanAD());
            pst.setString(2, tkAdmin.getMatKhauAD());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int doiMatKhau(TKAdmin tkAdmin){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "UPDATE dstkadmin " +
                    "SET password = ? " +
                    "WHERE userName = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, tkAdmin.getMatKhauAD());
            pst.setString(2, tkAdmin.getTaiKhoanAD());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int xoaTaiKhoan(TKAdmin tkAdmin){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "DELETE FROM dstkadmin WHERE userName = ? AND password = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, tkAdmin.getTaiKhoanAD());
            pst.setString(2, tkAdmin.getMatKhauAD());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int quenMatKhau(TKAdmin tkAdmin){
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "UPDATE dstkadmin " +
                    "SET password = ? " +
                    "WHERE userName = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, tkAdmin.getMatKhauAD());
            pst.setString(2, tkAdmin.getTaiKhoanAD());
            pst.executeUpdate();
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<TKAdmin> dsTkAdmin (){
        ArrayList<TKAdmin> dsTraVe = new ArrayList<TKAdmin>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT * FROM dstkadmin";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                String taiKhoan = resultSet.getString("userName");
                String matKhau =resultSet.getString("password");
                TKAdmin tkAdmin = new TKAdmin(taiKhoan, matKhau);
                dsTraVe.add(tkAdmin);
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsTraVe;
    }
    public boolean kiemTraTaiKhoanMatKhauAdmin(String taiKhoan, String matKhau) {
        Connection connection = null;
        try {
            connection = JDCBCUtil.getConnection();
            String sql= "SELECT * FROM dstkadmin WHERE userName = ? AND password = ?";
            PreparedStatement pts = connection.prepareStatement(sql);
            pts.setString(1, taiKhoan);
            pts.setString(2, matKhau);

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
    public boolean kiemTraTaiKhoanTonTai(String taiKhoan) {
        Connection connection = null;
        try {
            connection = JDCBCUtil.getConnection();
            String sql= "SELECT * FROM dstkadmin WHERE userName = ?";
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
    public int demSoLuongGhe_Economic(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMCB2023 = dsMacb2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
                for (int i=0; i<dsMCB2023.size(); i++){
                    if (i>0){
                        sql += ",";
                    }
                    sql += "?";
                }
                sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMCB2023.size(); i++) {
                pst.setString(i + 1, dsMCB2023.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSoLuongGhe_Business(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMCB2023 = dsMacb2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB2023.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMCB2023.size(); i++) {
                pst.setString(i + 1, dsMCB2023.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSoLuongGhe_FirstClass(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMCB2023 = dsMacb2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB2023.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMCB2023.size(); i++) {
                pst.setString(i + 1, dsMCB2023.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public List<String> dsMacb2023(){
        List<String> dsMCB2023 = new ArrayList<String>();
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay " +
                         "FROM lichbay " +
                         "WHERE YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                String mcb = resultSet.getString("maChuyenBay");
                dsMCB2023.add(mcb);
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB2023;
    }
    private List<String> dsMcb_T1_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T2_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    public List<String> dsMcb_T3_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T4_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T5_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T6_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T7_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T8_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T9_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T10_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T11_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    private List<String> dsMcb_T12_2023(){
        List<String> dsMcb = new ArrayList<String>();
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay, ngayBay FROM lichbay " +
                    "WHERE MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12 " +
                    "AND YEAR(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2023";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMcb.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMcb;
    }
    // COUNT ECONOMYCLASS
    public int demSlGheE_T1_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T1_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN (";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T2_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T2_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN (";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T3_2023() {
        int soluongghe = 0;
        Connection connection = null;
        try{

            List<String> dsMcb = dsMcb_T3_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMcb.size(); i++){
                pst.setString(i+1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()){
                soluongghe += resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T4_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T4_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T5_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T5_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T6_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T6_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T7_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T7_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T8_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T8_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T9_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T9_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T10_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T10_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T11_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T11_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheE_T12_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T12_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'EconomyClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    // COUNT BUSINESSCLASS
    public int demSlGheB_T1_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T1_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T2_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T2_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T3_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T3_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T4_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T4_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T5_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T5_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T6_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T6_2023();

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T7_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T7_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T8_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T8_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T9_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T9_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T10_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T10_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T11_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T11_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheB_T12_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T12_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'BusinessClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    // COUNT FIRSTCLASS
    public int demSlGheF_T1_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T1_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T2_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T2_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T3_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T3_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T4_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T4_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T5_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T5_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T6_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T6_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T7_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T7_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T8_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T8_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T9_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T9_2023();

            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T10_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T10_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T11_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T11_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }
    public int demSlGheF_T12_2023(){
        int soluongghe = 0;
        Connection connection = null;
        try{
            connection = JDCBCUtil.getConnection();

            // lấy ds mã chuyến bay từ dsMacb2023()
            List<String> dsMcb = dsMcb_T12_2023();
            if (dsMcb.size()==0){
                return soluongghe=0;
            }

            // tạo câu lệnh truy vấn nếu điều kiện true thì + chuỗi thêm một ",?"
            String sql = "SELECT COUNT(*) AS slGhe FROM dskhachhang WHERE maChuyenBay IN(";
            for (int i=0; i<dsMcb.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ") AND loaiGhe = 'FirstClass'";
            PreparedStatement pst = connection.prepareStatement(sql);

            // Dùng vòng lặp để tạo số lượng câu lệnh == với số giá trị "?" ở câu lệnh truy vấn
            for (int i = 0; i<dsMcb.size(); i++) {
                pst.setString(i + 1, dsMcb.get(i));
            }
            ResultSet resultSet = pst.executeQuery();

            // lấy ra giá trị số lượng ghế loại EconomyClass
            if (resultSet.next()) {
                soluongghe = resultSet.getInt("slGhe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return soluongghe;
    }

    // LẤY MCB CỦA VietJet_Air
    private List<String> VietJet_Air_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> VietJet_Air_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'VietJet Air'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY MCB CỦA Pacific_Airlines
    private List<String> Pacific_Airlines_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Pacific_Airlines_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Pacific Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY MCB CỦA Vietravel_Airlines
    private List<String> Vietravel_Airlines_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietravel_Airlines_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietravel Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY MCB CỦA Bamboo_Airways
    private List<String> Bamboo_Airways_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Bamboo_Airways_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Bamboo Airways'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY MCB Vasco
    private List<String> Vasco_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vasco_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vasco'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY MCB Vietnam_Airlines
    private List<String> Vietnam_Airlines_dsMCB_T1(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T2(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T3(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 3";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T4(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 4";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T5(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 5";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T6(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 6";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T7(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 7";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T8(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 8";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T9(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 9";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T10(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 10";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T11(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 11";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }
    private List<String> Vietnam_Airlines_dsMCB_T12(){
        List<String> dsMCB = new ArrayList<String>();
        try {
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT maChuyenBay\n" +
                    "FROM lichbay\n" +
                    "WHERE hangBay = 'Vietnam Airlines'\n" +
                    "AND MONTH(STR_TO_DATE(ngayBay, '%d/%m/%Y')) = 12";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                dsMCB.add(resultSet.getString("maChuyenBay"));
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsMCB;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA VietJet_Air
    public int VietJet_Air_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i + 1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int VietJet_Air_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = VietJet_Air_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA Pacific_Airlines
    public int Pacific_Airlines_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Pacific_Airlines_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = Pacific_Airlines_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA Vietravel_Airlines
    public int Vietravel_Airlines_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietravel_Airlines_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = Vietravel_Airlines_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA Bamboo_Airways
    public int Bamboo_Airways_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Bamboo_Airways_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = Bamboo_Airways_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA Vasco
    public int Vasco_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vasco_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = Vasco_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

    // LẤY SỐ LƯỢNG KHÁCH HÀNG QUA CÁC THÁNG CỦA Vietnam_Airlines
    public int Vietnam_Airlines_slKH_T1(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T1();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T2(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T2();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T3(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T3();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T4(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T4();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T5(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T5();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T6(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T6();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T7(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T7();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T8(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T8();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T9(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T9();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T10(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T10();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T11(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T11();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }
    public int Vietnam_Airlines_slKH_T12(){
        int slKH=0;
        List<String> dsMCB = Vietnam_Airlines_dsMCB_T12();
        if (dsMCB.size()==0){
            return slKH=0;
        }
        try{
            Connection connection = JDCBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS SlVe FROM dskhachhang\n" +
                    "WHERE maChuyenBay IN(";
            for (int i=0; i<dsMCB.size(); i++){
                if (i>0){
                    sql += ",";
                }
                sql += "?";
            }
            sql += ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            for (int i=0; i<dsMCB.size(); i++){
                pst.setString(i+1, dsMCB.get(i));
            }
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                slKH = resultSet.getInt("SlVe");
            }
            JDCBCUtil.closeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return slKH;
    }

}
