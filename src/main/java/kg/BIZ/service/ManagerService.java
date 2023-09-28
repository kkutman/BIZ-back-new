package kg.BIZ.service;

import kg.BIZ.dto.request.AboutCompanyRequest;
import kg.BIZ.dto.response.SimpleResponse;

public interface ManagerService {
    SimpleResponse aboutCompany(AboutCompanyRequest aboutCompany);
}
