package com.crud.tasks.trello.client;

import com.crud.tasks.controller.TrelloNotFoundException;
import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloAppToken;
    @Value(("${trello.app.username}"))
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards()

    {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/zuzannalubben/boards" + "?key=0df421c457a7a20bd555b4eea088f437&token=69598a7c2ba453f6319b06e09b683c91a9b0c7a89a6ba1df590b4f465a812679&fields=name,%20id&lists=all").build().encode().toUri();
                //.queryParam("key", trelloAppKey)
                //.queryParam("token", trelloAppToken)
                //.queryParam("fields", "name", "id").build().encode().toUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();

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
