package kg.BIZ.service.impl;

import kg.BIZ.dto.request.BannerRequest;
import kg.BIZ.dto.response.BannerResponse;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.Banner;
import kg.BIZ.repository.BannerRepository;
import kg.BIZ.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public SimpleResponse save(BannerRequest request) {

        Banner banner = new Banner();
        banner.setImage(request.image());
        bannerRepository.save(banner);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("banner successfully saved!!!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        bannerRepository.findById(id).orElseThrow(()-> new NotFoundException("banner with id not founf: " + id));
        bannerRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("banner successfully saved!!!")
                .build();
    }

    @Override
    public List<BannerResponse> getAll() {
        List<BannerResponse> getALl = new ArrayList<>();
        List<Banner> banners = bannerRepository.findAll();

        for (Banner banner : banners) {
            BannerResponse bannerResponse = new BannerResponse(banner.getId(), banner.getImage());
            getALl.add(bannerResponse);
        }

        return getALl;
    }


}
