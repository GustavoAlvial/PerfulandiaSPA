package com.perfulandiaspa.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandiaspa.assemblers.ProductoModelAssembler;
import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.listarProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

                return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getAllProductos()).withSelfRel());
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico por su ID.")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.buscarProductoPorId(id);
        return assembler.toModel(producto);
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto.")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.crearProducto(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).getProductoById(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente.")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID.")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}