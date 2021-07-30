package br.com.rolim.muxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rolim.muxi.entity.Terminal;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>{
}
