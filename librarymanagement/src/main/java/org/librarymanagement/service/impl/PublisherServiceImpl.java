package org.librarymanagement.service.impl;

import org.librarymanagement.entity.Genre;
import org.librarymanagement.entity.Publisher;
import org.librarymanagement.repository.AuthorRepository;
import org.librarymanagement.repository.PublisherRepository;
import org.librarymanagement.service.PublisherService;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl  implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findOrCreatePublisher(String publisherName){
        return publisherRepository.findByName(publisherName)
                .orElseGet(() -> {
                    Publisher p = new Publisher();
                    p.setName(publisherName);
                    return publisherRepository.save(p);
                });
    }
}
