package com.effecti.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Map;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Bidding {

    @Id
    @GeneratedValue(strategy = AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(unique = true)
    private int hash;

    private String publicationNumber;
    private String status;
    private String viprocNumber;
    private String contractObject;
    private String noticeNumberAndContractorAndDelivery;
    private String systematicsAndAcquisitionForm;
    private String receptionAndOpening;

    public Bidding(Map<String, String> biddingMap) {
        setPublicationNumber(biddingMap.get("Nº da Publicação"));
        setStatus(biddingMap.get("Status"));
        setViprocNumber(biddingMap.get("Nº Viproc"));
        setContractObject(biddingMap.get("Objeto da Contratação"));
        setNoticeNumberAndContractorAndDelivery(biddingMap.get("Nº Edital - Contratante - Entrega"));
        setSystematicsAndAcquisitionForm(biddingMap.get("Sistemática - Forma de Aquisição"));
        setReceptionAndOpening(biddingMap.get("Acolhimento - Abertura"));
        setHash(this.hashCode());
    }

    public Bidding(String publicationNumber, String status, String viprocNumber, String contractObject,
                   String noticeNumberAndContractorAndDelivery, String systematicsAndAcquisitionForm,
                   String receptionAndOpening) {
        setPublicationNumber(publicationNumber);
        setStatus(status);
        setViprocNumber(viprocNumber);
        setContractObject(contractObject);
        setNoticeNumberAndContractorAndDelivery(noticeNumberAndContractorAndDelivery);
        setSystematicsAndAcquisitionForm(systematicsAndAcquisitionForm);
        setReceptionAndOpening(receptionAndOpening);
        setHash(this.hashCode());
    }
}
