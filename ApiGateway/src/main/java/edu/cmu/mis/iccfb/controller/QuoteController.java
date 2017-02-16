package edu.cmu.mis.iccfb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import edu.cmu.mis.iccfb.model.Quote;
import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.service.QuoteService;
import edu.cmu.mis.iccfb.service.AuthorService;

@RestController
public class QuoteController {

	@Autowired
    private AuthorService authorService;
    
    @Autowired
    private QuoteService quoteService;    
    
    @RequestMapping("/api/quote/random")
    public Quote random() {
    	Quote q = quoteService.randomQuote();
        Author a = authorService.findOne(q.getAuthorId());
        q.setAuthor(a);
    	return q;
    }  

    @RequestMapping("/api/quote/author/{authorId}")
    public Iterable<Quote> listQuotes(@PathVariable Long authorId) {
    	Author a = authorService.findOne(authorId);
        System.out.println(authorId);
    	Iterable<Quote> quotes = quoteService.findByAuthorId(authorId);
    	return quotes;
    }

    @RequestMapping("/api/author/{authorId}")
    public Iterable<Quote> listQuotes2(@PathVariable Long authorId) {
        Author a = authorService.findOne(authorId);
        System.out.println(authorId);
        Iterable<Quote> quotes = quoteService.findByAuthorId(authorId);
        return quotes;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
        System.out.println(quote);        
        System.out.println(quote.getAuthor().getName());
        Author a = authorService.findByName(quote.getAuthor().getName());
        
        if (a == null) {
            System.out.println("Saving author");
            authorService.save(quote.getAuthor());
        }
        else {
            quote.setAuthor(a);
            quote.setAuthorId(a.getId());
        }
        System.out.println("Saving quote");
        quoteService.save(quote);
    }
}
