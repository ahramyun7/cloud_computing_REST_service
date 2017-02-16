package edu.cmu.mis.iccfb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;
import java.util.*;

@Service
public class RestQuoteServiceImpl implements QuoteService {
	@Value("${service.quote.uri}")
	private String quoteServiceUri;

	@Override
    public Quote randomQuote() {
        RestTemplate rt = new RestTemplate();
        String uri = quoteServiceUri + "/random";
        Quote q = rt.getForObject(uri, Quote.class);
        return q;
    }
    
    @Override
    public Iterable<Quote> findByAuthorId(Long authorId) {        
        RestTemplate rt = new RestTemplate();
        String uri = quoteServiceUri + "/author/" + authorId;
        System.out.println("request uri : " + uri);
        ResponseEntity<Quote[]> r = rt.getForEntity(uri, Quote[].class);
        Quote[] objects = r.getBody();
        Iterable<Quote> q = Arrays.asList(objects);
        for(Quote q_test : q) {
            System.out.println(q_test.getText());
        }
        return q;
    }

    @Override
    public Long save(Quote q) {
        RestTemplate rt = new RestTemplate();
        String uri = quoteServiceUri;
        ResponseEntity<Long> st = rt.postForEntity(uri, q, Long.class);
        return st.getBody();
    }
}
