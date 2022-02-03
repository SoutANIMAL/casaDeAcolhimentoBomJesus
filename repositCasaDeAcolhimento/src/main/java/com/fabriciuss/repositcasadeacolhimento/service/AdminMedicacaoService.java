package com.fabriciuss.repositcasadeacolhimento.service;

        import com.fabriciuss.repositcasadeacolhimento.domain.AdminMedicacao;
        import com.fabriciuss.repositcasadeacolhimento.repository.AdminMedicacaoRepository;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class AdminMedicacaoService {
    private final Logger log = LoggerFactory.getLogger(AdminMedicacaoService.class);

    private final AdminMedicacaoRepository adminMedicacaoRepository;

    public AdminMedicacaoService(AdminMedicacaoRepository adminMedicacaoRepository) {
        this.adminMedicacaoRepository = adminMedicacaoRepository;
    }

    public List<AdminMedicacao> findAllList(){
        return adminMedicacaoRepository.findAll();
    }

    public Optional<AdminMedicacao> findOne(Long id){
        log.debug("Request to get AdminMedicacao : {}", id);
        return adminMedicacaoRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete AdminMedicacao : {}", id);
        adminMedicacaoRepository.deleteById(id);
    }

    public AdminMedicacao save(AdminMedicacao adminMedicacao){
        log.debug("Request to save AdminMedicacao : {}", adminMedicacao);
        adminMedicacao = adminMedicacaoRepository.save(adminMedicacao);
        return adminMedicacao;
    }

    public List<AdminMedicacao> saveAll(List<AdminMedicacao> adminMedicacoes) {
        log.debug("Request to save AdminMedicacao : {}", adminMedicacoes);
        adminMedicacoes = adminMedicacaoRepository.saveAll(adminMedicacoes);
        return adminMedicacoes;
    }
}
