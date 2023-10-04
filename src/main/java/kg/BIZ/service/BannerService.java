package kg.BIZ.service;

import kg.BIZ.dto.request.BannerRequest;
import kg.BIZ.dto.response.BannerResponse;
import kg.BIZ.dto.response.SimpleResponse;

import java.util.List;

public interface BannerService {
    SimpleResponse save(BannerRequest request);
    SimpleResponse delete(Long id);
    List<BannerResponse> getAll();
}
