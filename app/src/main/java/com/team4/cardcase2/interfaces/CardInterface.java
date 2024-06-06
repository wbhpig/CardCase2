package com.team4.cardcase2.interfaces;

import com.team4.cardcase2.entity.Card;

import java.util.List;

public interface CardInterface {
    List<Card> getMyCards();
    List<Card> getAllCards();
    List<Card> getCardByType(int type);
    Card getCardById(int id);

    boolean insertCard(Card card);
    boolean deleteCard(Card card);
    Card updateCard(Card card);

}
