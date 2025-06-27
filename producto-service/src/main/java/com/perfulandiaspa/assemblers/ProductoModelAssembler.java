package com.perfulandiaspa.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.perfulandiaspa.controller.ProductoController;
import com.perfulandiaspa.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler <Producto, EntityModel<Producto>>{
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
        linkTo(methodOn(ProductoController.class).getProductoById(producto.getId())).withSelfRel(),
        linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("productos"));
    }
}
