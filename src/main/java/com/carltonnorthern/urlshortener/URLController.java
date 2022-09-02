package com.carltonnorthern.urlshortener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class URLController {

    private final URLRepository repository;

    URLController(URLRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/urls")
    CollectionModel<EntityModel<URL>> all() {

        List<EntityModel<URL>> urls = repository.findAll().stream()
                .map(URL -> EntityModel.of(URL,
                        /*linkTo(methodOn(URLController.class).one(URL.getId())).withSelfRel(),*/
                        linkTo(methodOn(URLController.class).all()).withRel("urls")))
                .collect(Collectors.toList());

        return CollectionModel.of(urls, linkTo(methodOn(URLController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/urls")
    URL newURL(@RequestBody URL newURL) {
        return repository.save(newURL);
    }

    // Single item

    /*@GetMapping("/urls/{id}")
    EntityModel<URL> one(@PathVariable Long id) {

        URL url = repository.findById(id) //
                .orElseThrow(() -> new URLNotFoundException(id));

        return EntityModel.of(url, //
                linkTo(methodOn(URLController.class).one(id)).withSelfRel(),
                linkTo(methodOn(URLController.class).all()).withRel("urls"));
    }*/

    @GetMapping("/api/urls/{id}")
    public RedirectView redirectUrl(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {

        URL url = repository.findById(id) //
                .orElseThrow(() -> new URLNotFoundException(id));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url.getLongURL());
        return redirectView;
    }

    @PutMapping("/api/urls/{id}")
    URL replaceURL(@RequestBody URL newURL, @PathVariable Long id) {

        return repository.findById(id)
                .map(URL -> {
                    URL.setLongURL(newURL.getLongURL());
                    URL.setRole(newURL.getRole());
                    return repository.save(URL);
                })
                .orElseGet(() -> {
                    newURL.setId(id);
                    return repository.save(newURL);
                });
    }

    @DeleteMapping("/api/urls/{id}")
    void deleteURL(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
