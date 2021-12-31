package com.ezshipp.api.persistence.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ezshipp.api.enums.InvoiceStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_invoice",  catalog = "")
@Setter
@Getter
public class CustomerInvoiceEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "from_date", nullable = false)
	    private Date fromDate;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "to_date", nullable = false)
	    private Date toDate;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "due_date", nullable = false)
	    private Date dueDate;
	    @Column(name = "invoice_number", nullable = false)
	    private String invoiceNumber;
	    @Column(name = "total_order_count", nullable = false)
	    private int totalOrderCount;
	    @Column(name = "sameday_count", nullable = false)
	    private int sameDayCount;
	    @Column(name = "four_hour_count", nullable = false)
	    private int fourHourCount;
	    @Column(name = "instant_count", nullable = false)
	    private int instantCount;
	    @Column(name = "range1_count", nullable = false)
	    private int range1Count;
	    @Column(name = "range2_count", nullable = false)
	    private int range2Count;
	    @Column(name = "range3_count", nullable = false)
	    private int range3Count;
	    @Column(name = "range4_count", nullable = false)
	    private int range4Count;
	    @Column(name = "final_range_count", nullable = false)
	    private int finalRangeCount;
	    @Column(name = "invoice_amount", nullable = false)
	    private Double invoiceAmount;
	    @Column(name = "tax_sgst", nullable = false)
	    private Double sgstTaxAmount;
	    @Column(name = "tax_cgst", nullable = false)
	    private Double cgstTaxAmount;
	    @Column(name = "total_invoice_amount", nullable = false)
	    private Double totalInvoiceAmount;
	    @Column(name = "four_hour_invoice_amount", nullable = false)
	    private Double fourHourInvoiceAmount;
	    @Column(name = "sameday_invoice_amount", nullable = false)
	    private Double sameDayInvoiceAmount;
	    @Column(name = "instant_invoice_amount", nullable = false)
	    private Double InstantInvoiceAmount;
	    @Column(name = "range1_invoice_amount", nullable = false)
	    private Double range1InvoiceAmount;
	    @Column(name = "range2_invoice_amount", nullable = false)
	    private Double range2InvoiceAmount;
	    @Column(name = "range3_invoice_amount", nullable = false)
	    private Double range3InvoiceAmount;
	    @Column(name = "range4_invoice_amount", nullable = false)
	    private Double range4InvoiceAmount;
	    @Column(name = "final_range_invoice_amount", nullable = false)
	    private Double finalRangeInvoiceAmount;
	    @Column(name = "extra_weight_amount", nullable = false)
	    private Double extraWeightAmount;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "status")
	    private InvoiceStatus status;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "paid_date", nullable = false)
	    private Date paidDate;
	    @Column(name = "reference_number", nullable = false)
	    private String referenceNumber;

	    @ManyToOne
	    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	    private CustomerEntity customerByCustomerId;

	    @Column(name = "customer_id", nullable = false, updatable = false, insertable = false)
	    private int customerId;
}
