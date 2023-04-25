package com.jeanrivera.evaluacion1;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.repositories.ProveedorRepository;
import com.jeanrivera.evaluacion1.services.ProveedorServices;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProveedorServiceTests {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorServices proveedorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listadoProveedoresTest() {
        // Se crea una lista con 2 proveedores
        List<Proveedor> proveedores = new ArrayList<>();
        Proveedor proveedor1 = new Proveedor();
        proveedor1.setCodigo("10001");
        proveedor1.setNombre("Jose Fernandez");
        proveedor1.setCategoria("A");
        proveedor1.setRetencion("No");
        proveedores.add(proveedor1);
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setCodigo("10002");
        proveedor2.setNombre("Maria Rodriguez");
        proveedor2.setCategoria("B");
        proveedor2.setRetencion("Si");
        proveedores.add(proveedor2);

        // Se configura el comportamiento del repositorio al llamar al método findAll
        when(proveedorRepository.findAll()).thenReturn(proveedores);

        // Se llama al método a probar
        List<Proveedor> result = proveedorService.listadoProveedores();

        // Se verifica que el resultado sea igual a la lista original de proveedores
        Assert.assertEquals(proveedores, result);

        // Se verifica que se haya llamado al método findAll del repositorio exactamente una vez
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    public void crearProveedorTest() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("10001");
        proveedor.setNombre("Jose Fernandez");
        proveedor.setCategoria("A");
        proveedor.setRetencion("No");
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        Proveedor result = proveedorService.crearProveedor(proveedor);

        Assert.assertNotNull(result);
        Assert.assertEquals(proveedor.getCodigo(), result.getCodigo());
        Assert.assertEquals(proveedor.getNombre(), result.getNombre());
        Assert.assertEquals(proveedor.getCategoria(), result.getCategoria());
        Assert.assertEquals(proveedor.getRetencion(), result.getRetencion());
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    public void actualizarProveedorTest() {
        // Preparación de datos de prueba
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("00001");
        proveedor.setNombre("Jose Fernandez");
        proveedor.setCategoria("A");
        proveedor.setRetencion("No");

        // Configuración del comportamiento del mock
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        // Ejecución del método a probar
        Proveedor result = proveedorService.actualizarProveedor(proveedor);

        // Verificación del resultado
        Assert.assertNotNull(result);
        Assert.assertEquals(proveedor.getCodigo(), result.getCodigo());
        Assert.assertEquals(proveedor.getNombre(), result.getNombre());
        Assert.assertEquals(proveedor.getCategoria(), result.getCategoria());
        Assert.assertEquals(proveedor.getRetencion(), result.getRetencion());

        // Verificación de que se haya llamado al método save() del proveedorRepository una sola vez
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    public void eliminarProveedorTest() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("10001");
        proveedor.setNombre("Jose Fernandez");
        proveedor.setCategoria("A");
        proveedor.setRetencion("No");
        doNothing().when(proveedorRepository).delete(proveedor);
        proveedorService.eliminarProveedor(proveedor);
        verify(proveedorRepository, times(1)).delete(proveedor);
    }

    @Test
    public void findByCodigoTest() {
        // Crear un proveedor con un código existente en la base de datos
        Proveedor proveedorExistente = new Proveedor();
        proveedorExistente.setCodigo("00001");
        when(proveedorRepository.findByCodigo(proveedorExistente.getCodigo())).thenReturn(proveedorExistente);

        // Crear un proveedor con un código que no existe en la base de datos
        Proveedor proveedorNoExistente = new Proveedor();
        proveedorNoExistente.setCodigo("10101");
        when(proveedorRepository.findByCodigo(proveedorNoExistente.getCodigo())).thenReturn(null);

        // Verificar que el método devuelve true para un proveedor existente
        boolean resultExistente = proveedorService.findByCodigo(proveedorExistente.getCodigo());
        Assert.assertTrue(resultExistente);

        // Verificar que el método devuelve false para un proveedor no existente
        boolean resultNoExistente = proveedorService.findByCodigo(proveedorNoExistente.getCodigo());
        Assert.assertFalse(resultNoExistente);

        // Verificar que se llama al método findByCodigo una vez para cada proveedor
        verify(proveedorRepository, times(1)).findByCodigo(proveedorExistente.getCodigo());
        verify(proveedorRepository, times(1)).findByCodigo(proveedorNoExistente.getCodigo());
    }

    @Test
    public void obtenerPorCodigoTest() {
        String codigo = "10001";
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo(codigo);
        proveedor.setNombre("Jose Fernandez");
        proveedor.setCategoria("A");
        proveedor.setRetencion("No");
        when(proveedorRepository.findByCodigo(codigo)).thenReturn(proveedor);

        Proveedor result = proveedorService.obtenerPorCodigo(codigo);

        Assert.assertNotNull(result);
        Assert.assertEquals(proveedor.getCodigo(), result.getCodigo());
        Assert.assertEquals(proveedor.getNombre(), result.getNombre());
        Assert.assertEquals(proveedor.getCategoria(), result.getCategoria());
        Assert.assertEquals(proveedor.getRetencion(), result.getRetencion());
        verify(proveedorRepository, times(1)).findByCodigo(codigo);
    }
}
