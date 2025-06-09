package com.natanconstrutora.service;

import com.natanconstrutora.model.Auditoria;
import com.natanconstrutora.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Auditoria> listarRegistros() {
        return auditoriaRepository.findAll();
    }

    public List<Auditoria> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return auditoriaRepository.findByDataBetween(dataInicio, dataFim);
    }

    public List<Auditoria> buscarPorUsuario(Long usuarioId) {
        return auditoriaRepository.findByUsuarioId(usuarioId);
    }

    public List<Auditoria> buscarPorTipo(String tipo) {
        return auditoriaRepository.findByTipo(tipo);
    }

    @Transactional
    public void limparRegistrosAntigos(LocalDate dataLimite) {
        auditoriaRepository.deleteByDataBefore(dataLimite);
    }

    @Transactional
    public Auditoria registrar(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }
} 