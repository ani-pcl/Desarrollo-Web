/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.repository;

import com.tienda.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    public List<Categoria> findByActivoTrue();
    
    //----------------------[practica 2]----------------------
    
    //metodo derivado
    List<Categoria> findByActivoTrueAndDescripcionContaining(String textoDescripcion);
    //findByActivoTrue: categorias activas //DescripcionContaining: que la descripcions sea
    
    
    @Query("SELECT c FROM Categoria c JOIN c.productos p " +
           "WHERE c.activo = true AND c.descripcion LIKE %:textoDescripcion% " +
           "AND p.activo = true " +
           "GROUP BY c " +
           "HAVING COUNT(p) >= 1 " +
           "ORDER BY COUNT(p) DESC")
    List<Categoria> consultaCategoriaJPQL(@Param("textoDescripcion") String textoDescripcion);

    // SQL nativa
    @Query(nativeQuery = true, value = "SELECT c.* FROM categoria c " +
                   "JOIN producto p ON c.id_categoria = p.id_categoria " +
                   "WHERE c.activo = true AND c.descripcion LIKE %:textoDescripcion% " +
                   "AND p.activo = true " +
                   "GROUP BY c.id_categoria " +
                   "HAVING COUNT(p.id_producto) >= 1 " +
                   "ORDER BY COUNT(p.id_producto) DESC"
           )
    List<Categoria> consultaCategoriaSQL(@Param("textoDescripcion") String textoDescripcion);
}


