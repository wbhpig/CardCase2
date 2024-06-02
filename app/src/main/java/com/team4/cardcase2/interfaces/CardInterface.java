package com.team4.cardcase2.interfaces;

import com.team4.cardcase2.entity.Card;

import java.util.List;

public interface CardInterface {
    List<Card> getMyCards();
    List<Card> getAllCards();
    List<Card> getCardByType(int type);
}
