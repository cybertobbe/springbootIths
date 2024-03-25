package com.example.springbootprojektiths.service;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MessageServices {

    @Autowired
    MessageRepository messageRepository;

    public Page<Message> findPaginated(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }
}
