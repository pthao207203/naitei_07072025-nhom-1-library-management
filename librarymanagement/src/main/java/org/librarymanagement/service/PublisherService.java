package org.librarymanagement.service;

import org.librarymanagement.entity.Publisher;

public interface PublisherService {
    Publisher findOrCreatePublisher(String publisherName);
}
