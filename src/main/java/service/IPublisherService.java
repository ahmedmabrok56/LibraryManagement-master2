package service;

import entity.Publisher;

import java.util.List;
import java.util.Optional;

public interface IPublisherService {
    List<Publisher> findAllPublishers();
    Optional<Publisher> findById(Long id);
    List<Publisher> searchPublishers(String keyword);
    Publisher save(Publisher publisher);
    Publisher update(Long id, Publisher publisherDetails);
    void deleteById(Long id);
}
