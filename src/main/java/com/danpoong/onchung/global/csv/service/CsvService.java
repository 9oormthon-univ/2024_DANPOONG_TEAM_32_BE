package com.danpoong.onchung.global.csv.service;

import com.danpoong.onchung.domain.policy.domain.FilteringDetails;
import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.policy.repository.PolicyRepository;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.public_office.repository.PublicOfficeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvService {
    private final PolicyRepository policyRepository;
    private final PublicOfficeRepository publicOfficeRepository;

    @Transactional
    public void loadDataFromCSV(MultipartFile file) {
        List<Policy> policyList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;

            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                List<PublicOffice> publicOffices = extractPublicOffices(row);
                FilteringDetails filteringDetails = createFilteringDetails(row);
                Policy policy = createPolicy(row, publicOffices, filteringDetails);

                for (PublicOffice publicOffice : publicOffices) {
                    publicOffice.getPolicies().add(policy);
                }

                policyList.add(policy);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!policyList.isEmpty()) {
                policyRepository.saveAll(policyList);
            }
        }
    }

    private List<PublicOffice> extractPublicOffices(Row row) {
        List<PublicOffice> publicOffices = new ArrayList<>();
        String cellValue = getCellValue(row, 28);
        String[] values = cellValue.contains(",") ? cellValue.split(",") : new String[]{cellValue};

        for (String value : values) {
            PublicOffice publicOffice = publicOfficeRepository.findByName(value)
                    .orElseGet(() -> {
                        PublicOffice newPublicOffice = PublicOffice.builder()
                                .name(value)
                                .build();

                        return publicOfficeRepository.save(newPublicOffice);
                    });

            publicOffices.add(publicOffice);
        }

        return publicOffices;
    }

    private String getPhoneNumber(Row row) {
        return !getCellValue(row, 30).isEmpty() && getCellValue(row, 30).contains("-")
                ? getCellValue(row, 30)
                : getCellValue(row, 27);
    }

    private FilteringDetails createFilteringDetails(Row row) {
        return FilteringDetails.builder()
                .ageInfo(getCellValue(row, 11))
                .employmentStatus(getCellValue(row, 13))
                .specializationField(getCellValue(row, 14))
                .educationRequirement(getCellValue(row, 15))
                .build();
    }

    private Policy createPolicy(Row row, List<PublicOffice> publicOffices, FilteringDetails filteringDetails) {
        return Policy.builder()
                .name(getCellValue(row, 4))
                .introduction(getCellValue(row, 5))
                .supportDetails(getCellValue(row, 6))
                .supportScale(getCellValue(row, 7))
                .supportTarget(getSupportTarget(row))
                .applicationPeriod(getCellValue(row, 8))
                .applicationProcedure(getCellValue(row, 19))
                .requiredDocuments(getCellValue(row, 20))
                .applicationSite(getCellValue(row, 22))
                .contactInfo(getPhoneNumber(row))
                .filteringDetails(filteringDetails)
                .category(getCellValue(row, 32))
                .publicOffices(publicOffices)
                .build();
    }

    private String getSupportTarget(Row row) {
        return !getCellValue(row, 16).isEmpty() ? getCellValue(row, 16) : getCellValue(row, 17);
    }

    private String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        return cell == null ? "" : cell.toString().trim();
    }
}
