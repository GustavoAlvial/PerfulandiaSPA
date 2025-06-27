package controller;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.perfulandiaspa.controller.ProductoController;
import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class TestProductoController {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre(faker.commerce().productName());
        producto.setTipo(faker.options().option("edp", "edt"));
        producto.setPrecio(Double.valueOf(faker.commerce().price()));
        producto.setMililitros(faker.number().numberBetween(30, 200));
        producto.setStock(faker.number().numberBetween(1, 100));
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.crearProducto(any(Producto.class)))
            .thenReturn(producto);
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
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
    public void testGetAllProductos() throws Exception {
        when(productoService.listarProductos()).thenReturn(List.of(producto));
        mockMvc.perform(get("/api/productos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(producto.getId()))
                .andExpect(jsonPath("$[0].nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$[0].tipo").value(producto.getTipo()))
                .andExpect(jsonPath("$[0].precio").value(producto.getPrecio()))
                .andExpect(jsonPath("$[0]mililitros").value(producto.getMililitros()))
                .andExpect(jsonPath("$[0].stock").value(producto.getStock()))
                .andExpect(jsonPath("$[0].codigo").value(producto.getCodigo()));
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.buscarProductoPorId(1L)).thenReturn(java.util.Optional.of(producto));
        mockMvc.perform(get("/api/productos/1")
                .accept(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(post("/api/productos/1")
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
                .andExpect(status().isOk());
        verify(productoService, times(1)).eliminarProducto(1L);
    }
}
