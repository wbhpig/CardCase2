package com.team4.cardcase2.interfaces;

import com.team4.cardcase2.entity.ServerCard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class test {

    @NotNull
    public static ServerCard getCardById(int id){
        String card = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"elements\": [\n" +
                "        {\n" +
                "            \"type\": \"name\",\n" +
                "            \"content\": \"Li Tiansuo\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 10\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": true,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"title\",\n" +
                "            \"content\": \"Title\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 30\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#333333\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"company\",\n" +
                "            \"content\": \"Company\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 50\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#333333\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"email\",\n" +
                "            \"content\": \"1919@810.com\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 70\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"phone\",\n" +
                "            \"content\": \"114514\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 90\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"socialMedia\",\n" +
                "            \"content\": \"Kobe Bryant\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 110\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"avatar\": \"5\",\n" +
                "    \"background\": \"6\",\n" +
                "    \"design\": {\n" +
                "        \"templateId\": \"1\",\n" +
                "        \"color\": \"2\",\n" +
                "        \"font\": \"3\",\n" +
                "        \"layout\": \"4\"\n" +
                "    }\n" +
                "}";
        ServerCard serverCard = ServerCard.Companion.fromJson(card);
        return serverCard;
    }

    @NotNull
    public static List<ServerCard> getMyCard() {
        String card = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"elements\": [\n" +
                "        {\n" +
                "            \"type\": \"name\",\n" +
                "            \"content\": \"Li Tiansuo\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 10\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": true,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"title\",\n" +
                "            \"content\": \"Title\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 30\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#333333\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"company\",\n" +
                "            \"content\": \"Company\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 50\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#333333\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"email\",\n" +
                "            \"content\": \"1919@810.com\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 70\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"phone\",\n" +
                "            \"content\": \"114514\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 90\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"socialMedia\",\n" +
                "            \"content\": \"Kobe Bryant\",\n" +
                "            \"position\": {\n" +
                "                \"x\": 50,\n" +
                "                \"y\": 110\n" +
                "            },\n" +
                "            \"style\": {\n" +
                "                \"font\": \"Arial\",\n" +
                "                \"size\": 20,\n" +
                "                \"color\": \"#000000\",\n" +
                "                \"bold\": false,\n" +
                "                \"italic\": false,\n" +
                "                \"underline\": false\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"avatar\": \"5\",\n" +
                "    \"background\": \"6\",\n" +
                "    \"design\": {\n" +
                "        \"templateId\": \"1\",\n" +
                "        \"color\": \"2\",\n" +
                "        \"font\": \"3\",\n" +
                "        \"layout\": \"4\"\n" +
                "    }\n" +
                "}";
        List<ServerCard> cardList = new ArrayList<>();
        ServerCard serverCard = ServerCard.Companion.fromJson(card);
        System.out.println(serverCard);
        cardList.add(serverCard);
//        cardList.add(serverCard);
        return cardList;
    }
}
