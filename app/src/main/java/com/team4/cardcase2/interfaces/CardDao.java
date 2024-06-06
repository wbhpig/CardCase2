package com.team4.cardcase2.interfaces;
import com.team4.cardcase2.entity.Card;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.team4.cardcase2.utils.JDBCutils;
public class CardDao {
    private Connection connection = null;
    private PreparedStatement pStmt = null;
    private ResultSet rs = null;

    public List<Card> getMyCards(int uid){

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Card> c;
        try {
            //获得数据的连接
            conn = JDBCutils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "select * from user_card where user_id=" + uid + ";";
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt(1));
                card.setcName(rs.getString(2));
                card.setcType(rs.getString(3));
                c.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.release(rs, stmt, conn);
        }
        return classificationList;
    }

    public List<Card> getAllCards(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Card> c = new ArrayList<Card>();
        try {
            //获得数据的连接
            conn = JDBCutils.getConnection();
            //获得Statement对象
            stmt = conn.createStatement();
            // 拼接SQL语句
            String sql = "select * from cards;";
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Card card = new Card();
                card.setCard_id(rs.getInt(1));
                card.setElement_type(rs.getString(2));
                card.setContent(rs.getString(3));
                card.setPosition_x(rs.getInt(4));
                card.setPosition_y(rs.getInt(5));
                card.setStyle(rs.getString(6));
                c.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.release(rs, stmt, conn);
        }return c;
    }
    public List<Card> getCardByType(int type){

    }
    public Card getCardById(int id){

    }

    public boolean insertCard(Card card){

    }
    public boolean deleteCard(Card card){

    }
    public Card updateCard(Card card){

    }
}
