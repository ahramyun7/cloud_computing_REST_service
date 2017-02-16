package edu.cmu.mis.iccfb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

@Service
public class RestAuthorServiceImpl implements AuthorService {
	@Value("${service.author.uri}")
	private String authorServiceUri;

	@Override
    public Author findByName(String name) {
    	RestTemplate rt = new RestTemplate();
    	String uri = authorServiceUri + "/byname?name=" + name;
    	Author a = rt.getForObject(uri, Author.class);
    	return a;
    }

    @Override
    public Long save(Author a) {
    	RestTemplate rt = new RestTemplate();
    	String uri = authorServiceUri;
    	ResponseEntity<Long> st = rt.postForEntity(uri, a, Long.class);
    	return st.getBody();
    }

    @Override
    public Author findOne(Long id) {
    	RestTemplate rt = new RestTemplate();
    	String uri = authorServiceUri + "/" + id;
    	Author a = rt.getForObject(uri, Author.class);
    	return a;
    }
}
