package kg.BIZ.dto.request;

import lombok.Builder;

@Builder
public record BannerRequest (
        String image
){
}
