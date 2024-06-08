package com.team4.cardcase2.interfaces;

import com.team4.cardcase2.entity.Card;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public interface CardInterface {

    Connection connection = null;

    List<Card> getMyCards();

    List<Card> getAllCards();
    List<Card> getCardByType(int type);
    Card getCardById(int id);

    boolean insertCard(Card card);
    boolean deleteCard(Card card);
    Card updateCard(Card card);

}
