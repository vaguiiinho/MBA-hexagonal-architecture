package br.com.fullcycle.hexagonal.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import br.com.fullcycle.hexagonal.application.entities.Partner;
import br.com.fullcycle.hexagonal.application.entities.PartnerId;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

public class InMemoryPartnerRepository implements PartnerRepository {

    private final Map<String, Partner> partners;
    private final Map<String, Partner> partnersByCPF;
    private final Map<String, Partner> partnersByEmail;

    public InMemoryPartnerRepository() {
        this.partners = new HashMap<>();
        this.partnersByCPF = new HashMap<>();
        this.partnersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId anId) {
        return Optional.ofNullable(this.partners.get(Objects.requireNonNull(anId).value().toString()));
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(String cpf) {
        return Optional.ofNullable(this.partnersByCPF.get(Objects.requireNonNull(cpf)));
    }

    @Override
    public Optional<Partner> partnerOfEmail(String email) {
        return Optional.ofNullable(this.partnersByEmail.get(Objects.requireNonNull(email)));
    }

    @Override
    public Partner create(Partner partner) {
        this.partners.put(partner.partnerId().value().toString(), partner);
        this.partnersByCPF.put(partner.cnpj().value(), partner);
        this.partnersByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public Partner update(Partner partner) {
        this.partners.put(partner.partnerId().value().toString(), partner);
        this.partnersByCPF.put(partner.cnpj().value(), partner);
        this.partnersByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public void deleteAll() {
        this.partners.clear();
        this.partnersByCPF.clear();
        this.partnersByEmail.clear();
    }
}