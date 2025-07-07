package com.perfulandiaspa.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.repository.ProductoRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class TestProductoService {
    @InjectMocks
    private ProductoService productoService;
    
    @Mock
    private ProductoRepository productoRepository;

    private Faker faker;
    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testCrearProducto() {
        Producto producto = Producto.builder().id(faker.number().randomNumber(6))
                .nombre(faker.commerce().productName())
                .tipo("EDP")
                .precio(faker.number().randomDouble(2, 10000, 100000))
                .mililitros(faker.number().numberBetween(30, 200))
                .stock(faker.number().numberBetween(0, 100))
                .codigo(faker.code().ean8())
                .build();
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto productoCreado = productoService.crearProducto(producto);
        assertNotNull(productoCreado);
        assertEquals(producto.getId(), productoCreado.getId());
    }

    @Test
    public void testListarProductos() {
                Producto producto = Producto.builder().id(faker.number().randomNumber(6))
                .nombre(faker.commerce().productName())
                .tipo("EDP")
                .precio(faker.number().randomDouble(2, 10000, 100000))
                .mililitros(faker.number().numberBetween(30, 200))
                .stock(faker.number().numberBetween(0, 100))
                .codigo(faker.code().ean8())
                .build();
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.listarProductos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals(producto.getId(), productos.get(0).getId());
    }

    @Test
    public void testBuscarProductoPorId() {
        Producto producto = Producto.builder().id(1L)
                .nombre(faker.commerce().productName())
                .tipo("EDP")
                .precio(faker.number().randomDouble(2, 10000, 100000))
                .mililitros(faker.number().numberBetween(30, 200))
                .stock(faker.number().numberBetween(0, 100))
                .codigo(faker.code().ean8())
                .build();
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto productoEncontrado = productoService.buscarProductoPorId(id);
        assertNotNull(productoEncontrado);
        assertEquals(id, productoEncontrado.getId());
    }

    @Test
    public void testEliminarProducto() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminarProducto(id);
        verify(productoRepository, times(1)).deleteById(id);
    }
    
}
