package com.crud.tasks.trello.client;

import com.crud.tasks.controller.TrelloNotFoundException;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/zuzannalubben/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,%20id")
                .queryParam("lists", "all").build().encode().toUri();

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        }catch(RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }


    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }


}




    /*
    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/zuzannalubben/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name, id")
                .queryParam("lists", "all").build().encode().toUri();

        return url;
    }


    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                getUrl(), TrelloBoardDto[].class);
        return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
    }
}
     */
