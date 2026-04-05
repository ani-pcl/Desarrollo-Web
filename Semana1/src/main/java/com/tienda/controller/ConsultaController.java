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
import com.tienda.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ProductoService productoService;
    
    //se cambia el constructor a uno doble
    public ConsultaController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);
        return "/consultas/listado";
    }

    @PostMapping("/consultaDerivada")
    public String consultaDerivada(@RequestParam() double precioInf,
            @RequestParam() double precioSup, Model model) {
        var lista = productoService.consultaDerivada(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }
    
    @PostMapping("/consultaJPQL")
    public String consultaJPQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup, Model model) {
        var lista = productoService.consultaJPQL(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    @PostMapping("/consultaSQL")
    public String consultaSQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup, Model model) {
        var lista = productoService.consultaSQL(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }
    
    //----------------------------[practica2]----------------------
    
    @PostMapping("/consultaDerivadaPractica")
    public String consultaDerivadaPractica(@RequestParam() double precioMin,
            @RequestParam() double precioMax,
            @RequestParam() int existenciasMin,
            @RequestParam() String descripcionCategoria, Model model){
        var lista = productoService.consultaDerivadaPractica(precioMin, precioMax, existenciasMin, descripcionCategoria);
        model.addAttribute("productos", lista);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/listado";
    }
    
    @PostMapping("/consultaJPQLPractica")
    public String consultaJPQLPractica(@RequestParam() double precioMin,
            @RequestParam() double precioMax,
            @RequestParam() int existenciasMin,
            @RequestParam() String descripcionCategoria, Model model){
        var lista = productoService.consultaJPQLPractica(precioMin, precioMax, existenciasMin, descripcionCategoria);
        model.addAttribute("productos", lista);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/listado";
    }
    
    @PostMapping("/consultaSQLPractica")
    public String consultaSQLPractica(@RequestParam() double precioMin,
            @RequestParam() double precioMax,
            @RequestParam() int existenciasMin,
            @RequestParam() String descripcionCategoria, Model model){
        var lista = productoService.consultaSQLPractica(precioMin, precioMax, existenciasMin, descripcionCategoria);
        model.addAttribute("productos", lista);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/listado";
    }
    
    
    
}
