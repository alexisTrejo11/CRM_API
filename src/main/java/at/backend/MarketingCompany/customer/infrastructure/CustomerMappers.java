package at.backend.MarketingCompany.customer.infrastructure;

import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import at.backend.MarketingCompany.customer.domain.*;
import at.backend.MarketingCompany.customer.domain.ValueObjects.BusinessProfile;
import at.backend.MarketingCompany.customer.domain.ValueObjects.ContactDetails;
import at.backend.MarketingCompany.customer.domain.ValueObjects.CustomerId;
import at.backend.MarketingCompany.customer.domain.ValueObjects.PersonalInfo;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class CustomerMappers {

    public static CustomerModel domainToModel(Customer domain) {
        CustomerModel model = new CustomerModel();
        model.setId(domain.getId().value());
        model.setFirstName(domain.getPersonalInfo().getFirstName());
        model.setLastName(domain.getPersonalInfo().getLastName());
        model.setEmail(domain.getContactDetails().getEmail());
        model.setPhone(domain.getContactDetails().getPhone());
        model.setCompany(domain.getBusinessProfile().getCompany());
        model.setIndustry(domain.getBusinessProfile().getIndustry());
        model.setBrandVoice(domain.getBusinessProfile().getBrandVoice());
        model.setBrandColors(domain.getBusinessProfile().getBrandColors());
        model.setTargetMarket(domain.getBusinessProfile().getTargetMarket());
        model.setCompetitorUrls(domain.getBusinessProfile().getCompetitorUrls());
        model.setSocialMediaHandles(domain.getBusinessProfile().getSocialMediaHandles());
        model.setCreatedAt(domain.getCreatedAt());
        model.setUpdatedAt(domain.getUpdatedAt());
        return model;
    }

    public static Customer modelToDomain(CustomerModel model) {
        return Customer.builder()
                .id(CustomerId.of(model.getId()))
                .personalInfo(PersonalInfo.builder()
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .build())
                .contactDetails(ContactDetails.builder()
                        .email(model.getEmail())
                        .phone(model.getPhone())
                        .build())
                .businessProfile(BusinessProfile.builder()
                        .company(model.getCompany())
                        .industry(model.getIndustry())
                        .brandVoice(model.getBrandVoice())
                        .brandColors(model.getBrandColors())
                        .targetMarket(model.getTargetMarket())
                        .competitorUrls(model.getCompetitorUrls())
                        .socialMediaHandles(model.getSocialMediaHandles())
                        .build())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }

    public static Customer insertDTOToDomain(CustomerInsertDTO dto) {
        return Customer.builder()
                .id(CustomerId.generate())
                .personalInfo(PersonalInfo.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .build())
                .contactDetails(ContactDetails.builder()
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .build())
                .businessProfile(BusinessProfile.builder()
                        .company(dto.getCompany())
                        .industry(dto.getIndustry())
                        .brandVoice(dto.getBrandVoice())
                        .brandColors(dto.getBrandColors())
                        .targetMarket(dto.getTargetMarket())
                        .competitorUrls(dto.getCompetitorUrls())
                        .socialMediaHandles(dto.getSocialMediaHandles())
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static CustomerDTO domainToDTO(Customer domain) {
        return CustomerDTO.builder()
                .id(domain.getId().value())
                .firstName(domain.getPersonalInfo().getFirstName())
                .lastName(domain.getPersonalInfo().getLastName())
                .email(domain.getContactDetails().getEmail())
                .phone(domain.getContactDetails().getPhone())
                .company(domain.getBusinessProfile().getCompany())
                .industry(domain.getBusinessProfile().getIndustry())
                .brandVoice(domain.getBusinessProfile().getBrandVoice())
                .brandColors(domain.getBusinessProfile().getBrandColors())
                .targetMarket(domain.getBusinessProfile().getTargetMarket())
                .competitorUrls(domain.getBusinessProfile().getCompetitorUrls())
                .socialMediaHandles(domain.getBusinessProfile().getSocialMediaHandles())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static CustomerDTO modelToDTO(CustomerModel model) {
        return CustomerDTO.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .company(model.getCompany())
                .industry(model.getIndustry())
                .brandVoice(model.getBrandVoice())
                .brandColors(model.getBrandColors())
                .targetMarket(model.getTargetMarket())
                .competitorUrls(model.getCompetitorUrls())
                .socialMediaHandles(model.getSocialMediaHandles())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }
}