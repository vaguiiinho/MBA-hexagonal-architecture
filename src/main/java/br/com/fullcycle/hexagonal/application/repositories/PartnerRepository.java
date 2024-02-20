package br.com.fullcycle.hexagonal.application.repositories;

import java.util.Optional;

import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(PartnerId anId);

    Optional<Partner> partnerOfCNPJ(String cnpj);

    Optional<Partner> partnerOfEmail(String email);

    Partner create(Partner partner);

    Partner update(Partner partner);

    void deleteAll();
}