package com.jeanrivera.evaluacion1;

import com.jeanrivera.evaluacion1.entity.Porcentaje;
import com.jeanrivera.evaluacion1.repositories.PorcentajeRepository;
import com.jeanrivera.evaluacion1.services.PorcentajeServices;
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
public class PorcentajeServiceTests {

    @Mock
    private PorcentajeRepository porcentajeRepository;

    @InjectMocks
    private PorcentajeServices porcentajeServices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void obtenerDataTest() {
        // Creamos una lista de porcentajes simulada
        List<Porcentaje> porcentajesSimulados = new ArrayList<>();
        porcentajesSimulados.add(new Porcentaje(1, "00001", "23", "10"));
        porcentajesSimulados.add(new Porcentaje(2, "00002", "12", "8"));

        // Simulamos el comportamiento del porcentajeRepository.findAll() para retornar la lista simulada
        when(porcentajeRepository.findAll()).thenReturn(porcentajesSimulados);

        // Ejecutamos el método a probar
        List<Porcentaje> porcentajesObtenidos = porcentajeServices.obtenerData();

        // Verificamos que el resultado no es nulo y tiene el tamaño correcto
        Assert.assertNotNull(porcentajesObtenidos);
        Assert.assertEquals(2, porcentajesObtenidos.size());

        // Verificamos que los elementos de la lista son iguales a los simulados
        Assert.assertEquals(porcentajesSimulados.get(0), porcentajesObtenidos.get(0));
        Assert.assertEquals(porcentajesSimulados.get(1), porcentajesObtenidos.get(1));

        // Verificamos que el método porcentajeRepository.findAll() fue llamado exactamente una vez
        verify(porcentajeRepository, times(1)).findAll();
    }

    @Test
    public void guardarDataTest() {
        // Creamos un objeto Porcentaje simulado
        Porcentaje porcentajeSimulado = new Porcentaje();
        porcentajeSimulado.setProveedor("00001");
        porcentajeSimulado.setGrasa("35");
        porcentajeSimulado.setSolidototal("8");

        // Simulamos el comportamiento del porcentajeRepository.save() para retornar el objeto simulado
        when(porcentajeRepository.save(porcentajeSimulado)).thenReturn(porcentajeSimulado);

        // Ejecutamos el método a probar
        porcentajeServices.guardarData(porcentajeSimulado);

        // Verificamos que el método porcentajeRepository.save() fue llamado exactamente una vez con el objeto simulado como argumento
        verify(porcentajeRepository, times(1)).save(porcentajeSimulado);
    }

    @Test
    public void guardarDataDBTest() {
        // Creamos los parámetros simulados
        String proveedorSimulado = "00001";
        String grasaSimulada = "3";
        String solidosSimulados = "9";

        // Ejecutamos el método a probar
        porcentajeServices.guardarDataDB(proveedorSimulado, grasaSimulada, solidosSimulados);

        // Verificamos que se haya llamado al método porcentajeRepository.save() exactamente una vez
        verify(porcentajeRepository, times(1)).save(any(Porcentaje.class));
    }

    @Test
    public void eliminarDataTest() {
        // Creamos una lista de porcentajes simulada
        List<Porcentaje> porcentajesSimulados = new ArrayList<>();
        porcentajesSimulados.add(new Porcentaje(1, "00001", "23", "10"));
        porcentajesSimulados.add(new Porcentaje(2, "00002", "12", "8"));

        // Simulamos el comportamiento del porcentajeRepository.deleteAll() para no hacer nada
        doNothing().when(porcentajeRepository).deleteAll(porcentajesSimulados);

        // Ejecutamos el método a probar
        porcentajeServices.eliminarData(porcentajesSimulados);

        // Verificamos que el método porcentajeRepository.deleteAll() fue llamado exactamente una vez con la lista simulada
        verify(porcentajeRepository, times(1)).deleteAll(porcentajesSimulados);
    }

    @Test
    public void findByProveedorTest() {
        // Creamos un proveedor simulado
        Porcentaje porcentajeSimulado = new Porcentaje(1, "00001", "23", "10");

        // Simulamos el comportamiento del porcentajeRepository.findByProveedor() para retornar el proveedor simulado
        when(porcentajeRepository.findByProveedor("00001")).thenReturn(porcentajeSimulado);

        // Ejecutamos el método a probar
        Porcentaje porcentajeObtenido = porcentajeServices.findByProveedor("00001");

        // Verificamos que el resultado no es nulo y es igual al proveedor simulado
        Assert.assertNotNull(porcentajeObtenido);
        Assert.assertEquals(porcentajeSimulado, porcentajeObtenido);

        // Verificamos que el método porcentajeRepository.findByProveedor() fue llamado exactamente una vez con el código "00001"
        verify(porcentajeRepository, times(1)).findByProveedor("00001");
    }
}
