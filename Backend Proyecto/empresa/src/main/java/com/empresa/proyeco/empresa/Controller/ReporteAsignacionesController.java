package com.empresa.proyeco.empresa.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.model.Asignacion;
import com.empresa.proyeco.empresa.repository.AsignacionRepository;
import com.empresa.proyeco.empresa.service.ReporteAsignacionesPDFService;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteAsignacionesController {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private ReporteAsignacionesPDFService reporteAsignacionesPDFService;

    @GetMapping("/asignaciones/pdf")
    public ResponseEntity<byte[]> generarReporteAsignaciones() {
       
        List<Asignacion> asignaciones = asignacionRepository.findAll();

       
        ByteArrayInputStream pdfStream = reporteAsignacionesPDFService.generarReporteAsignaciones(asignaciones);

       
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_asignaciones.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
}
