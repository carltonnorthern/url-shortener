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
    CollectionModel<EntityModel<Url>> all() {

        List<EntityModel<Url>> urls = repository.findAll().stream()
                .map(Url -> EntityModel.of(Url,
                        linkTo(methodOn(URLController.class).one(Url.getId())).withSelfRel(),
                        linkTo(methodOn(URLController.class).all()).withRel("urls")))
                .collect(Collectors.toList());

        return CollectionModel.of(urls, linkTo(methodOn(URLController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/urls")
    Url newURL(@RequestBody Url newUrl) {
        return repository.save(newUrl);
    }

    // Single item

    @GetMapping("/api/urls/{id}")
    EntityModel<Url> one(@PathVariable Long id) {

        Url url = repository.findById(id) //
                .orElseThrow(() -> new URLNotFoundException(id));

        return EntityModel.of(url, //
                linkTo(methodOn(URLController.class).one(id)).withSelfRel(),
                linkTo(methodOn(URLController.class).all()).withRel("urls"));
    }

    /*@GetMapping("/api/urls/{id}")
    public RedirectView redirectUrl(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {

        Url url = repository.findById(id) //
                .orElseThrow(() -> new URLNotFoundException(id));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url.getLongurl());
        return redirectView;
    }*/

    @PutMapping("/api/urls/{id}")
    Url replaceURL(@RequestBody Url newUrl, @PathVariable Long id) {

        return repository.findById(id)
                .map(Url -> {
                    Url.setLongurl(newUrl.getLongurl());
                    return repository.save(Url);
                })
                .orElseGet(() -> {
                    newUrl.setId(id);
                    return repository.save(newUrl);
                });
    }

    @DeleteMapping("/api/urls/{id}")
    void deleteURL(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
