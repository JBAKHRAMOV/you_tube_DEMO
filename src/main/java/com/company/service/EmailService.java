package com.company.service;

import com.company.dto.EmailDTO;
import com.company.entity.EmailEntity;
import com.company.enums.EmailType;
import com.company.exception.ItemNotFoundException;
import com.company.repository.EmailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    public void send(String toEmail, String title, String content) {
        SimpleMailMessage simple = new SimpleMailMessage();
        simple.setTo(toEmail);
        simple.setSubject(title);
        simple.setText(content);
        javaMailSender.send(simple);

        EmailEntity entity = new EmailEntity();
        entity.setToEmail(toEmail);
        entity.setType(EmailType.VERIFICATION);
        emailRepository.save(entity);
    }

    public List<EmailDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "sendDate"));

        List<EmailDTO> dtoList = new ArrayList<>();
        emailRepository.findAll(pageable).stream().forEach(entity -> dtoList.add(toDTO(entity)));

        return dtoList;
    }

    public Boolean delete(Integer id) {
        emailRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not found!"));

        emailRepository.deleteById(id);
        return true;
    }

    private EmailDTO toDTO(EmailEntity entity) {
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setType(entity.getType());
        dto.setSendDate(entity.getCreatedDate());
        return dto;
    }
}
