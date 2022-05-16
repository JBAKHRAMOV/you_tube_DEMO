package com.company.dto;

public class LikeCountDTO {
    private Integer like;
    private Integer dislike;

    public LikeCountDTO() {
    }

    public LikeCountDTO(Integer like, Integer dislike) {
        this.like = like;
        this.dislike = dislike;
    }
}
