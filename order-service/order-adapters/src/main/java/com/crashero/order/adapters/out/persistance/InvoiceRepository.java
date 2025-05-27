package com.crashero.order.adapters.out.persistance;

import com.crashero.core.service.InvoicePort;
import com.crashero.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class InvoiceRepository implements InvoicePort {
    private final SpringDataInvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);
        InvoiceEntity saved = invoiceRepository.save(entity);
        return invoiceMapper.toDomain(saved);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDomain);
    }

    @Override
    public List<Invoice> findAllByUserId(Long userId) {
        return invoiceRepository.findAllByUserId(userId).stream()
                .map(invoiceMapper::toDomain)
                .toList();
    }

//    private InvoiceEntity toEntity(Invoice invoice) {
//        if (invoice == null) {
//            return null;
//        }
//
//        InvoiceEntity entity = new InvoiceEntity();
//        entity.setId(invoice.getId());
//        entity.setUserId(invoice.getUserId());
//        entity.setOrderId(invoice.getOrderId());
//        entity.setAmount(invoice.getAmount());
//        entity.setIssuedAt(invoice.getIssuedAt());
//        return entity;
//    }
//
//    private Invoice toDomain(InvoiceEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        return Invoice.builder()
//                .id(entity.getId())
//                .userId(entity.getUserId())
//                .orderId(entity.getOrderId())
//                .amount(entity.getAmount())
//                .issuedAt(entity.getIssuedAt())
//                .build();
//    }
}
