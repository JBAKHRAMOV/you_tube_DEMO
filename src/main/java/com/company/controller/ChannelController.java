package com.company.controller;

import com.company.changeDto.ChannelStatusDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.ChannelDto;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.ChannelService;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid ChannelDto dto,
                                    HttpServletRequest request) {
        Integer pId=JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("create : {}", dto );
        return ResponseEntity.ok(channelService.create(pId,dto));
    }

    @PutMapping("/{key}")
    public ResponseEntity<?> update(@PathVariable("key") String key,
                                    @RequestBody @Valid ChannelDto dto,
                                    HttpServletRequest request) {
        Integer pId=JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("update : {}", "key: "+key+" "+dto );
        return ResponseEntity.ok(channelService.update(pId,key, dto));
    }

    @PutMapping("/photo/{key}")
    public ResponseEntity<?> updatephoto(@PathVariable("key") String key,
                                         @RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {
        Integer pId=JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("update : {}", "key: "+key+" "+file );
        return ResponseEntity.ok(channelService.updatePhoto(pId,key,file));
    }

    @PutMapping("/banner/{key}")
    public ResponseEntity<?> updatebanner(@PathVariable("key") String key,
                                         @RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {
        Integer pId=JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("update : {}", "key: "+key+" "+file );
        return ResponseEntity.ok(channelService.updateBanner(pId,key,file));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "3") int size,
                                     HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("find all : {}", "page: "+page+" size: "+size );
        return ResponseEntity.ok(channelService.getPaginationList(page, size));
    }

    @GetMapping("/adm/userPag")
    public ResponseEntity<?> findUserCHannel(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "3") int size,
                                     HttpServletRequest request) {
        Integer pId=JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("find all : {}", "page: "+page+" size: "+size );
        return ResponseEntity.ok(channelService.getUserPaginationList(pId,page, size));
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("get by id : {}", id );
        return ResponseEntity.ok(categoryService.getById(id));
    }


    @PutMapping("/status")
    public ResponseEntity<?> changeStatus(@RequestBody @Valid ChannelStatusDTO dto,
                                          HttpServletRequest request) {
        ProfileJwtDTO jwtDTO=JwtUtil.getProfileFromHeader(request);
        log.info("delete : {}", dto );
        return ResponseEntity.ok(channelService.changeStatus(jwtDTO.getId(), jwtDTO.getRole(), dto));
    }
}
