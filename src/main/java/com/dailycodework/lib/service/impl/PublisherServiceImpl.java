package com.dailycodework.lib.service.impl;


import com.dailycodework.lib.entity.Publisher;
import com.dailycodework.lib.repository.PublisherRepository;
import com.dailycodework.lib.service.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherServiceImpl implements IPublisherService {

    private final PublisherRepository publisherRepository;


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
    @Transactional
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    @Transactional
    public Publisher update(Long id, Publisher publisherDetails) {

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Publisher not found with id: "+id));

        BeanUtils.copyProperties(publisherDetails,publisher,"id");

       /* return publisherRepository.findById(id)
                .map(existingPublisher -> {
                    existingPublisher.setName(publisherDetails.getName());
                    existingPublisher.setAddress(publisherDetails.getAddress());
                    existingPublisher.setCity(publisherDetails.getCity());
                    existingPublisher.setCountry(publisherDetails.getCountry());
                    existingPublisher.setWebsite(publisherDetails.getWebsite());
                    return publisherRepository.save(existingPublisher);
                })
                .orElseThrow(() -> new RuntimeException("Publisher not found with id " + id));*/
        return publisherRepository.save(publisher);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }
}
