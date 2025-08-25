package service.impl;

import entity.Publisher;
import repository.PublisherRepository;
import service.IPublisherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements IPublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Override
    public List<Publisher> searchPublishers(String keyword) {
        return publisherRepository.searchPublishers(keyword);
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher update(Long id, Publisher publisherDetails) {
        return publisherRepository.findById(id)
                .map(existingPublisher -> {
                    existingPublisher.setName(publisherDetails.getName());
                    existingPublisher.setAddress(publisherDetails.getAddress());
                    existingPublisher.setCity(publisherDetails.getCity());
                    existingPublisher.setCountry(publisherDetails.getCountry());
                    existingPublisher.setWebsite(publisherDetails.getWebsite());
                    return publisherRepository.save(existingPublisher);
                })
                .orElseThrow(() -> new RuntimeException("Publisher not found with id " + id));
    }

    @Override
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }
}
