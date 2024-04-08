package DAO;

import Database.JDCBCUtil;
import Model.LoaiGhe;
import View.ADMIN;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XuHuongDAO {
    private LichBayDAO lichBayDAO;
    private ADMIN admin;
    public XuHuongDAO() {
        this.lichBayDAO = new LichBayDAO();
    }
    public LoaiGhe xuHuongDatQuaCacNam(String year) {
        String[] macb = laydanhsachcacchuyenbaycungnam(year);
        int eco = 0;
        int busin = 0;
        int first = 0;
        for(String ma : macb) {
            try {
                Connection con = JDCBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT loaiGhe FROM " + ma);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    if(rs.getString("loaiGhe").equals("EconomyClass")) {
                        eco ++;
                    } else if (rs.getString("loaiGhe").equals("BusinessClass")) {
                        busin ++;
                    } else if (rs.getString("loaiGhe").equals("FirstClass")) {
                        first ++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();;
            }
        }
        return new LoaiGhe(eco, busin, first);
    }

    public String[] laydanhsachcacchuyenbaycungnam(String year) {
        List<String> ketqua = new ArrayList<>();
        try {
            Connection con = JDCBCUtil.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT maChuyenBay FROM luutru WHERE SUBSTRING_INDEX(ngayBay, '/', -1) = '" + year + " '");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ketqua.add(rs.getString("maChuyenBay"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketqua.toArray(new String[0]);
    }
    public String[] laynam() {
        List<String> ketqua = new ArrayList<>();
        try {
            Connection con = JDCBCUtil.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT SUBSTRING_INDEX(ngayBay, '/', -1) AS nam FROM luutru");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ketqua.add(rs.getString("nam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketqua.toArray(new String[0]);
    }
}
