package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.repository.ProductoRepository;
import com.perfulandiaspa.service.ProductoService;

@SpringBootTest
@ActiveProfiles("test")
public class TestProductoService {
    @Autowired
    private ProductoService productoService;
    
    @Mock
    private ProductoRepository productoRepository;

    public Producto crearProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setTipo("Tipo Test");
        producto.setPrecio(3000D);
        producto.setMililitros(100);
        producto.setStock(50);
        producto.setCodigo("PROD123");
        return producto;
    }

    @Test
    public void testCrearProducto() {
        Producto producto = crearProducto();
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto productoCreado = productoService.crearProducto(producto);
        assertNotNull(productoCreado);
        assertEquals(producto.getId(), productoCreado.getId());
    }

    @Test
    public void testListarProductos() {
        Producto producto = crearProducto();
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.listarProductos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals(producto.getId(), productos.get(0).getId());
    }

    @Test
    public void testBuscarProductoPorId() {
        Producto producto = crearProducto();
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
