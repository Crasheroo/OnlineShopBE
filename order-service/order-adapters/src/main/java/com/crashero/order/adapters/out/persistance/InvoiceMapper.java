package com.crashero.order.adapters.out.persistance;

import com.crashero.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceEntity toEntity(Invoice invoice);

    Invoice toDomain(InvoiceEntity invoiceEntity);
}
