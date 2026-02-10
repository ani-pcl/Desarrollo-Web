/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author aniff
 */

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var categorias = categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Categoria categoria,
            BindingResult result,
            @RequestParam MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    messageSource.getMessage("categoria.error.form", null, Locale.getDefault())
            );
            return "redirect:/categoria/listado";
        }

        categoriaService.save(categoria, imagenFile);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault())
        );

        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer idCategoria,
            RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            categoriaService.delete(idCategoria);

        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "categoria.error1"; // no existe

        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "categoria.error2"; // datos asociados

        } catch (Exception e) {
            titulo = "error";
            detalle = "categoria.error3"; // error inesperado
        }

        redirectAttributes.addFlashAttribute(
                titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault())
        );

        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String modificar(
            @PathVariable Integer idCategoria,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(idCategoria);

        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    messageSource.getMessage("categoria.error1", null, Locale.getDefault())
            );
            return "redirect:/categoria/listado";
        }

        model.addAttribute("categoria", categoriaOpt.get());
        return "/categoria/modificar";
    }
}
