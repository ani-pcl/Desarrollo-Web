/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    public List<Producto> findByActivoTrue();
    
    //esto es una consulta desrivada
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    //Ejemplo de método utilizando consultas JPQL
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    //Ejemplo de método utilizando consultas SQL nativas
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
    
    //--------------------------[PRACTICA 2]--------------------------
    
    //Consulta derivada
    public List<Producto> findByActivoTrueAndPrecioBetweenAndExistenciasGreaterThanAndCategoria_ActivoTrueAndCategoria_DescripcionContainingOrderByPrecioAsc(
    double precioMin, double precioMax, int existenciasMin, String descripcionCategoria);
    //1.findByActivoTrue: que el producto este activo 2.PrecioBetween: el precio este entre 2 parametros. 3.ExistenciasGreaterThan: que haya un minimo de productos
    //4.Categoria_ActivoTrue:la categoria este activa. 5.Categoria_DescripcionContains: que haya una desc en especifico. 6.OrderByPrecioAsc: que se ordene conforme el precio
    
    //consulta de JPQL
    @Query("SELECT p FROM Producto p JOIN p.categoria c "
            + "WHERE p.activo = true AND p.precio BETWEEN :precioMin AND :precioMax "
            + "AND p.existencias > :existenciasMin AND c.activo = true "
            + "AND c.descripcion LIKE %:descripcionCategoria% "
            + "ORDER BY p.precio ASC")
    public List<Producto> consultaJPQLPractica(@Param("precioMin") double precioMin, @Param("precioMax") double precioMax,
            @Param("existenciasMin") int existenciasMin, @Param("descripcionCategoria") String descripcionCategoria);
    
    //SQL
    @Query(nativeQuery = true, value = "SELECT p.* FROM producto p "
            + "JOIN categoria c ON p.id_categoria = c.id_categoria "
            + "WHERE p.activo = true AND p.precio BETWEEN :precioMin AND :precioMax "
            + "AND p.existencias > :existenciasMin AND c.activo = true "
            + "AND c.descripcion LIKE %:descripcionCategoria% "
            + "ORDER BY p.precio ASC")
    public List<Producto> consultaSQLPractica(@Param("precioMin") double precioMin, @Param("precioMax") double precioMax,
            @Param("existenciasMin") int existenciasMin, @Param("descripcionCategoria") String descripcionCategoria);
    
}
