package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.BIZ.dto.request.BannerRequest;
import kg.BIZ.dto.response.BannerResponse;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
@Tag(name = "Banner")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BannerApi {
    private final BannerService service;

    @PostMapping
    @Operation(summary = "Save banner", description = "This is the method to save banner")
    public SimpleResponse save(@RequestBody BannerRequest request){
        return service.save(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete banner", description = "This is the method to delete banner")
    public SimpleResponse delete(@PathVariable Long id){
        return service.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get all banners", description = "This is the method to return all banners")
    public List<BannerResponse> getAll(){
        return service.getAll();
    }
}
