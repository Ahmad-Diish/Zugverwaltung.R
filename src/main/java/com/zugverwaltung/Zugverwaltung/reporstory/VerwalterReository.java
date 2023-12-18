package com.zugverwaltung.Zugverwaltung.reporstory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zugverwaltung.Zugverwaltung.entity.info;

public interface VerwalterReository extends JpaRepository<info, Integer>{

    
} 
