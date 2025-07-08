package com.perfulandiaspa.controller;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaspa.assemblers.ProductoModelAssembler;
import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.service.ProductoService;

@WebMvcTest(controllers = ProductoController.class)
@Import({ProductoModelAssembler.class})
public class TestProductoController {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach
    public void setUp() {
        producto = Producto.builder()
                .id(1L)
                .nombre("Perfume Test")
                .tipo("EDP")
                .precio(49990.0)
                .mililitros(100)
                .stock(20)
                .codigo("PERF001")
                .build();
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.crearProducto(any(Producto.class)))
            .thenReturn(producto);
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto).getBytes()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(org.hamcrest.Matchers.is(producto.getNombre())))
                .andExpect(jsonPath("$.tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$.mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$.stock").value(org.hamcrest.Matchers.is(producto.getStock())))
                .andExpect(jsonPath("$.codigo").value(producto.getCodigo()));
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.listarProductos()).thenReturn(List.of(producto));
        mockMvc.perform(get("/api/productos")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productoList[0].id").value(producto.getId()))
                .andExpect(jsonPath("$._embedded.productoList[0].nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$._embedded.productoList[0].tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$._embedded.productoList[0].precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$._embedded.productoList[0].mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$._embedded.productoList[0].stock").value(org.hamcrest.Matchers.is(producto.getStock())))
                .andExpect(jsonPath("$._embedded.productoList[0].codigo").value(producto.getCodigo()));
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.buscarProductoPorId(1L)).thenReturn((producto));
        mockMvc.perform(get("/api/productos/1")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$.tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$.mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$.stock").value(producto.getStock()))
                .andExpect(jsonPath("$.codigo").value(producto.getCodigo()));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        producto.setNombre("Nombre actualizado");
        when(productoService.actualizarProducto(eq(1L), any(Producto.class)))
            .thenReturn(producto);
        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value("Nombre actualizado"))
                .andExpect(jsonPath("$.tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$.mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$.stock").value(producto.getStock()))
                .andExpect(jsonPath("$.codigo").value(producto.getCodigo()));
    }
    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).eliminarProducto(1L);
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
        verify(productoService, times(1)).eliminarProducto(1L);
    }

    @Test
    public void testGetProductosSimilares() throws Exception {
        when(productoService.buscarProductoPorId(1L)).thenReturn(producto);
        when(productoService.obtenerProductosSimilares("EDP", 1L)).thenReturn(List.of(producto));
        mockMvc.perform(get("/api/productos/1/similares")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productoList[0].id").value(producto.getId()))
                .andExpect(jsonPath("$._embedded.productoList[0].nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$._embedded.productoList[0].tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$._embedded.productoList[0].precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$._embedded.productoList[0].mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$._embedded.productoList[0].stock").value(producto.getStock()))
                .andExpect(jsonPath("$._embedded.productoList[0].codigo").value(producto.getCodigo()));
    }
}
