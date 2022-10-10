package com.company.controller;

import com.company.dto.VideoLikeDto;
import com.company.service.VideoLIkeService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/videoLike")
@RequiredArgsConstructor
public class VideoLikeController {

    private final VideoLIkeService videoLIkeService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid VideoLikeDto dto,
                                    HttpServletRequest request) {
        log.info("create: {}", dto);
        Integer pId = JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.create(pId, dto));
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> get(@PathVariable("key") String key,
                                 HttpServletRequest request) {
        log.info("create: {}", key);
        Integer pId = JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.read(pId, key));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody @Valid VideoLikeDto dto,
                                    HttpServletRequest request) {
        log.info("create: {}", dto);
        Integer pId = JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.update(pId, dto));
    }
}
