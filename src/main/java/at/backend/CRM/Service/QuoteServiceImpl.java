package at.backend.CRM.Service;

import at.backend.CRM.Inputs.QuoteInput;
import at.backend.CRM.Inputs.QuoteItemInput;
import at.backend.CRM.Mappers.QuoteMappers;
import at.backend.CRM.Models.*;
import at.backend.CRM.Repository.*;
import at.backend.CRM.Utils.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements  CommonService<Quote, QuoteInput>{

    public final QuoteRepository quoteRepository;
    public final CustomerRepository customerRepository;
    public final OpportunityRepository opportunityRepository;
    public final ServicePackageRepository servicePackageRepository;
    public final QuoteMappers quoteMappers;

    @Override
    public Page<Quote> findAll(Pageable pageable) {
        return quoteRepository.findAll(pageable);
    }

    @Override
    public Optional<Quote> findById(Long id) {
        return quoteRepository.findById(id);
    }

    @Override
    @Transactional
    public Quote create(QuoteInput input) {
        Quote newQuote = quoteMappers.inputToEntity(input);

        newQuote.setCustomer(getCustomer(input.customerId()));
        newQuote.setOpportunity(getOpportunity(input.opportunityId()));

        List<QuoteItem> items = generateItems(newQuote, input.items());
        newQuote.setItems(items);

        calculateNumbers(newQuote);

        return quoteRepository.saveAndFlush(newQuote);
    }

    @Override
    @Transactional
    public Quote update(Long id, QuoteInput input) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean isQuoteExisting = quoteRepository.existsById(id);
        if (!isQuoteExisting) {
            throw new EntityNotFoundException("quote not found");
        }

        quoteRepository.deleteById(id);
    }

    @Override
    public void validate(QuoteInput input) {
        LocalDate validUntil = input.validUntil();
        LocalDate validUntilLimit = LocalDate.now().plusMonths(10);

        if (validUntil.isAfter(validUntilLimit)) {
            throw new BusinessLogicException("The 'valid until' date exceeds the allowed limit. The maximum duration is 10 months from today.");
        }
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    private Opportunity getOpportunity(Long opportunityId) {
        return opportunityRepository.findById(opportunityId).orElseThrow(() -> new EntityNotFoundException("Opportunity not found"));
    }

    private List<QuoteItem> generateItems(Quote createdQuote, List<QuoteItemInput> inputs) {
        List<Long> servicePackageIds = inputs.stream()
                .map(QuoteItemInput::servicePackageId)
                .toList();
        Map<Long, ServicePackage> servicePackages = servicePackageRepository.findAllById(servicePackageIds)
                .stream()
                .collect(Collectors.toMap(ServicePackage::getId, servicePackage -> servicePackage));

        return inputs.stream()
                .map(input -> {
                    QuoteItem item =  new QuoteItem();
                    item.setQuote(createdQuote);

                    ServicePackage servicePackage = servicePackages.get(input.servicePackageId());
                    if (servicePackage == null) {
                        throw new EntityNotFoundException("ServicePackage not found for ID: " + input.servicePackageId());
                    }
                    item.setServicePackage(servicePackage);

                    calculateItemNumbers(item);
                    return item;
                }).toList();
    }


    private void calculateNumbers(Quote quote) {
        List<QuoteItem> items = quote.getItems();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Cannot calculate total amount for a quote with no items.");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (var item : items) {
            calculateItemNumbers(item);

            subtotal = subtotal.add(item.getUnitPrice());
            discount = discount.add(item.getDiscount());
            total = total.add(item.getTotal());
        }

        quote.setSubTotal(subtotal);
        quote.setDiscount(discount);
        quote.setTotalAmount(total);
    }

    private void calculateItemNumbers(QuoteItem item) {
        BigDecimal unitPrice = item.getServicePackage().getPrice();
        item.setUnitPrice(unitPrice);

        BigDecimal discountPercentage = item.getDiscountPercentage();
        BigDecimal discount = unitPrice.multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal total = unitPrice.subtract(discount);

        item.setDiscount(discount);
        item.setTotal(total);
    }

}
