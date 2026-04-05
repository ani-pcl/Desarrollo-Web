/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

/**
 *
 * @author aniff
 */
import com.tienda.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//-------------------------[PRACTICA 2]----------------

@Controller
@RequestMapping("/consultaCategoria")
public class ConsultaCategoriaController {
    
    private final CategoriaService categoriaService;

    public ConsultaCategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = categoriaService.getCategorias(false);//activas y no activas
        model.addAttribute("categorias", lista);
        return "/consultaCategoria/listado";
    }
    
    @PostMapping("/consultaDerivadaCategoria")
    public String consultaDerivadaCategoria(@RequestParam() String textoDescripcion, Model model) {
        var lista = categoriaService.consultaDerivadaCategoria(textoDescripcion);
        model.addAttribute("categorias", lista);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultaCategoria/listado";
    }
    
    @PostMapping("/consultaCategoriaJPQL")
    public String consultaCategoriaJPQL(@RequestParam() String textoDescripcion, Model model) {
        var lista = categoriaService.consultaCategoriaJPQL(textoDescripcion);
        model.addAttribute("categorias", lista);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultaCategoria/listado";
    }

    @PostMapping("/consultaCategoriaSQL")
    public String consultaCategoriaSQL(@RequestParam() String textoDescripcion, Model model) {
        var lista = categoriaService.consultaCategoriaSQL(textoDescripcion);
        model.addAttribute("categorias", lista);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultaCategoria/listado";
    }
}
