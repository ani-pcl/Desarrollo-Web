/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service;

import com.tienda.repository.CategoriaRepository;
import com.tienda.domain.Categoria;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author aniff
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile) {
        categoria = categoriaRepository.save(categoria);

        if (imagenFile.isEmpty()) {
            return;
        }

        try {
            String rutaImagen = firebaseStorageService.uploadImage(
                    imagenFile,
                    "categoria",
                    categoria.getIdCategoria()
            );
            categoria.setRutaImagen(rutaImagen);
            categoriaRepository.save(categoria);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }

    @Transactional
    public void delete(Integer idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new IllegalArgumentException(
                    "La categoria con ID " + idCategoria + " no existe"
            );
        }

        try {
            categoriaRepository.deleteById(idCategoria);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar la categoria, tiene datos asociados", e
            );
        }
    }
}
